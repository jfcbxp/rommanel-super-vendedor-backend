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
@Table(name = "AGENDAMENTO")
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("EMPRESA")
    private String empresa;
    @Column("SITUACAO")
    private String situacao;
    @Column("DATA_AGENDAMENTO")
    private LocalDate dataAgendamento;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO_CLIENTE")
    private String codigoCliente;
    @Column("LOJA_CLIENTE")
    private String lojaCliente;
    @Column("NOME_CLIENTE")
    private String nomeCliente;
    @Column("VALOR")
    private BigDecimal valor;
    @Column("OBSERVACAO")
    private String observacao;
    @Column("HORA_INICIAL")
    private String horaInicial;
    @Column("HORA_FINAL")
    private String horaFinal;
    @Column("TELEFONE")
    private String telefone;

}
