package br.com.viasoft.model;

import br.com.viasoft.enumeration.FileCodigoFonte;
import br.com.viasoft.enumeration.TipoScript;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaspar on 06/04/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InformacoesCodigoFonte {

    private String diretorio;

    private String diretorioBD;

    private String diretorioJsf;

    private TipoScript tipoScript = TipoScript.FIREBIRD;

    private ObservableList<Campos> camposList = FXCollections.observableArrayList();

    private List<FileCodigoFonte> fileCodigoFonteList = new ArrayList<>();

    private Map<String, String> variaveis = new HashMap<>();

    public String getClassName() {
        return variaveis.get("className");
    }

    public String getPrefixInterface() {
        try {
            return variaveis.get("prefixInterface");
        } catch (Exception e) {
            return null;
        }
    }

    public String getTableName() {
        return variaveis.get("tableName");
    }

}
