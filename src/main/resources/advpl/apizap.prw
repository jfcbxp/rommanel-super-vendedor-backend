#Include 'protheus.ch'
#Include 'FWMVCDEF.ch'
#Include "RestFul.CH"
#include "tbiconn.ch"
#INCLUDE 'totvs.ch'
#INCLUDE "restful.ch"
WSRESTFUL APIZAP DESCRIPTION "servico REST APIZAP" FORMAT APPLICATION_JSON

WSMETHOD PUT UPDAGE DESCRIPTION "Atualiza o comentario e status de um agendamento" PATH "/v1/atualizar" TTALK "v1"


END WSRESTFUL

WSMETHOD PUT UPDAGE WSSERVICE APIZAP
	aArea		:= GetArea()
	cBody := ::GetContent()
	oJson := JsonObject():New()
	ret := oJson:FromJson(cBody)
	::SetContentType("application/json")
	IF oJson['id'] > 0
		_cObs := PadR(FWNoAccent(ALLTRIM(oJson['comentario'])),TamSX3("ZAP_OBS")[1])
		_cSitua := ALLTRIM(oJson['situacao'])
		Do Case
			Case _cSitua == "Remarcado"
				_cSitua :=  "2"
			Case _cSitua == "Cliente nao compareceu"
				_cSitua :=  "3"
			Case _cSitua == "Concluido"
				_cSitua :=  "4"
			OtherWise
				_cSitua := "1"
		EndCase
		_cSql := " UPDATE "+RETSQLNAME("ZAP")+" SET ZAP_OBS = '"+_cObs+"',ZAP_SITUA = '"+_cSitua+"' WHERE R_E_C_N_O_ = "+cValToChar(oJson['id'])
		TCSQLEXEC(_cSql)
		::SetResponse(oJson:toJSON())

	ENDIF
	FreeObj(oJson)

	RestArea(aArea)
Return(.T.)
