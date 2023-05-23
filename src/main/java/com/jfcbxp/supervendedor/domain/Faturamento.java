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
@Table(name = "FATURAMENTO_VENDEDOR")
@AllArgsConstructor
@NoArgsConstructor
public class Faturamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("ORCAMENTO")
    private String orcamento;
    @Column("EMPRESA")
    private String empresa;
    @Column("TIPO_ORCAMENTO")
    private String tipoOrcamento;
    @Column("EMISSAO")
    private LocalDate emissao;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO_CLIENTE")
    private String codigoCliente;
    @Column("LOJA_CLIENTE")
    private String lojaCliente;
    @Column("NOME_CLIENTE")
    private String nomeCliente;
    @Column("NOTA_FISCAL")
    private String observacao;
    @Column("SERIE")
    private String serie;
    @Column("TELEFONE")
    private String telefone;
    @Column("TOTAL")
    private BigDecimal total;
    @Column("FRETE")
    private BigDecimal frete;
    @Column("VALOR_RA")
    private BigDecimal valorRA;
    @Column("VALOR_NCC")
    private BigDecimal valorNCC;
    @Column("FORMA_PAGAMENTO")
    private String formaPagamento;

}
