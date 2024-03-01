
ALTER VIEW CARTEIRA_CLIENTE AS
SELECT ZAO_CODIGO AS CODIGO,ZAO_FILIAL AS EMPRESA,ZAO_VEND AS CODIGO_VENDEDOR,ZAO_CODCLI AS CODIGO_CLIENTE,ZAO_LOJCLI AS LOJA_CLIENTE,
CASE A1_SITUA1
WHEN '1' THEN 'Ativo'
WHEN '2' THEN 'Bloqueado media de vendas'
WHEN '3' THEN 'Inativo'
WHEN '4' THEN '30 dias sem compras p1'
WHEN '5' THEN '60 dias sem compras p2'
WHEN '6' THEN 'Cessado'
END
AS SITUACAO,
RTRIM(ZAO_NOME) AS NOME_CLIENTE,CASE WHEN ZAO_UAGE <> '' THEN CAST(ZAO_UAGE AS DATE) ELSE NULL END AS ULTIMO_AGENDAMENTO ,ZAO_ULTVEN AS ULTIMO_VENDEDOR,
ZAO_ULTFIL AS ULTIMA_FILIAL,RTRIM(ZAO_OBS) AS OBSERVACAO,ZAO.R_E_C_N_O_ AS REC,RTRIM(A1_DDD+A1_TEL) AS TELEFONE, CASE WHEN A1_ULTCOM <> ''  THEN CAST(A1_ULTCOM AS DATE) ELSE NULL END AS ULTIMA_COMPRA,
CASE WHEN A1_DTNASC <> '' THEN CAST(A1_DTNASC AS DATE) ELSE NULL END AS NASCIMENTO
FROM ZAO010 ZAO (NOLOCK) INNER JOIN SA1010  A1 ON ZAO_CODCLI = A1_COD AND ZAO_LOJCLI = A1_LOJA AND ZAO.D_E_L_E_T_ = '' AND A1.D_E_L_E_T_ = ''



