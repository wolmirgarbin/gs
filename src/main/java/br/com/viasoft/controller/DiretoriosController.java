package br.com.viasoft.controller;

import br.com.viasoft.App;
import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.enumeration.TipoScript;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaspar on 10/04/17.
 */
public class DiretoriosController implements Initializable, SceneAttributes {

    @FXML JFXTextField txtDiretorioSourceRoot;
    @FXML JFXTextField txtDiretorioBancoDeDados;
    @FXML JFXTextField txtDiretorioJsf;
    @FXML JFXButton btnDiretorioSourceRoot;
    @FXML JFXButton btnDiretorioBancoDeDados;
    @FXML JFXButton btnDiretorioJsf;

    @Override
    public String getTitle() {
        return "Diretorios";
    }

    @Override
    public boolean allowForward() {
        return !(!txtDiretorioSourceRoot.isDisable() && (txtDiretorioSourceRoot.getText() == null || txtDiretorioSourceRoot.getText().length() == 0))
                && !(!txtDiretorioBancoDeDados.isDisable() && (txtDiretorioBancoDeDados.getText() == null || txtDiretorioBancoDeDados.getText().length() == 0))
                && !(!txtDiretorioJsf.isDisable() && (txtDiretorioJsf.getText() == null || txtDiretorioJsf.getText().length() == 0));

    }

    @Override
    public String getMessageNotAllwForward() {
        return "Informe todos os diretorios";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.JSF_FORM)) {
            txtDiretorioJsf.setText(AppController.informacoesCodigoFonte.getDiretorioJsf());
        } else {
            txtDiretorioJsf.setText("");
            txtDiretorioJsf.setDisable(true);
            btnDiretorioJsf.setDisable(true);
        }

        if (AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.SQL)
                && !TipoScript.JPA.equals(AppController.informacoesCodigoFonte.getTipoScript())) {
            txtDiretorioBancoDeDados.setText(AppController.informacoesCodigoFonte.getDiretorioBD());
        } else {
            txtDiretorioBancoDeDados.setText("");
            txtDiretorioBancoDeDados.setDisable(true);
            btnDiretorioBancoDeDados.setDisable(true);
        }

        if (AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.ENTITY)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.DATA)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.SERVICE)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.REPOSITORY)) {
            txtDiretorioSourceRoot.setText(AppController.informacoesCodigoFonte.getDiretorio());
        } else {
            txtDiretorioSourceRoot.setText("");
            txtDiretorioSourceRoot.setDisable(true);
            btnDiretorioSourceRoot.setDisable(true);
        }
    }

    @Override
    public void execButtonStep() {
        AppController.informacoesCodigoFonte.setDiretorio(txtDiretorioSourceRoot.getText());
        AppController.informacoesCodigoFonte.setDiretorioBD(txtDiretorioBancoDeDados.getText());
        AppController.informacoesCodigoFonte.setDiretorioJsf(txtDiretorioJsf.getText());
    }

    @FXML
    public void informeODiretorioSourceRoot() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecione o diretório definido como source root do seu projeto");
        File selectedDirectory = chooser.showDialog(App.stage);
        if (selectedDirectory != null) {
            txtDiretorioSourceRoot.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void informeODiretorioBancoDeDados() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecione o arquivo de banco de dados");
        File selectedDirectory = chooser.showOpenDialog(App.stage);
        if (selectedDirectory != null) {
            txtDiretorioBancoDeDados.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void informeODiretorioJsf() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecione o diretório do dos arquivos jsf");
        File selectedDirectory = chooser.showDialog(App.stage);
        if (selectedDirectory != null) {
            txtDiretorioJsf.setText(selectedDirectory.getAbsolutePath());
        }
    }

}
