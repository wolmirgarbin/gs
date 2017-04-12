package br.com.viasoft.enumeration;

import br.com.viasoft.service.GeradorDeCodigoPorTemplate;
import br.com.viasoft.service.impl.GeradorDeCodigoFonteEntitympl;
import br.com.viasoft.service.impl.GeradorDeCodigoFonteJsfFormImpl;
import br.com.viasoft.service.impl.GeradorDeCodigoFonteJsfSearchImpl;
import br.com.viasoft.service.impl.GeradorDeCodigoFonteSqlImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileCodigoFonte {

    ENTITY("entity.txt", ".java", "entity", true, new GeradorDeCodigoFonteEntitympl()),
    DATA("data.txt", "Data.java", "data", true, null),
    INTERFACE_SERVICE("interfaceService.txt", "Service.java", "service", true, null),
    SERVICE("service.txt", "ServiceImpl.java", "service.impl", true, null),
    INTERFACE_REPOSITORY("interfaceRepository.txt", "Repository.java", "repository", true, null),
    REPOSITORY("repository.txt", "RepositoryImpl.java", "repository.impl", true, null),
    SQL(null, null, null, false, new GeradorDeCodigoFonteSqlImpl()),
    JSF_SEARCH("jsf_search.txt", "Search.xhtml", null, true, new GeradorDeCodigoFonteJsfSearchImpl()),
    JSF_FORM("jsf_form.txt", "Form.xhtml", null, true, new GeradorDeCodigoFonteJsfFormImpl()),
    JSF_CONTROLLER("jsf_controller.txt", "Controller.java", "controller", true, null);

    private String file;
    private String nome;
    private String pacote;
    private boolean possuiTemplate;
    private GeradorDeCodigoPorTemplate geradorDeCodigoPorTemplate;

}