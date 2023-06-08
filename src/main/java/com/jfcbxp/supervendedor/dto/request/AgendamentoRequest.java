package com.jfcbxp.supervendedor.dto.request;

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
public class AgendamentoRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String codigoVendedor;
    private String comentario;
    private String situacao;
}
