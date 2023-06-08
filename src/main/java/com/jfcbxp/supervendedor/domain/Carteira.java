package com.jfcbxp.supervendedor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Table(name = "CARTEIRA_CLIENTE")
@AllArgsConstructor
@NoArgsConstructor
public class Carteira implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("CODIGO")
    private String codigo;
    @Column("EMPRESA")
    private String empresa;
    @Column("SITUACAO")
    private String situacao;
    @Column("ULTIMO_AGENDAMENTO")
    private LocalDate ultimoAgendamento;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO_CLIENTE")
    private String codigoCliente;
    @Column("LOJA_CLIENTE")
    private String lojaCliente;
    @Column("NOME_CLIENTE")
    private String nomeCliente;
    @Column("OBSERVACAO")
    private String observacao;
    @Column("TELEFONE")
    private String telefone;
    @Column("ULTIMO_VENDEDOR")
    private String ultimoCodigoVendedor;
    @Column("ULTIMA_FILIAL")
    private String ultimaEmpresa;
    @Column("ULTIMA_COMPRA")
    private LocalDate ultimaCompra;
    @Column("NASCIMENTO")
    private LocalDate nascimento;
}
