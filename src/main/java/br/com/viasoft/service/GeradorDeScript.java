package br.com.viasoft.service;

import br.com.viasoft.model.Campos;

import java.util.List;

/**
 * Created by gaspar on 07/04/17.
 */
public interface GeradorDeScript {

    String getScript(String tabela, List<Campos> camposList);

}
