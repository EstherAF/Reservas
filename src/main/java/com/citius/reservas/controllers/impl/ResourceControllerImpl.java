package com.citius.reservas.controllers.impl;

import com.citius.reservas.controllers.*;
import com.citius.reservas.models.Resource;
import com.citius.reservas.business.ResourceBusiness;
import com.citius.reservas.business.ResourceGroupBusiness;
import com.citius.reservas.controllers.controllerModel.ResourceCustom;
import com.citius.reservas.controllers.validators.ResourceValidator;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.ResourceGroup;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
public class ResourceControllerImpl implements ResourceController {

    @Autowired
    private ResourceGroupBusiness rgb;
    @Autowired
    private ResourceBusiness rb;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ResourceValidator validator;
    private static final Logger logger = Logger.getLogger(ResourceControllerImpl.class);

       /**
     * ****************** JSON ************************
     */
    @Override
    public List<ResourceGroup> read() {
        List<ResourceGroup> l = rgb.readAll();
        logger.debug("Read all: " + l);
        return l;
    }

    @Override
    public Resource read(Integer id) {
        Resource r = rb.read(id);
        logger.debug("Read " + id + ": " + r);
        return r;
    }

    @Override
    public List<Resource> create(ResourceCustom resourceCustom, BindingResult result)
            throws InputRequestValidationException{
        validator.validate(resourceCustom, result);
        
        if (!result.getAllErrors().isEmpty()) {
            throw new InputRequestValidationException(result.getAllErrors());
        }

        Integer quantity = 1;

        if (resourceCustom.getQuantity() != null
                && resourceCustom.getQuantity() > 0) {
            quantity = resourceCustom.getQuantity();
        }

        List<Resource> l = rb.create(resourceCustom, quantity);

        logger.debug("Created: " + l);
        return l;
    }

    @Override
    public Resource update(Resource resource, BindingResult result) 
            throws InputRequestValidationException{
        validator.validate(resource, result);
        
        if (!result.getAllErrors().isEmpty()) {
            throw new InputRequestValidationException(result.getAllErrors());
        }
        
        Resource r = rb.createOrSave(resource);
        logger.debug("Save: " + r);
        return r;
    }

    @Override
    public Boolean delete(Integer id) throws UnknownResourceException{
        rb.delete(id);
        logger.debug("Delete " + id);
        return true;
    }

    /**
     * ****************** HTML ************************
     */
    @Override
    public String resources() {
        return this.resourcesMonth();
    }

    @Override
    public String resourcesMonth() {
        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        Integer month = now.get(Calendar.MONTH) + 1;

        String url = "/resources/month/" + year + "/" + month;

        return "redirect:" + url;
    }

    @Override
    public String resourcesMonth(Model model, Integer year, Integer month) {
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "avaliability";
    }

    @Override
    public String resourcesWeek() {
        Calendar now = Calendar.getInstance();
        Integer year = now.get(Calendar.YEAR);
        Integer week = now.get(Calendar.WEEK_OF_YEAR);

        String url = "/resources/month/" + year + "/" + week;

        return "redirect:" + url;
    }
    
    @Override
    public String resourcesWeek(Model model, Integer year, Integer week) {
        model.addAttribute("year", year);
        model.addAttribute("week", week);
        model.addAttribute("view", "agendaWeek");
        return "avaliability";
    }

    @Override
    public String resourcesAdmin(Model model) {
        List<ResourceGroup> l = rgb.readAll();

        logger.debug(l);

        String json = null;
        try {
            json = mapper.writeValueAsString(l);
        } catch (IOException ex) {
            logger.error(ex, ex);
        }
        model.addAttribute("resourcesJson", json);
        return "resources";
    }

//    @Override
//    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException {
//        throw new NoSuchRequestHandlingMethodException(request);
//    }
}
