/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.repositories.impl.resources;

import com.citius.reservas.models.ResourceGroup;
import com.citius.reservas.repositories.GenericResourceRepository;
import com.citius.reservas.repositories.impl.GenericResourceRepositoryImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Esther
 */
@Repository("resourceGroupRepository")
public class ResourceGroupRepositoryImpl extends GenericResourceRepositoryImpl<ResourceGroup> implements GenericResourceRepository<ResourceGroup>{
    private static final Logger logger = Logger.getLogger(ResourceGroupRepositoryImpl.class);
}
