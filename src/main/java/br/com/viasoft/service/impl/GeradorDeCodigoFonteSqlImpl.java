package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.model.InformacoesCodigoFonte;
import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.LogExecucao;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by gaspar on 11/04/17.
 */
public class GeradorDeCodigoFonteSqlImpl implements GeradorDeCodigoPorTemplate {

    @Override
    public String gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao, String codigoFonteGerado) {
        if (informacoesCodigoFonte.getTipoScript().getGeradorDeScript() != null) {
            String script = "\n\n------Script abaixo foi inserido pelo gerador de código viasoft------\n\n";
            script += informacoesCodigoFonte.getTipoScript().getGeradorDeScript().getScript(informacoesCodigoFonte.getTableName(), informacoesCodigoFonte.getCamposList());
            script += "\n\n------Script acima foi inserido pelo gerador de código viasoft------\n\n";
            return script;
        }
        return null;
    }

    @Override
    public FileWriter getDiretorio(InformacoesCodigoFonte informacoesCodigoFonte, FileCodigoFonte file, LogExecucao logExecucao) throws IOException {
        if (informacoesCodigoFonte.getTipoScript().getGeradorDeScript() != null) {
            return new FileWriter(informacoesCodigoFonte.getDiretorioBD(), true);
        }
        return null;
    }
}
