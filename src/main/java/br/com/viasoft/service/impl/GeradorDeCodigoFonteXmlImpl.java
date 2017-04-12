package br.com.viasoft.service.impl;

import org.xembly.Directives;
import org.xembly.Xembler;

/**
 * Created by gaspar on 12/04/17.
 */
public class GeradorDeCodigoFonteXmlImpl {

    String getXmlByDirectives(Directives directives) throws Exception {
        return new Xembler(directives).xml().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
    }

}
