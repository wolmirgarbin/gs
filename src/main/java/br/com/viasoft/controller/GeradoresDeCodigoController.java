package br.com.viasoft.controller;

import br.com.viasoft.enumeration.FileCodigoFonte;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaspar on 10/04/17.
 */
public class GeradoresDeCodigoController implements Initializable, SceneAttributes {

    @FXML JFXToggleButton checkEntity;
    @FXML JFXToggleButton checkData;
    @FXML JFXToggleButton checkService;
    @FXML JFXToggleButton checkRepository;
    @FXML JFXToggleButton checkSql;
    @FXML JFXToggleButton checkJsf;

    @Override
    public String getTitle() {
        return "Geradores";
    }

    @Override
    public boolean allowForward() {
        return checkEntity.isSelected()
                || checkData.isSelected()
                || checkService.isSelected()
                || checkRepository.isSelected()
                || checkSql.isSelected()
                || checkJsf.isSelected();
    }

    @Override
    public String getMessageNotAllwForward() {
        return "Selecione ao menos um dos geradores";
    }

    @Override
    public void execButtonNext() {
        AppController.informacoesCodigoFonte.getFileCodigoFonteList().clear();

        if (checkEntity.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.ENTITY);
        }
        if (checkData.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.DATA);
        }
        if (checkService.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.INTERFACE_SERVICE);
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.SERVICE);
        }
        if (checkRepository.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.INTERFACE_REPOSITORY);
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.REPOSITORY);
        }
        if (checkSql.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.SQL);
        }
        if (checkJsf.isSelected()) {
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.JSF_CONTROLLER);
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.JSF_FORM);
            AppController.informacoesCodigoFonte.getFileCodigoFonteList().add(FileCodigoFonte.JSF_SEARCH);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkEntity.setSelected(fileFoiMarcado(FileCodigoFonte.ENTITY));
        checkData.setSelected(fileFoiMarcado(FileCodigoFonte.DATA));
        checkService.setSelected(fileFoiMarcado(FileCodigoFonte.SERVICE));
        checkRepository.setSelected(fileFoiMarcado(FileCodigoFonte.REPOSITORY));
        checkSql.setSelected(fileFoiMarcado(FileCodigoFonte.SQL));
        checkJsf.setSelected(fileFoiMarcado(FileCodigoFonte.JSF_FORM));
    }

    private boolean fileFoiMarcado(FileCodigoFonte file) {
        return AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(file);
    }

}
