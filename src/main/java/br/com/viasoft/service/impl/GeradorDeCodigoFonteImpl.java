package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.model.InformacoesCodigoFonte;
import br.com.viasoft.service.FileService;
import br.com.viasoft.service.GeradorDeCodigoFonte;
import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.LogExecucao;

import java.io.FileWriter;

/**
 * Created by gaspar on 06/04/17.
 */
public class GeradorDeCodigoFonteImpl implements GeradorDeCodigoFonte {

    @Override
    public void gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao) {
        try {
            FileService fileService = new FileServiceImpl();

            for (FileCodigoFonte file : informacoesCodigoFonte.getFileCodigoFonteList()) {
                logExecucao.log("Gerando codigo fonte do tipo " + file.name() + ".");

                String codigoFonteGerado = "";
                if (file.getFile() != null && file.getFile().length() > 0) {
                    codigoFonteGerado = fileService.getStringFromFile(file);
                }

                GeradorDeCodigoPorTemplate geradorDeCodigoPorTemplate;
                if (file.getGeradorDeCodigoPorTemplate() != null) {
                    geradorDeCodigoPorTemplate = file.getGeradorDeCodigoPorTemplate();
                } else {
                    geradorDeCodigoPorTemplate = new DefaultGeradorDeCodigoPorTemplate();
                }

                codigoFonteGerado = geradorDeCodigoPorTemplate.gerarCodigosFontes(informacoesCodigoFonte, logExecucao, codigoFonteGerado);

                FileWriter fileWriter = geradorDeCodigoPorTemplate.getDiretorio(informacoesCodigoFonte, file, logExecucao);
                if (fileWriter != null) {
                    fileWriter.write(codigoFonteGerado);
                    fileWriter.close();
                }

                logExecucao.log("Codigo fonte do tipo " + file.name() + " gerado com sucesso!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

