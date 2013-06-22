/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.helper;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class i18nControllerHelper {

    @Autowired
    private LocaleResolver lr;
    @Autowired
    public MessageSource ms;

    public String getMessage(HttpServletRequest request, String code) {
        Locale locale = lr.resolveLocale(request);
        return getMessage(locale, code);
    }
    
    public String getMessage(Locale locale, String code) {
        return ms.getMessage(code, null, locale);
    }
    
    public Locale getLocale(HttpServletRequest request) {
        return lr.resolveLocale(request);
    }
    
    public String getParam(String code) {
        Locale l = new Locale("es");
        return ms.getMessage(code,null,l);
    }
}
