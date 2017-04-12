package br.com.viasoft.service;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.model.InformacoesCodigoFonte;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by gaspar on 11/04/17.
 */
public interface GeradorDeCodigoPorTemplate {

    default String gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao, String codigoFonteGerado) {
        return varReplace(informacoesCodigoFonte, codigoFonteGerado);
    }

    default String varReplace(InformacoesCodigoFonte informacoesCodigoFonte, String codigoFonteGerado) {
        for (String variavel : informacoesCodigoFonte.getVariaveis().keySet()) {
            String variavelReplace = "${%s}";
            variavelReplace = String.format(variavelReplace, variavel);

            codigoFonteGerado = codigoFonteGerado.replace(variavelReplace, informacoesCodigoFonte.getVariaveis().get(variavel));
        }
        return codigoFonteGerado;
    }

    default FileWriter getDiretorio(InformacoesCodigoFonte informacoesCodigoFonte, FileCodigoFonte file, LogExecucao logExecucao) throws IOException {
        String diretorio = informacoesCodigoFonte.getDiretorio() + File.separator;

        if (file.getPacote() != null && file.getPacote().length() > 0) {
            diretorio += file.getPacote().replace(".", File.separator) + File.separator;
        }

        File fileDiretorio = new File(diretorio);
        fileDiretorio.mkdirs();

        String diretorioFileWriter = diretorio;
        if (file.name().contains("INTERFACE") && informacoesCodigoFonte.getPrefixInterface() != null) {
            diretorioFileWriter += informacoesCodigoFonte.getPrefixInterface();
        }
        diretorioFileWriter += informacoesCodigoFonte.getClassName() + file.getNome();

        return new FileWriter(diretorioFileWriter);
    }

}
