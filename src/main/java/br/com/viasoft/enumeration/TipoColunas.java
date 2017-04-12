package br.com.viasoft.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoColunas {

    SMALLINT(false, false),
    INTEGER(false, false),
    BIGINT(false, false),
    NUMERIC(true, true),
    FLOAT(false, false),
    DOUBLE(false, false),
    DECIMAL(true, true),
    DATE(false, false),
    TIME(false, false),
    TIMESTAMP(false, false),
    CHAR(true, false),
    VARCHAR(true, false),
    BLOB(true, false);

    private boolean size;
    private boolean scale;

}