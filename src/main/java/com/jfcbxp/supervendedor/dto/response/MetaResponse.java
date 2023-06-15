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
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String empresa;
    private String tipoVendedor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicial;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinal;
    private String codigoVendedor;
    private String codigo;
    private Integer dias;
    private BigDecimal metaValor;
    private BigDecimal metaProspecto;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAtualizacao;
    private String horaAtualizacao;
    private Integer quantidadeProspectos;
    private Integer quantidadeReativos;
    private BigDecimal ticketMedio;
    private Integer quantidadeVendas;
    private Integer quantidadeItens;
    private BigDecimal totalVendido;
    private Integer quantidadeDevolvido;
    private BigDecimal totalDevolvido;

    public long getProgressoPeriodo(){
        long totalDays = ChronoUnit.DAYS.between( dataInicial , dataFinal ) ;
        long elapsedDays = ChronoUnit.DAYS.between( dataInicial , LocalDate.now() ) ;
        return  ( elapsedDays * 100 ) / totalDays ;
    }
}
