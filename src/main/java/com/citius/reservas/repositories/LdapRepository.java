package com.citius.reservas.repositories;

import com.citius.reservas.models.User;
import java.util.List;
import org.springframework.ldap.core.LdapTemplate;

/**
 *
 * @author esther
 */
public interface LdapRepository {
    
    /**
     *
     * @return
     */
    public LdapTemplate getLdapTemplate();

    /**
     *
     * @param ldapTemplate
     */
    public void setLdapTemplate(LdapTemplate ldapTemplate);
    
    /**
     *
     * @param userId
     * @return
     */
    public List<User> getByUID(String userId);
 
    /**
     *
     * @param groupId
     * @return
     */
    public List<User> getByGroupUID(String groupId);
}
