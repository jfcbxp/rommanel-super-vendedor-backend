package com.jfcbxp.supervendedor.domain;

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
@Table(name = "TOTALIZADOR_AGENDAMENTO")
@AllArgsConstructor
@NoArgsConstructor
public class TotalizadorAgendamento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("DATA_AGENDAMENTO")
    private LocalDate dataAgendamento;
    @Column("TOTAL")
    private Integer total;
    @Column("PREVISTOS")
    private Integer previstos;
    @Column("FALTAS")
    private Integer faltas;
    @Column("CHEGADAS")
    private Integer chegadas;

}
