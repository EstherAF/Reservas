package com.citius.reservas.repositories;

import com.citius.reservas.models.User;
import java.util.List;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author esther
 */
public interface LdapRepository {
    
    public LdapTemplate getLdapTemplate();

    public void setLdapTemplate(LdapTemplate ldapTemplate);
    
    public List<User> getByUID(String userId);
 
    public List<User> getByGroupUID(String groupId);
}
