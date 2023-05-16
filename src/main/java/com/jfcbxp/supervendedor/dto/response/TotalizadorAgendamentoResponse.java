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
public class TotalizadorAgendamentoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer total;
    private Integer previstos;
    private Integer faltas;
    private Integer chegadas;
}
