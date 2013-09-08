package com.citius.reservas.controllers.impl;

import com.citius.reservas.controllers.*;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

@Controller
public class ResourcesGroupsControllerImpl implements ResourcesGroupsController {

    @Autowired
    private ResourceGroupBusiness rgs;
    private static final Logger logger = Logger.getLogger(ResourcesGroupsControllerImpl.class);

    @Override
    public List<ResourceGroup> read() {
        return rgs.readAll();
    }

    @Override
    public ResourceGroup read(Integer id) throws UnknownResourceException{
        return rgs.read(id);
    }

    @Override
    public ResourceGroup create(ResourceGroup resourceGroup, BindingResult result) throws InputRequestValidationException {
        if (!result.getAllErrors().isEmpty()) {
            throw new InputRequestValidationException(result.getAllErrors());
        }

        return rgs.create(resourceGroup);
    }

    @Override
    public ResourceGroup update(ResourceGroup resourceGroup, BindingResult result) throws InputRequestValidationException {
        if (!result.getAllErrors().isEmpty()) {
            throw new InputRequestValidationException(result.getAllErrors());
        }

        logger.debug("update " + resourceGroup);
        return rgs.save(resourceGroup);
    }

    @Override
    public Boolean deleteOnlyGroup(Integer id) throws UnknownResourceException{
        rgs.delete(id);
        logger.debug("Delete only group" + id);
        return true;
        
    }

    @Override
    public Boolean delete(Integer id) throws UnknownResourceException{
        rgs.deleteWithResources(id);
        logger.debug("Delete grup with childs" + id);
        return true;
    }

//    @Override
//    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException {
//        throw new NoSuchRequestHandlingMethodException(request);
//    }
}
