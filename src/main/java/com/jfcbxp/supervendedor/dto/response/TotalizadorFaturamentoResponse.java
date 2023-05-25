package com.jfcbxp.supervendedor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalizadorFaturamentoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private BigDecimal qtd;
    private BigDecimal total;
    private BigDecimal credito;
    public BigDecimal getLiquido(){
        return total.subtract(credito);
    }
}
