package br.com.viasoft.service.impl;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.service.FileService;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

/**
 * Created by gaspar on 07/04/17.
 */
public class FileServiceImpl implements FileService {

    @Override
    public void gravarArquivo(String codigoFonte, FileCodigoFonte file) {
        try {
            String diretorioTemplates = getDiretorioTemplates();
            String arquivoCodigoFonte = getArquivoCodigoFonte(file);

            new java.io.File(diretorioTemplates).mkdirs();
            FileWriter fileWriter = new FileWriter(arquivoCodigoFonte);
            fileWriter.write(codigoFonte);
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getDiretorioTemplates() throws Exception {
        String canonicalPath = new java.io.File(".").getCanonicalPath();
        return canonicalPath + java.io.File.separator + "templates" + java.io.File.separator;
    }

    private String getArquivoCodigoFonte(FileCodigoFonte file) throws Exception {
        return getDiretorioTemplates() + file.getFile();
    }

    @Override
    public String getStringFromFile(FileCodigoFonte file) {
        try {
            String arquivoCodigoFonte = getArquivoCodigoFonte(file);
            java.io.File fileCodigoFonte = new java.io.File(arquivoCodigoFonte);
            if (!fileCodigoFonte.exists()) {
                URL entityUrl = getClass().getClassLoader().getResource(file.getFile());
                InputStream is = entityUrl.openStream();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                String codigoFonte = writer.toString();

                gravarArquivo(codigoFonte, file);

                return codigoFonte;
            } else {
                InputStream inputStream = new FileInputStream(arquivoCodigoFonte);
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer, "UTF-8");
                return writer.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
