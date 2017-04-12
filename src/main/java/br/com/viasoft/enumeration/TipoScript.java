package br.com.viasoft.enumeration;

import br.com.viasoft.service.GeradorDeScript;
import br.com.viasoft.service.impl.GeradorDeScriptFirebird;
import br.com.viasoft.service.impl.GeradorDeScriptLiquibase;
import br.com.viasoft.service.impl.GeradorDeScriptOracle;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by gaspar on 07/04/17.
 */
@Getter
@AllArgsConstructor
public enum TipoScript {

    FIREBIRD(new GeradorDeScriptFirebird()),
    LIQUIBASE(new GeradorDeScriptLiquibase()),
    ORACLE(new GeradorDeScriptOracle()),
    JPA(null);

    private GeradorDeScript geradorDeScript;

}
