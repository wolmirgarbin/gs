package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.model.InformacoesCodigoFonte;
import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.LogExecucao;
import org.apache.commons.lang3.text.WordUtils;
import org.xembly.Directives;
import org.xembly.Xembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by gaspar on 12/04/17.
 */
public class GeradorDeCodigoFonteJsfXhtmlImpl extends GeradorDeCodigoFonteXmlImpl implements GeradorDeCodigoPorTemplate {

    @Override
    public FileWriter getDiretorio(InformacoesCodigoFonte informacoesCodigoFonte, FileCodigoFonte file, LogExecucao logExecucao) throws IOException {
        String diretorio = informacoesCodigoFonte.getDiretorioJsf() +
                File.separator +
                WordUtils.uncapitalize(informacoesCodigoFonte.getClassName()) +
                File.separator;

        File fileDiretorio = new File(diretorio);
        fileDiretorio.mkdirs();

        String diretorioFileWriter = fileDiretorio + File.separator + WordUtils.uncapitalize(informacoesCodigoFonte.getClassName()) + file.getNome();

        return new FileWriter(diretorioFileWriter);
    }

    String getPatternDataByTipoColuna(TipoColunas tipoColunas) {
        if (TipoColunas.TIME.equals(tipoColunas)) {
            return "HH:mm";
        } else if (TipoColunas.TIMESTAMP.equals(tipoColunas)) {
            return "dd/MM/yyyy HH:mm";
        } else {
            return "dd/MM/yyyy";
        }
    }


}
