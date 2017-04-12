package br.com.viasoft.model;

import br.com.viasoft.enumeration.TipoColunas;
import lombok.Getter;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by gaspar on 07/04/17.
 */
@Getter
public class Campos {

    private String nome;
    private String descricao;
    private TipoColunas tipoColuna;
    private Integer size;
    private Integer scale;
    private Boolean notNull;
    private Boolean primary;
    private Boolean podeRemover;
    private String field;

    public Campos(String nome, String descricao, TipoColunas tipoColuna, Integer size, Integer scale, Boolean notNull, Boolean primary, Boolean podeRemover) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoColuna = tipoColuna;
        this.size = size;
        this.scale = scale;
        this.notNull = notNull;
        this.primary = primary;
        this.podeRemover = podeRemover;
    }

    public String getField() {
        if (field == null) {
            field = nome.toLowerCase();
            field = field.replace("_", " ");
            field = WordUtils.capitalize(field);
            field = field.replace(" ", "");
            field = WordUtils.uncapitalize(field);
        }
        return field;
    }

}
