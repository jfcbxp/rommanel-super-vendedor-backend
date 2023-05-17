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
@Table(name = "USUARIOS_SUPERVENDEDOR")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("CODIGO")
    private String codigo;
    @Column("SENHA")
    private String senha;
    @Column("NOME")
    private String nome;
    @Column("NOME_REDUZIDO")
    private String nomeReduzido;

}
