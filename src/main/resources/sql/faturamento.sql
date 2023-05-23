ALTER VIEW FATURAMENTO_VENDEDOR AS  
SELECT EMPRESA,ORCAMENTO,
CASE TIPO_ORCAMENTO
WHEN '1' THEN 'Normal'
WHEN '2' THEN 'Troca'
WHEN '3' THEN 'Material de Apoio'
WHEN '4' THEN 'Brinde'
WHEN '5' THEN 'Pagamento de Titulos'
WHEN '7' THEN 'Devolução'
WHEN '8' THEN 'Venda Externa'
WHEN '9' THEN 'Varejo'
WHEN '0' THEN 'Remessa Venda Externa'
WHEN 'A' THEN 'Troca Venda Externa'
WHEN 'E' THEN 'Ecommerce'
WHEN 'O' THEN 'Whatsapp'
END AS TIPO_ORCAMENTO
, A1_COD AS CODIGO_CLIENTE, A1_LOJA AS LOJA_CLIENTE, RTRIM(A1_NOME) AS NOME_CLIENTE, RTRIM(A1_DDD+A1_TEL) AS TELEFONE,
L1_DOC AS NOTA_FISCAL, L1_SERIE AS SERIE, CAST(F2_EMISSAO AS DATE) EMISSAO,
L1_VLRLIQ AS TOTAL, L1_VEND AS CODIGO_VENDEDOR, CASE WHEN L1_FORMPG = '' AND
                  VALOR_RA > 0 THEN 'RA' ELSE L1_FORMPG END AS FORMA_PAGAMENTO,
				  VALOR_RA,VALOR_NCC, FRETE, REC
FROM     (
SELECT L1.L1_FILIAL AS EMPRESA,L1.L1_NUM AS ORCAMENTO,L1_TIPOORC AS TIPO_ORCAMENTO, A1.A1_COD, A1.A1_LOJA, A1.A1_NOME, A1.A1_TEL, A1.A1_DDD, L1.L1_DOC, L1.L1_SERIE,
                                    L1.L1_EMISNF AS F2_EMISSAO, L1.L1_VLRLIQ, L1.L1_VEND, A1.A1_MCOMPRA, A1.A1_LC, L1.L1_FORMPG, L1.L1_CREDITO, L1.L1_FILIAL,
                                        ISNULL((SELECT SUM(E5_VALOR) AS TOTAL
                                         FROM      dbo.SE5010 AS E5 WITH (NOLOCK)
                                         WHERE   (E5_NUMERO = L1.L1_DOC) AND (E5_PREFIXO = L1.L1_SERIE) AND (E5_FILIAL = L1.L1_FILIAL) AND (E5_TIPO = 'CR') AND (E5_PARCELA = '001')  AND (E5_DOCUMEN LIKE '%NCC%') AND (D_E_L_E_T_ = '') ),0)
                                    AS VALOR_RA,
                                        ISNULL((SELECT SUM(E5_VALOR) AS TOTAL
                                         FROM      dbo.SE5010 AS E5 WITH (NOLOCK)
                                         WHERE   (E5_NUMERO = L1.L1_DOC) AND (E5_PREFIXO = L1.L1_SERIE) AND (E5_FILIAL = L1.L1_FILIAL) AND (E5_TIPO = 'CR') AND (E5_PARCELA = '001')  AND (E5_DOCUMEN LIKE '%NCC%') AND (D_E_L_E_T_ = '') ),0)
                                    AS VALOR_NCC, L1.L1_DESPESA + L1.L1_FRETE AS FRETE, L1.R_E_C_N_O_ AS REC
                  FROM      dbo.SA1010 AS A1 WITH (NOLOCK) INNER JOIN
                                    dbo.SL1010 AS L1 WITH (NOLOCK) ON L1.L1_CLIENTE = A1.A1_COD AND L1.L1_LOJA = A1.A1_LOJA
                  WHERE   (L1.L1_DOC <> ' ')  AND (A1.D_E_L_E_T_ <> '*') AND (L1.D_E_L_E_T_ <> '*') AND ((L1.L1_FILIAL + L1.L1_NUM) IN
                                        (SELECT L2.L2_FILIAL + L2.L2_NUM AS Expr1
                                         FROM      dbo.SL2010 AS L2 WITH (NOLOCK) INNER JOIN
                                                           dbo.SF4010 AS F4 WITH (NOLOCK) ON L2.L2_TES = F4.F4_CODIGO AND SUBSTRING(L2.L2_FILIAL, 1, 2) = SUBSTRING(F4.F4_FILIAL, 1, 2)
                                         WHERE (L2.L2_NUM = L1.L1_NUM) AND (L2.L2_FILIAL = L1.L1_FILIAL) AND   (L2.D_E_L_E_T_ = '') AND (F4.D_E_L_E_T_ = '') AND (L2.L2_CF NOT IN ('5910', '5949')) AND (F4.F4_DUPLIC <> 'N') ))) AS TMP
