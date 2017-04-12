package br.com.viasoft.controller;

import br.com.viasoft.App;
import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.model.InformacoesCodigoFonte;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.*;

/**
 * Created by gaspar on 10/04/17.
 */
public class AppController implements Initializable {

    static InformacoesCodigoFonte informacoesCodigoFonte = new InformacoesCodigoFonte();

    @FXML Label labelGeradores;
    @FXML Label labelEdicaoTemplates;
    @FXML Label labelInputVariaveis;
    @FXML Label labelBancoDeDados;
    @FXML Label labelDiretorios;
    @FXML Label labelExecucacao;
    @FXML Label labelErro;
    @FXML Pane paneConteudo;
    @FXML Pane paneTopo;
    @FXML JFXButton btnAnterior;
    @FXML JFXButton btnProximo;

    private SceneAttributes sceneAttributes = null;

    private Map<Integer, Step> mapPassos = new HashMap<>();
    private int passoAtual = 0;
    private double xOffset;
    private double yOffset;

    @AllArgsConstructor
    @Getter
    private class Step {
        private String fxml;
        private Label label;
        private FileCodigoFonte fileCodigoFonte;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneTopo.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        paneTopo.setOnMouseDragged(event -> {
            App.stage.setX(event.getScreenX() - xOffset);
            App.stage.setY(event.getScreenY() - yOffset);
        });

        mapPassos.put(1, new Step("geradoresDeCodigo.fxml", labelGeradores, null));
        mapPassos.put(2, new Step("editTemplate.fxml", labelEdicaoTemplates, null));
        mapPassos.put(3, new Step("inputVariaveis.fxml", labelInputVariaveis, null));
        mapPassos.put(4, new Step("gerenciarTabela.fxml", labelBancoDeDados, FileCodigoFonte.SQL));
        mapPassos.put(5, new Step("diretorios.fxml", labelDiretorios, null));
        mapPassos.put(6, new Step("execucao.fxml", labelExecucacao, null));

        proximo();
    }

    private void addMsgError() {
        labelErro.setText(sceneAttributes.getMessageNotAllwForward());
        labelErro.setVisible(true);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                labelErro.setVisible(false);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000);
    }

    @FXML
    public void buttonMinimizar() {
        Stage stage = (Stage) App.stage.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void buttonFechar() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void proximo() {
        if (sceneAttributes != null) {
            if (!sceneAttributes.allowForward()) {
                addMsgError();
                return;
            }

            sceneAttributes.execButtonNext();
            sceneAttributes.execButtonStep();
        }

        desabilitaLabel();
        passoAtual++;
        changeConteudo(true);
    }

    @FXML
    public void anterior() {
        if (sceneAttributes != null) {
            sceneAttributes.execButtonPrev();
            sceneAttributes.execButtonStep();
        }

        desabilitaLabel();
        passoAtual--;
        changeConteudo(false);
    }

    private void desabilitaLabel() {
        Step stepAnterior = mapPassos.get(passoAtual);
        if (stepAnterior != null) {
            stepAnterior.getLabel().setDisable(true);
        }
    }

    private void desabilitaPassos() {
        if (passoAtual > 1) {
            btnAnterior.setDisable(false);
        } else {
            btnAnterior.setDisable(true);
        }
        if (mapPassos.get(passoAtual +1) == null) {
            btnProximo.setDisable(true);
        } else {
            btnProximo.setDisable(false);
        }
    }

    private void changeConteudo(boolean next) {
        try {
            labelErro.setVisible(false);

            desabilitaPassos();

            Step step = mapPassos.get(passoAtual);
            if (step.getFileCodigoFonte() != null) {
                if (!informacoesCodigoFonte.getFileCodigoFonteList().contains(step.getFileCodigoFonte())) {
                    if (next) {
                        passoAtual++;
                    } else {
                        passoAtual--;
                    }
                    changeConteudo(next);
                    return;
                }
            }

            step.getLabel().setDisable(false);

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(step.getFxml()));
            Parent root = loader.load();
            paneConteudo.getChildren().clear();
            paneConteudo.getChildren().add(root);

            sceneAttributes = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
