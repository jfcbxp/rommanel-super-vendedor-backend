ALTER VIEW TOTALIZADOR_AGENDAMENTO AS
SELECT CODIGO_VENDEDOR,DATA_AGENDAMENTO,COUNT(*) AS TOTAL, SUM(CASE  WHEN  SITUACAO = 'Agendamento' THEN 1 ELSE 0 END) AS PREVISTOS,
SUM(CASE  WHEN  SITUACAO = 'Cliente nao compareceu' THEN 1 ELSE 0 END) AS FALTAS,
SUM(CASE  WHEN  SITUACAO = 'Concluido' THEN 1 ELSE 0 END) AS CHEGADAS
FROM AGENDAMENTO
GROUP BY CODIGO_VENDEDOR,DATA_AGENDAMENTO