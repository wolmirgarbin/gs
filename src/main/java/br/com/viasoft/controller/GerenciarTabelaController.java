package br.com.viasoft.controller;

import br.com.viasoft.enumeration.TipoColunas;
import br.com.viasoft.enumeration.TipoScript;
import br.com.viasoft.model.Campos;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gaspar on 07/04/17.
 */
public class GerenciarTabelaController implements Initializable, SceneAttributes {

    @FXML JFXTextField txtNome;
    @FXML JFXTextField txtDescricao;
    @FXML JFXTextField txtSize;
    @FXML JFXTextField txtScale;
    @FXML JFXComboBox<TipoColunas> comboTipo;
    @FXML JFXCheckBox checkNotNull;
    @FXML TableView<Campos> tableView;
    @FXML JFXComboBox<TipoScript> comboTipoScript;

    @Override
    public String getTitle() {
        return "Banco de dados";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manipulaComboTipo();
        manipulaComboTipoScript();
        manipulaTabela();
    }

    private void manipulaComboTipoScript() {
        comboTipoScript.getItems().addAll(TipoScript.values());
        comboTipoScript.setValue(AppController.informacoesCodigoFonte.getTipoScript());
        comboTipoScript.valueProperty().addListener(observable -> {
            if (comboTipoScript.getValue() != null && comboTipoScript.getValue().toString().length() > 0) {
                TipoScript tipoScript = TipoScript.valueOf(comboTipoScript.getValue().toString());
                AppController.informacoesCodigoFonte.setTipoScript(tipoScript);
            }
        });
    }

    private void manipulaComboTipo() {
        comboTipo.getItems().addAll(TipoColunas.values());

        comboTipo.valueProperty().addListener(observable -> {
            if (comboTipo.getValue() != null && comboTipo.getValue().toString().length() > 0) {
                TipoColunas tipoColunas = TipoColunas.valueOf(comboTipo.getValue().toString());
                txtSize.setDisable(!tipoColunas.isSize());
                txtScale.setDisable(!tipoColunas.isScale());

                if (txtSize.isDisable()) {
                    txtSize.setText("");
                }
                if (txtScale.isDisable()) {
                    txtScale.setText("");
                }
            }
        });
    }

    private void adicionarCampoPK() {
        if (AppController.informacoesCodigoFonte.getCamposList().isEmpty()) {
            Campos campoId = new Campos(
                    "ID",
                    "Código",
                    TipoColunas.NUMERIC,
                    18,
                    0,
                    true,
                    true,
                    false
            );
            AppController.informacoesCodigoFonte.getCamposList().add(campoId);
        }
    }

    private void contextoMenuDaTabela() {
        ContextMenu menu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Editar");
        MenuItem removeMenuItem = new MenuItem("Remover");
        menu.getItems().addAll(editMenuItem, removeMenuItem);
        tableView.setContextMenu(menu);

        removeMenuItem.setOnAction(removerCampo());

        editMenuItem.setOnAction(editarCampo());
    }

    private void manipulaTabela() {
        TableColumn<Campos, Boolean> columnPrimary = new TableColumn<>("PK");
        TableColumn<Campos, String> columnNome = new TableColumn<>("Nome");
        TableColumn<Campos, String> columnDescricao = new TableColumn<>("Descrição");
        TableColumn<Campos, TipoColunas> columnTipo = new TableColumn<>("Tipo");
        TableColumn<Campos, Integer> columnSize = new TableColumn<>("Size");
        TableColumn<Campos, Integer> columnScale = new TableColumn<>("Scale");
        TableColumn<Campos, Boolean> columnNotNull = new TableColumn<>("Not Null");
        columnPrimary.setCellValueFactory(new PropertyValueFactory<>("primary"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("tipoColuna"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        columnScale.setCellValueFactory(new PropertyValueFactory<>("scale"));
        columnNotNull.setCellValueFactory(new PropertyValueFactory<>("notNull"));

        defineLarguraColunas(columnPrimary, 50);
        defineLarguraColunas(columnTipo, 200);
        defineLarguraColunas(columnSize, 120);
        defineLarguraColunas(columnScale, 100);
        defineLarguraColunas(columnNotNull, 100);

        tableView.getColumns().setAll(columnPrimary, columnNome, columnDescricao, columnTipo, columnSize, columnScale, columnNotNull);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(AppController.informacoesCodigoFonte.getCamposList());

        contextoMenuDaTabela();

        adicionarCampoPK();
    }

    private EventHandler<ActionEvent> removerCampo() {
        return event -> {
            Campos campoSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (campoSelecionado.getPodeRemover()) {
                tableView.getItems().remove(campoSelecionado);
            }
        };
    }

    private EventHandler<ActionEvent> editarCampo() {
        return event -> {
            Campos campoSelecionado = tableView.getSelectionModel().getSelectedItem();
            if (campoSelecionado.getPodeRemover()) {
                txtNome.setText(campoSelecionado.getNome());
                txtDescricao.setText(campoSelecionado.getDescricao());
                comboTipo.setValue(campoSelecionado.getTipoColuna());
                txtSize.setText(campoSelecionado.getSize() != null ? campoSelecionado.getSize().toString() : "");
                txtScale.setText(campoSelecionado.getScale() != null ? campoSelecionado.getScale().toString() : "");
                checkNotNull.setSelected(campoSelecionado.getNotNull());
                tableView.getItems().remove(campoSelecionado);
            }
        };
    }

    private void defineLarguraColunas(TableColumn tableColumn, double width) {
        tableColumn.setPrefWidth(width);
        tableColumn.setMinWidth(width);
        tableColumn.setMaxWidth(width);
    }

    private Integer getSize(TipoColunas tipoColuna) {
        if (tipoColuna.isSize()) {
            if (txtSize.getText().length() == 0) {
                if (TipoColunas.VARCHAR.equals(tipoColuna)) {
                    return 255;
                } else if (TipoColunas.CHAR.equals(tipoColuna)) {
                    return 10;
                } else if (TipoColunas.BLOB.equals(tipoColuna)) {
                    return 80;
                } else if (TipoColunas.DECIMAL.equals(tipoColuna)
                        || TipoColunas.NUMERIC.equals(tipoColuna)) {
                    return 15;
                }
            } else {
                return Integer.valueOf(txtSize.getText().trim());
            }
        }
        return null;
    }

    private Integer getScale(TipoColunas tipoColuna) {
        if (tipoColuna.isScale()) {
            if (txtScale.getText().length() == 0) {
                return 2;
            } else {
                return Integer.valueOf(txtScale.getText().trim());
            }
        }
        return null;
    }

    private TipoColunas getTipoColuna() {
        return TipoColunas.valueOf(comboTipo.getValue().toString());
    }

    @FXML
    public void adicionar() {
        if (txtNome.getText().length() > 0
                && txtDescricao.getText().length() > 0
                && comboTipo.getValue() != null
                && comboTipo.getValue().toString().length() > 0) {
            TipoColunas tipoColuna = getTipoColuna();
            Integer size = getSize(tipoColuna);
            Integer scale = getScale(tipoColuna);

            Campos campos = new Campos(
                    txtNome.getText().toUpperCase(),
                    txtDescricao.getText(),
                    tipoColuna,
                    size,
                    scale,
                    checkNotNull.isSelected(),
                    false,
                    true
            );

            AppController.informacoesCodigoFonte.getCamposList().add(campos);
            reset();
        }
    }

    public void reset() {
        txtNome.setText("");
        txtDescricao.setText("");
        comboTipo.setValue(null);
        txtSize.setText("");
        txtScale.setText("");
        checkNotNull.setSelected(false);
        txtNome.requestFocus();
    }

}
