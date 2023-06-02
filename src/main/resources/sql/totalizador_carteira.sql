ALTER VIEW TOTALIZADOR_CARTEIRA AS
SELECT CODIGO_VENDEDOR,CODIGO,COUNT(*) AS TOTAL, SUM(CASE  WHEN  SITUACAO = 'Ativo' THEN 1 ELSE 0 END) AS ATIVOS,
SUM(CASE  WHEN  SITUACAO IN ('Pre Inativo 1 60 dias sem compras','Pre Inativo 2 70 dias sem compras','Pre Inativo 3 80 dias sem compras') THEN 1 ELSE 0 END) AS PREINATIVOS,
SUM(CASE  WHEN  SITUACAO IN ('Bloqueado media de vendas','Bloqueado 90 dias sem compras','Bloqueado 150 dias sem compras','365 dias sem compras','730 dias sem compras','Bloqueado sem compras') THEN 1 ELSE 0 END) AS INATIVOS
FROM CARTEIRA_CLIENTE
GROUP BY CODIGO,CODIGO_VENDEDOR