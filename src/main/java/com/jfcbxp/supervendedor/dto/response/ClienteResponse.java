package com.jfcbxp.supervendedor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private String loja;
    private String nome;
    private String nomeFantasia;
    private String cgc;
    private String telefone;
    private String email;
}
