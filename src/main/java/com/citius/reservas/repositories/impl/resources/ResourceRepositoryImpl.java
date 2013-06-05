/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl.resources;

import com.citius.reservas.models.Resource;
import com.citius.reservas.repositories.GenericResourceRepository;
import com.citius.reservas.repositories.impl.GenericResourceRepositoryImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther
 */
@Repository("resourceRepository")
public class ResourceRepositoryImpl extends GenericResourceRepositoryImpl<Resource> implements GenericResourceRepository<Resource>{
    private static final Logger logger = Logger.getLogger(ResourceRepositoryImpl.class);
}
