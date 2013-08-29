/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.i18n;

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
public class i18nManager {

    @Autowired
    private LocaleResolver lr;
    @Autowired
    public MessageSource ms;
    
    public String getMessage(HttpServletRequest request, Object[] args, String code) {
        Locale locale = lr.resolveLocale(request);
        return getMessage(locale, args, code);
    }

    public String getMessage(Locale locale, Object[] args, String code) {
        return ms.getMessage(code, null, locale);
    }

    public Locale getLocale(HttpServletRequest request) {
        return lr.resolveLocale(request);
    }

    public String getParam(String code) {
        Locale l = new Locale("en");
        return ms.getMessage(code, null, l);
    }
}
