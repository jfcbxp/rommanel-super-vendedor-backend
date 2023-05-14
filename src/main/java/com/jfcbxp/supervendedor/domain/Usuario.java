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
@Table(name = "USUARIOS_COMISSAO")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("USUARIO")
    private String username;
    @Column("SENHA")
    private String password;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("GRUPO")
    private String grupo;
    @Column("FILIAIS")
    private String filiais;

}
