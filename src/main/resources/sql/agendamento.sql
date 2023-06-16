ALTER VIEW AGENDAMENTO AS
SELECT ZAP_FILIAL AS EMPRESA, ZAP_VEND AS CODIGO_VENDEDOR
,CAST(ZAP_DATA AS DATE) AS DATA_AGENDAMENTO,
ZAP_HORAI AS HORA_INICIAL,ZAP_HORAF AS HORA_FINAL,
ZAP_CLIENT AS CODIGO_CLIENTE, ZAP_LOJA AS LOJA_CLIENTE, ZAP_NOME AS NOME_CLIENTE,
CASE ZAP_SITUA WHEN '1' THEN 'Agendamento' WHEN '2' THEN 'Remarcado' WHEN '3' THEN 'Cliente nao compareceu' WHEN '4' THEN 'Concluido' END  AS SITUACAO,
ZAP_VALOR AS VALOR, RTRIM(ZAP_OBS) AS OBSERVACAO,ZAP.R_E_C_N_O_ AS REC,RTRIM(A1_DDD+A1_TEL) AS TELEFONE
 FROM ZAP010 ZAP (NOLOCK) INNER JOIN SA1010 SA1 ON A1_COD=ZAP_CLIENT AND A1_LOJA = ZAP_LOJA WHERE ZAP.D_E_L_E_T_ = '' AND SA1.D_E_L_E_T_ = ''