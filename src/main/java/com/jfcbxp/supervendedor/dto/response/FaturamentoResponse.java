package com.jfcbxp.supervendedor.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaturamentoResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String orcamento;
    private String empresa;
    private String tipoOrcamento;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate emissao;
    private String codigoVendedor;
    private String codigoCliente;
    private String lojaCliente;
    private String nomeCliente;
    private String observacao;
    private String serie;
    private String telefone;
    private BigDecimal total;
    private BigDecimal frete;
    private BigDecimal valorRA;
    private BigDecimal valorNCC;
    private String formaPagamento;
}
