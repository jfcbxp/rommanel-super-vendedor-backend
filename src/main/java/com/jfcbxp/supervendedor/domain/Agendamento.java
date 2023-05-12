package com.jfcbxp.supervendedor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
@Table(name = "AGENDAMENTO")
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("REC")
    private Integer id;
    @Column("EMPRESA")
    private String empresa;
    @Column("SITUACAO")
    private String situacao;
    @Column("DATA_AGENDAMENTO")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAgendamento;
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO_CLIENTE")
    private String codigoCliente;
    @Column("LOJA_CLIENTE")
    private String lojaCliente;
    @Column("NOME_CLIENTE")
    private String nomeCliente;

}
