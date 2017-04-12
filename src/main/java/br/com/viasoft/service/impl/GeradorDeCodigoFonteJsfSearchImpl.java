package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.model.Campos;
import br.com.viasoft.model.InformacoesCodigoFonte;
import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.LogExecucao;
import org.apache.commons.lang3.text.WordUtils;
import org.xembly.Directives;

/**
 * Created by gaspar on 11/04/17.
 */
public class GeradorDeCodigoFonteJsfSearchImpl extends GeradorDeCodigoFonteJsfXhtmlImpl implements GeradorDeCodigoPorTemplate {

    @Override
    public String gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao, String codigoFonteGerado) {
        try {
            codigoFonteGerado = varReplace(informacoesCodigoFonte, codigoFonteGerado);

            return codigoFonteGerado
                    .replace("@{jsfDataTableColumns}", getJsfDataTableColumns(informacoesCodigoFonte))
                    .replace("@{jsfDialogViewGrid}", getJsfDialogViewGrid(informacoesCodigoFonte))
                    .replace("@{controller}", WordUtils.uncapitalize(informacoesCodigoFonte.getClassName()) + "Controller");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getJsfDataTableColumns(InformacoesCodigoFonte informacoesCodigoFonte) throws Exception {
        StringBuilder columns = new StringBuilder();

        for (Campos campos : informacoesCodigoFonte.getCamposList()) {
            String value = "#{item.%s}";
            value = String.format(value, campos.getField());

            Directives column = new Directives()
                    .add("p:column")
                    .attr("sortBy", value)
                    .attr("headerText", campos.getDescricao())
                    .add("h:outputText")
                    .attr("value", value);

            addAttrInXmlByTipoColuna(column, campos.getTipoColuna());

            columns.append(getXmlByDirectives(column));
        }

        return columns.toString();
    }

    private void addAttrInXmlByTipoColuna(Directives directives, TipoColunas tipoColunas) {
        if (TipoColunas.DATE.equals(tipoColunas)
                || TipoColunas.TIME.equals(tipoColunas)
                || TipoColunas.TIMESTAMP.equals(tipoColunas)) {
            directives
                    .add("f:attribute")
                    .attr("name", "formato")
                    .attr("value", getPatternDataByTipoColuna(tipoColunas));

            directives
                    .up()
                    .add("f:attribute")
                    .attr("name", "ignoreTimeZone")
                    .attr("value", "true");
        } else if (TipoColunas.DECIMAL.equals(tipoColunas)
                || TipoColunas.DOUBLE.equals(tipoColunas)
                || TipoColunas.FLOAT.equals(tipoColunas)) {
            directives
                    .add("f:convertNumber")
                    .attr("locale", "pt_BR")
                    .attr("pattern", "#,##0.00");
        }
    }

    private String getJsfDialogViewGrid(InformacoesCodigoFonte informacoesCodigoFonte) throws Exception {
        StringBuilder grid = new StringBuilder();

        for (Campos campos : informacoesCodigoFonte.getCamposList()) {
            Directives columnLabel = new Directives()
                    .add("p:column")
                    .add("b")
                    .add("h:outputText")
                    .attr("value", campos.getDescricao() + ":");
            grid.append(getXmlByDirectives(columnLabel));

            String value = "#{%s}";
            value = String.format(
                    value,
                    WordUtils.uncapitalize(informacoesCodigoFonte.getClassName()) + "Controller.entityView." + campos.getField()
            );

            Directives columnValue = new Directives()
                    .add("p:column")
                    .add("h:outputText")
                    .attr("value", value);

            addAttrInXmlByTipoColuna(columnValue, campos.getTipoColuna());

            grid.append(getXmlByDirectives(columnValue));
        }

        return grid.toString();
    }

}
