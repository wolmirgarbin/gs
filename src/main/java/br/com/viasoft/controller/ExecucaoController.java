package br.com.viasoft.controller;

import br.com.viasoft.service.GeradorDeCodigoFonte;
import br.com.viasoft.service.impl.GeradorDeCodigoFonteImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

/**
 * Created by gaspar on 10/04/17.
 */
public class ExecucaoController implements SceneAttributes {

    @FXML
    JFXTextArea txtLog;
    @FXML JFXButton btnIniciar;

    @Override
    public String getTitle() {
        return "Execucao";
    }

    @FXML
    public void iniciar() {
        txtLog.setText("");
        txtLog.setEditable(false);
        btnIniciar.setDisable(true);

        GeradorDeCodigoFonte geradorDeCodigoFonte = new GeradorDeCodigoFonteImpl();
        geradorDeCodigoFonte.gerarCodigosFontes(AppController.informacoesCodigoFonte, log -> txtLog.appendText(log + "\n"));

        btnIniciar.setDisable(false);
    }
}
