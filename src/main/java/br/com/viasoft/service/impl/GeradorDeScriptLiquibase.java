package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.model.Campos;
import br.com.viasoft.service.GeradorDeScript;
import org.xembly.Directives;

import java.util.List;

/**
 * Created by gaspar on 07/04/17.
 */
public class GeradorDeScriptLiquibase extends GeradorDeCodigoFonteXmlImpl implements GeradorDeScript {

    private String getType(TipoColunas tipoColunas) {
        if (TipoColunas.INTEGER.equals(tipoColunas)) {
            return "int";
        } else if (TipoColunas.BLOB.equals(tipoColunas)) {
            return "clob";
        } else if (TipoColunas.NUMERIC.equals(tipoColunas)) {
            return "currency";
        }
        return tipoColunas.name().toLowerCase();
    }

    @Override
    public String getScript(String tabela, List<Campos> camposList) {
        try {
            String tabelaUpperCase = tabela.toUpperCase();

            Directives table = new Directives()
                    .add("createTable")
                    .attr("tableName", tabelaUpperCase);

            for (Campos campo : camposList) {
                StringBuilder type = new StringBuilder(getType(campo.getTipoColuna()));
                if (campo.getTipoColuna().isSize()) {
                    type.append("(");
                    type.append(campo.getSize());
                    if (campo.getTipoColuna().isScale()) {
                        type.append(", ").append(campo.getScale());
                    }
                    type.append(")");
                }

                table.add("column")
                     .attr("name", campo.getNome())
                     .attr("type", type.toString());

                if (campo.getPrimary()) {
                    table.add("constraints")
                         .attr("primaryKey", "true")
                         .attr("nullable", "false");
                    table.up();
                } else if (campo.getNotNull()) {
                    table.add("constraints")
                         .attr("nullable", "false");
                    table.up();
                }

                table.up();
            }

            Directives sequence = new Directives()
                    .add("createSequence")
                    .attr("cycle", "true")
                    .attr("incrementBy", "1")
                    .attr("schemaName", tabelaUpperCase + "_SEQ")
                    .attr("startValue", "0")
                    .attr("minValue", "0")
                    .attr("ordered", "true");

            return getXmlByDirectives(table) +
                    "\n\n" +
                    getXmlByDirectives(sequence);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
