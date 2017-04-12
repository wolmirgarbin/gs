package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.model.Campos;
import br.com.viasoft.model.InformacoesCodigoFonte;
import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.LogExecucao;

/**
 * Created by gaspar on 11/04/17.
 */
public class GeradorDeCodigoFonteEntitympl implements GeradorDeCodigoPorTemplate {

    @Override
    public String gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao, String codigoFonteGerado) {
        codigoFonteGerado = varReplace(informacoesCodigoFonte, codigoFonteGerado);
        return codigoFonteGerado.replace("@{fieldEntity}", getCamposEntity(informacoesCodigoFonte));
    }

    private String getCamposEntity(InformacoesCodigoFonte informacoesCodigoFonte) {
        StringBuilder camposEntity = new StringBuilder();

        for (Campos campo : informacoesCodigoFonte.getCamposList()) {
            if (campo.getPrimary()) {
                camposEntity.append("\n\t");
                camposEntity.append("@Id");
                camposEntity.append("\n\t");
                camposEntity.append("@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=\"").append(informacoesCodigoFonte.getTableName().toUpperCase()).append("_SEQ\")");
            }

            if (verificaSeAdicionaTemporalType(campo.getTipoColuna())) {
                camposEntity.append("\n\t");
                camposEntity.append("@Temporal(TemporalType.").append(getTemporalTypeByTipoColumn(campo.getTipoColuna())).append(")");
            }

            camposEntity.append("\n\t");
            camposEntity.append("@Column(name=\"").append(campo.getNome()).append("\"");

            if (campo.getTipoColuna().isSize()) {
                camposEntity.append(", length=").append(campo.getSize());
            }

            if (campo.getNotNull()) {
                camposEntity.append(", nullable=false");
            }

            camposEntity.append(")");
            camposEntity.append("\n\t");

            camposEntity.append("private ")
                    .append(getTipoJavaByTipoColumn(campo.getTipoColuna()))
                    .append(" ")
                    .append(campo.getField())
                    .append(";");

            camposEntity.append("\n");
        }

        return camposEntity.toString();
    }

    private boolean verificaSeAdicionaTemporalType(TipoColunas tipoColunas) {
        return TipoColunas.DATE.equals(tipoColunas)
                || TipoColunas.TIME.equals(tipoColunas)
                || TipoColunas.TIMESTAMP.equals(tipoColunas);
    }

    private String getTemporalTypeByTipoColumn(TipoColunas tipoColunas) {
        if (TipoColunas.TIME.equals(tipoColunas)) {
            return "TIME";
        } else if (TipoColunas.TIMESTAMP.equals(tipoColunas)) {
            return "TIMESTAMP";
        }
        return "DATE";
    }

    private String getTipoJavaByTipoColumn(TipoColunas tipoColunas) {
        if (TipoColunas.SMALLINT.equals(tipoColunas)) {
            return "Short";
        } else if (TipoColunas.INTEGER.equals(tipoColunas)) {
            return "Integer";
        } else if (TipoColunas.BIGINT.equals(tipoColunas)
                || TipoColunas.NUMERIC.equals(tipoColunas)) {
            return "Long";
        } else if (TipoColunas.FLOAT.equals(tipoColunas)) {
            return "Float";
        } else if (TipoColunas.DOUBLE.equals(tipoColunas)
                || TipoColunas.DECIMAL.equals(tipoColunas)) {
            return "Double";
        } else if (TipoColunas.DATE.equals(tipoColunas)
                || TipoColunas.TIME.equals(tipoColunas)
                || TipoColunas.TIMESTAMP.equals(tipoColunas)) {
            return "Date";
        }
        return "String";
    }

}
