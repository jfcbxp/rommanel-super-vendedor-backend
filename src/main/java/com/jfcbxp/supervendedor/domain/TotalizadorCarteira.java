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
@Table(name = "TOTALIZADOR_CARTEIRA")
@AllArgsConstructor
@NoArgsConstructor
public class TotalizadorCarteira implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column("CODIGO_VENDEDOR")
    private String codigoVendedor;
    @Column("CODIGO")
    private String codigo;
    @Column("TOTAL")
    private Integer total;
    @Column("ATIVOS")
    private Integer ativos;
    @Column("PREINATIVOS")
    private Integer preInativos;
    @Column("INATIVOS")
    private Integer inativos;

}
