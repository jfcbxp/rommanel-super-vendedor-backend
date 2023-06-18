package com.jfcbxp.supervendedor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "META_VENDEDOR")
@AllArgsConstructor
@NoArgsConstructor
public class Meta implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("EMPRESA")
    private String empresa;
    @Column("TIPO_VENDEDOR")
    private String tipoVendedor;
    @Column("DATA_INICIAL")
    private LocalDate dataInicial;
    @Column("DATA_FINAL")
    private LocalDate dataFinal;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO")
    private String codigo;
    @Column("DIAS")
    private Integer dias;
    @Column("META_ESPECIFICA")
    private BigDecimal metaValor;
    @Column("META_PROSPECTO")
    private BigDecimal metaProspecto;
    @Column("META_QTD_PECAS")
    private BigDecimal metaQuantidadePecas;
    @Column("DATA_ATUALIZACAO")
    private LocalDate dataAtualizacao;
    @Column("HORA_ATUALIZACAO")
    private String horaAtualizacao;
    @Column("QUANTIDADE_PROSPECTOS")
    private Integer quantidadeProspectos;
    @Column("QUANTIDADE_REATIVOS")
    private Integer quantidadeReativos;
    @Column("TICKET_MEDIO")
    private BigDecimal ticketMedio;
    @Column("QUANTIDADE_VENDAS")
    private Integer quantidadeVendas;
    @Column("QUANTIDADE_ITENS")
    private Integer quantidadeItens;
    @Column("VALOR_VENDIDO")
    private BigDecimal totalVendido;
    @Column("QUANTIDADE_DEVOLVIDO")
    private Integer quantidadeDevolvido;
    @Column("VALOR_DEVOLVIDO")
    private BigDecimal totalDevolvido;
    @Column("VALOR_COMISSAO")
    private BigDecimal totalComissao;
    @Column("QTD_CARTEIRA")
    private Integer quantidadeCarteira;
    @Column("QTD_CLIENTES")
    private Integer quantidadeClientes;
    @Column("ATENDIMENTO_PERCENTUAL")
    private BigDecimal atendimentoPercentual;
}
