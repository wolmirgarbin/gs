package br.com.viasoft.service;

import br.com.viasoft.model.InformacoesCodigoFonte;

/**
 * Created by gaspar on 07/04/17.
 */
public interface GeradorDeCodigoFonte {

    void gerarCodigosFontes(InformacoesCodigoFonte informacoesCodigoFonte, LogExecucao logExecucao);

}
