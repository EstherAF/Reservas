///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.citius.reservas.controllers.view;
//
//import com.citius.reservas.business.ResourceGroupBusiness;
//import com.citius.reservas.controllers.CustomJsonMapper;
//import com.citius.reservas.models.ResourceGroup;
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import java.io.IOException;
//import java.util.List;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// *
// * @author Esther
// */
//@Controller
//@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
//@RequestMapping(value="/resources",
//        headers={"content-type=text/html"})
//public class TestController {
//    
//    @Autowired
//    private ResourceGroupBusiness rgb;
//    
//    @Autowired
//    private CustomJsonMapper mapper;
//    
//    private static final Logger logger = Logger.getLogger(TestController.class);
//    
//    @RequestMapping("/")
//    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
//    public String loginFailure(Model model) {
//        List<ResourceGroup> l = rgb.readAll();
//        logger.debug("Found");
//        String json = null;
//        try {
//            json = mapper.writeValueAsString(l);
//        } catch (JsonGenerationException ex) {
//            logger.error(ex, ex);
//        } catch (JsonMappingException ex) {
//            logger.error(ex, ex);
//        } catch (IOException ex) {
//            logger.error(ex, ex);
//        }
//        logger.debug("resources:"+json);
//        model.addAttribute("resourcesJson", json);
//        model.addAttribute("resources", l);
//        return "resources";
//    }
//    
//}
