package br.com.viasoft.controller;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.service.FileService;
import br.com.viasoft.service.impl.FileServiceImpl;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaspar on 10/04/17.
 */
public class InputVariaveisController implements Initializable, SceneAttributes {

    @FXML AnchorPane anchorPane;

    private List<Variaveis> variaveisList = new ArrayList<>();

    @Override
    public String getTitle() {
        return "Input de variaveis";
    }

    @Override
    public void execButtonStep() {
        for (Variaveis variaveis : variaveisList) {
            String text = variaveis.getTextField().getText();
            if (text == null) {
                text = "";
            }

            AppController.informacoesCodigoFonte.getVariaveis().put(
                    variaveis.getVariavel(),
                    text.trim()
            );
        }
    }

    @Override
    public boolean allowForward() {
        for (Variaveis variaveis : variaveisList) {
            if (variaveis.getTextField().getText() == null || variaveis.getTextField().getText().length() == 0) {
                return false;
            }

        }
        return true;
    }

    @Override
    public String getMessageNotAllwForward() {
        return "Informe o valor de todas as variaveis";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileService fileService = new FileServiceImpl();

        List<String> variaveisEncontradas = new ArrayList<>();
        if (AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.ENTITY)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.DATA)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.SERVICE)
                || AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.REPOSITORY)) {
            variaveisEncontradas.add("className");
        }

        if (AppController.informacoesCodigoFonte.getFileCodigoFonteList().contains(FileCodigoFonte.SQL)) {
            variaveisEncontradas.add("tableName");
        }

        AppController.informacoesCodigoFonte.getFileCodigoFonteList()
                .stream()
                .filter(FileCodigoFonte::isPossuiTemplate)
                .forEach(f -> {
                    String stringFromFile = fileService.getStringFromFile(f);
                    String regex = "\\$\\{(\\w+)\\}+";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(stringFromFile);
                    while (m.find()) {
                        variaveisEncontradas.add(m.group(1));
                    }
                });

        for (String variavelEncontrada : variaveisEncontradas) {
            if (!AppController.informacoesCodigoFonte.getVariaveis().containsKey(variavelEncontrada)) {
                AppController.informacoesCodigoFonte.getVariaveis().put(variavelEncontrada, null);
            }
        }

        List<String> variaveisRemovidas = new ArrayList<>();
        for (String variavel : AppController.informacoesCodigoFonte.getVariaveis().keySet()) {
            if (!variaveisEncontradas.contains(variavel)) {
                variaveisRemovidas.add(variavel);
            }
        }

        for (String variaveisRemovida : variaveisRemovidas) {
            AppController.informacoesCodigoFonte.getVariaveis().remove(variaveisRemovida);
        }

        double layoutY = 0;
        for (String variavel : AppController.informacoesCodigoFonte.getVariaveis().keySet()) {
            Label label = new Label(variavel);
            label.setLayoutX(27);
            if (layoutY > 0) {
                label.setLayoutY(layoutY = layoutY + 48);
            } else {
                label.setLayoutY(layoutY = layoutY + 15);
            }
            anchorPane.getChildren().add(label);

            JFXTextField textField = new JFXTextField();
            textField.setId(variavel);
            textField.setLayoutX(27);
            textField.setLayoutY(layoutY=layoutY+20);
            textField.setPrefHeight(24);
            textField.setPrefWidth(870);
            textField.setText(AppController.informacoesCodigoFonte.getVariaveis().get(variavel));
            anchorPane.getChildren().add(textField);

            variaveisList.add(new Variaveis(textField, variavel));
        }

        anchorPane.setPrefHeight(layoutY+50);
        anchorPane.setPrefWidth(890);
    }

    @AllArgsConstructor
    @Getter
    private class Variaveis {
        private JFXTextField textField;
        private String variavel;
    }

}
