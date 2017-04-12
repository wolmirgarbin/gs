package br.com.viasoft.controller;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.service.FileService;
import br.com.viasoft.service.impl.FileServiceImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by gaspar on 06/04/17.
 */
public class EditTemplateController implements Initializable, SceneAttributes {

    @FXML JFXTextArea txtCodigoFonte;

    @FXML JFXComboBox<FileCodigoFonte> btnOpcoes;

    private FileCodigoFonte file;

    private FileService fileService = new FileServiceImpl();

    @Override
    public String getTitle() {
        return "Edição de templates";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOpcoes.valueProperty().addListener(observable -> {
            if (btnOpcoes.getValue() != null) {
                carregarCodigoFonte(btnOpcoes.getValue());
            }
        });

        if (!AppController.informacoesCodigoFonte.getFileCodigoFonteList().isEmpty()) {
            List<FileCodigoFonte> fileCodigoFonteComTemplateList = AppController.informacoesCodigoFonte.getFileCodigoFonteList()
                    .stream()
                    .filter(FileCodigoFonte::isPossuiTemplate)
                    .collect(Collectors.toList());
            btnOpcoes.getItems().addAll(fileCodigoFonteComTemplateList);

            FileCodigoFonte fileCodigoFonte = AppController.informacoesCodigoFonte.getFileCodigoFonteList().get(0);
            btnOpcoes.setValue(fileCodigoFonte);
        }
    }

    private void carregarCodigoFonte(FileCodigoFonte file) {
        execButtonStep();
        this.file = file;
        String conteudo = fileService.getStringFromFile(this.file);
        txtCodigoFonte.setText(conteudo);
    }

    @Override
    public void execButtonStep() {
        if (file != null) {
            fileService.gravarArquivo(txtCodigoFonte.getText(), file);
        }
    }

}
