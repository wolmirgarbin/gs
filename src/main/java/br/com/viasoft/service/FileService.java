package br.com.viasoft.service;

import br.com.viasoft.enumeration.FileCodigoFonte;

/**
 * Created by gaspar on 07/04/17.
 */
public interface FileService {

    void gravarArquivo(String codigoFonte, FileCodigoFonte file);

    String getStringFromFile(FileCodigoFonte file);
}
