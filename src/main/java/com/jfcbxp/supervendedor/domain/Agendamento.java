package com.jfcbxp.supervendedor.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "AGENDAMENTO")
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

}
