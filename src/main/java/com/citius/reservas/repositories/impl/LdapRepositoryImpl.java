/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl;

import com.citius.reservas.repositories.LdapRepository;
import com.citius.reservas.models.User;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author esther
 */
@Repository
public class LdapRepositoryImpl implements LdapRepository{
    
    @Autowired
    private LdapTemplate ldapTemplate;
    
    @Override
    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    @Override
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    
    @Override
    public List<User> getByUID(String userId) {
        return ldapTemplate.search("", "(uid=" + userId + ")", personMapper());
        
    }
 
    @Override
    public List<User> getByGroupUID(String groupId) {
        final List<User> persons = new ArrayList<User>();
        final List<String> userIds = (List<String>) ldapTemplate.search("", "(&(objectClass=group)(cn=" + groupId + "))", groupMapper()).get(0);
        for (String userId : userIds) {
            persons.add(getByUID(userId).get(0));
        }
        return persons;
    }
     
    private AttributesMapper personMapper() {
        return new AttributesMapper() {
             
            @Override
            //Aqui devuelve Usuario
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                final User person = new User();
                
                /* Añadir aquí los atributos que se quieren obtener del Ldap, y asignarlos a propiedades de la clase User.
                 * Si la clase User no tiene las propiedades necesarias, añadirlas*/
                person.setUniqueName((String) attributes.get("uid").get());
                person.setFullName((String) attributes.get("cn").get());
                person.setEmail( (String) attributes.get("mail").get());
                return person;
            }
        };
    }
     
    private AttributesMapper groupMapper() {
        return new AttributesMapper() {
             
            @Override
            public List<String> mapFromAttributes(final Attributes attributes) throws NamingException {
                final List<String> userIds = new ArrayList<String>();
                final NamingEnumeration<?> members = attributes.get("member").getAll();
                while (members.hasMore()) {
                    String userId = members.next().toString().split(",")[0].split("=")[1];
                    userIds.add(userId);
                }
                return userIds;
            }
        };
    }
}
