#Include 'protheus.ch'
#Include 'FWMVCDEF.ch'
#Include "RestFul.CH"
#include "tbiconn.ch"
#INCLUDE 'totvs.ch'
#INCLUDE "restful.ch"
WSRESTFUL APIZAP DESCRIPTION "servico REST APIZAP" FORMAT APPLICATION_JSON

WSMETHOD PUT UPDAGE DESCRIPTION "Atualiza a observacao e status de um agendamento" PATH "/v1/atualizar" TTALK "v1"
WSMETHOD POST CADAGE DESCRIPTION "Cadastra um agendamento" PATH "/v1/cadastrar" TTALK "v1"
WSMETHOD DELETE DELAGE DESCRIPTION "Deleta um agendamento" PATH "/v1/deletar" TTALK "v1"


END WSRESTFUL

WSMETHOD POST CADAGE WSSERVICE APIZAP
	aArea		:= GetArea()
	cBody := ::GetContent()
	oJson := JsonObject():New()
	ret := oJson:FromJson(cBody)
	::SetContentType("application/json")

        Reclock("ZAP",.T.)
		ZAP->ZAP_FILIAL    :=  PadR(FWNoAccent(ALLTRIM(oJson['empresa'])),TamSX3("ZAP_FILIAL")[1])
		ZAP->ZAP_CODIGO    :=  "000001"
		ZAP->ZAP_VEND      :=  PadR(FWNoAccent(ALLTRIM(oJson['codigoVendedor'])),TamSX3("ZAP_VEND")[1])
		ZAP->ZAP_DATA      :=  CTOD(oJson['dataAgendamento'])
		ZAP->ZAP_HORAI     :=  PadR(FWNoAccent(ALLTRIM(oJson['horaInicial'])),TamSX3("ZAP_HORAI")[1])
		ZAP->ZAP_HORAF     :=  PadR(FWNoAccent(ALLTRIM(oJson['horaFinal'])),TamSX3("ZAP_HORAF")[1])
		ZAP->ZAP_CLIENT    :=  PadR(FWNoAccent(ALLTRIM(oJson['codigoCliente'])),TamSX3("ZAP_CLIENT")[1])
		ZAP->ZAP_LOJA      :=  PadR(FWNoAccent(ALLTRIM(oJson['lojaCliente'])),TamSX3("ZAP_LOJA")[1])
		ZAP->ZAP_NOME      :=  PadR(FWNoAccent(ALLTRIM(oJson['nomeCliente'])),TamSX3("ZAP_NOME")[1])
		ZAP->ZAP_SITUA     :=  "1"
		ZAP->ZAP_VALOR     :=  oJson['valor']
		ZAP->ZAP_OBS       := PadR(FWNoAccent(ALLTRIM(oJson['observacao'])),TamSX3("ZAP_OBS")[1])
		ZAP->(MsUnLock())
		oJson['id'] := ZAP->(Recno())
		::SetResponse(oJson:toJSON())
	FreeObj(oJson)

	RestArea(aArea)
Return(.T.)

WSMETHOD PUT UPDAGE WSSERVICE APIZAP
	aArea		:= GetArea()
	cBody := ::GetContent()
	oJson := JsonObject():New()
	ret := oJson:FromJson(cBody)
	::SetContentType("application/json")
	IF oJson['id'] > 0
		_cObs := PadR(FWNoAccent(ALLTRIM(oJson['observacao'])),TamSX3("ZAP_OBS")[1])
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


WSMETHOD DELETE DELAGE WSSERVICE APIZAP
	aArea		:= GetArea()
	cBody := ::GetContent()
	oJson := JsonObject():New()
	ret := oJson:FromJson(cBody)
	::SetContentType("application/json")
	IF oJson['id'] > 0
		_cSql := " UPDATE "+RETSQLNAME("ZAP")+" SET D_E_L_E_T_ = '*', R_E_C_D_E_L_ = R_E_C_N_O_ WHERE R_E_C_N_O_ = "+cValToChar(oJson['id'])
		TCSQLEXEC(_cSql)
		::SetResponse(oJson:toJSON())
	ENDIF
	FreeObj(oJson)

	RestArea(aArea)
Return(.T.)
