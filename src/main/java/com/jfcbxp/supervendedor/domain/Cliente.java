package com.jfcbxp.supervendedor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

@Data
@Table(name = "CLIENTE")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("CODIGO")
    private String codigo;
    @Column("LOJA")
    private String loja;
    @Column("NOME")
    private String nome;
    @Column("NOME_FANTASIA")
    private String nomeFantasia;
    @Column("CGC")
    private String cgc;
    @Column("TELEFONE")
    private String telefone;
    @Column("EMAIL")
    private String email;
}
