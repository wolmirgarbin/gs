package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.model.Campos;
import br.com.viasoft.service.GeradorDeScript;

import java.util.List;

/**
 * Created by gaspar on 07/04/17.
 */
public class GeradorDeScriptFirebird implements GeradorDeScript {

    private String getType(TipoColunas tipoColunas) {
        if (TipoColunas.DOUBLE.equals(tipoColunas)) {
            return "DOUBLE PRECISION";
        }
        return tipoColunas.name();
    }

    @Override
    public String getScript(String tabela, List<Campos> camposList) {
        String tabelaUpperCase = tabela.toUpperCase();

        StringBuilder script = new StringBuilder("CREATE TABLE ").append(tabelaUpperCase).append(" (\n");

        int length = 0;
        for (Campos campo : camposList) {
            if (length > 0) {
                script.append(",\n");
            }
            length++;
            // Column NAME
            script.append("\t").append(campo.getNome()).append(" ");

            // Coumn TYPE
            script.append(getType(campo.getTipoColuna()));
            if (campo.getTipoColuna().isSize()) {
                script.append("(");
                script.append(campo.getSize());
                if (campo.getTipoColuna().isScale()) {
                    script.append(", ").append(campo.getScale());
                }
                script.append(")");
            }

            if (campo.getNotNull()) {
                script.append(" NOT NULL");
            }
        }

        script.append("\n);");

        camposList.stream()
                .filter(Campos::getPrimary)
                .findAny()
                .ifPresent(c -> {
                    script.append("\n\n");
                    script.append(String.format(
                            "ALTER TABLE %s ADD CONSTRAINT %s PRIMARY KEY (%s);",
                            tabelaUpperCase,
                            tabelaUpperCase+"_01",
                            c.getNome()
                    ));
                });

        script.append("\n\n");
        script.append("CREATE SEQUENCE ").append(tabelaUpperCase).append("_SEQ;");

        return script.toString();
    }

}
