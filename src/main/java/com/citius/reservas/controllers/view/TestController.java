/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.view;

import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.controllers.CustomJsonMapper;
import com.citius.reservas.models.ReservationInstance;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Esther
 */
@Controller
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class TestController {
    
    @Autowired
    private ReservationBusiness rb;
    
    @Autowired
    private CustomJsonMapper mapper;
    
    private static final Logger logger = Logger.getLogger(TestController.class);
    
    @RequestMapping("/test")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String loginFailure(Model model) {
        List<ReservationInstance> l = rb.readAll();
        logger.debug("Found");
        String json = null;
        try {
            json = mapper.writeValueAsString(l);
        } catch (JsonGenerationException ex) {
            logger.error(ex, ex);
        } catch (JsonMappingException ex) {
            logger.error(ex, ex);
        } catch (IOException ex) {
            logger.error(ex, ex);
        }
        logger.debug("Json:"+json);
        model.addAttribute("json", json);
        model.addAttribute("test", "Otra cosa");
        return "hello";
    }
    
}
