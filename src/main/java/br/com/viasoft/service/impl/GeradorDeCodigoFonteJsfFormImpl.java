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
public class GeradorDeCodigoFonteJsfFormImpl extends GeradorDeCodigoFonteJsfXhtmlImpl implements GeradorDeCodigoPorTemplate {

    @Override
    public String gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao, String codigoFonteGerado) {
        try {
            codigoFonteGerado = varReplace(informacoesCodigoFonte, codigoFonteGerado);
            return codigoFonteGerado.replace("@{jsfFormGrid}", getJsfFormGrid(informacoesCodigoFonte));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getJsfFormGrid(InformacoesCodigoFonte informacoesCodigoFonte) throws Exception {
        StringBuilder columns = new StringBuilder();

        for (Campos campos : informacoesCodigoFonte.getCamposList()) {
            String id = "form_" + campos.getField();

            Directives field = new Directives()
                    .add("p:row")
                    .add("p:column")
                    .add("h:outputLabel")
                    .attr("value", campos.getDescricao())
                    .attr("for", id)
                    .up()
                    .up()
                    .add("p:column");

            if (TipoColunas.SMALLINT.equals(campos.getTipoColuna())
                    || TipoColunas.INTEGER.equals(campos.getTipoColuna())
                    || TipoColunas.BIGINT.equals(campos.getTipoColuna())
                    || TipoColunas.NUMERIC.equals(campos.getTipoColuna())) {
                field.add("p:inputNumber");
            } else if (TipoColunas.DATE.equals(campos.getTipoColuna())
                    || TipoColunas.TIME.equals(campos.getTipoColuna())
                    || TipoColunas.TIMESTAMP.equals(campos.getTipoColuna())) {
                field
                        .add("p:calendar")
                        .attr("navigator", "true")
                        .attr("locale", "pt")
                        .attr("pattern", getPatternDataByTipoColuna(campos.getTipoColuna()));
            } else {
                field.add("p:inputText");
            }

            // value
            String value = "#{%s}";
            value = String.format(
                    value,
                    WordUtils.uncapitalize(informacoesCodigoFonte.getClassName()) + "Controller." + campos.getField()
            );
            field.attr("value", value);

            // id
            field.attr("id", id);

            // required
            if (campos.getNotNull()) {
                field
                        .attr("required", "true")
                        .attr("requiredMessage", "Preencha o campo " + campos.getDescricao());
            }

            // disabled
            if (campos.getPrimary()) {
                field.attr("disabled", "true");
            }

            if (TipoColunas.DATE.equals(campos.getTipoColuna())
                    || TipoColunas.TIME.equals(campos.getTipoColuna())
                    || TipoColunas.TIMESTAMP.equals(campos.getTipoColuna())) {
                field
                        .add("f:attribute")
                        .attr("name", "ignoreTimeZone")
                        .attr("value", "true");
            }  else if (TipoColunas.DOUBLE.equals(campos.getTipoColuna())
                    || TipoColunas.DECIMAL.equals(campos.getTipoColuna())
                    || TipoColunas.FLOAT.equals(campos.getTipoColuna())) {
                field
                        .attr("styleClass", "alignRight")
                        .add("f:convertNumber")
                        .attr("locale", "pt_BR")
                        .attr("pattern", "#,##0.00");
            } else if ((TipoColunas.CHAR.equals(campos.getTipoColuna())
                    || TipoColunas.VARCHAR.equals(campos.getTipoColuna()))
                    && campos.getSize() != null
                    && campos.getSize() > 0) {
                field.attr("maxlength", campos.getSize());
            }

            columns.append(getXmlByDirectives(field));
        }

        return columns.toString();
    }

}
