package com.citius.reservas.security;

import com.citius.reservas.models.User;
import com.citius.reservas.business.AccessBusiness;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author esther
 */
@Service
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AccessBusiness accessService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) authentication.getPrincipal();
        User u = accessService.getLoggedUser(userDetails);
        System.out.println(u.getUniqueName() + " logged"); //LdC
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
