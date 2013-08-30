package com.citius.reservas.controllers;

import com.citius.reservas.controllers.customModel.ResourceCustom;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.models.Resource;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/resources")
@Secured("IS_AUTHENTICATED_FULLY")
public interface ResourceController {

    @InitBinder(value = {"resource","resourceCustom"})
    public void initBinder(WebDataBinder binder);

    /**
     * ****************** JSON   ************************
     */
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ResourceGroup> read();

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource read(@PathVariable(value = "id") Integer id);

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> create(@Valid @RequestBody ResourceCustom resourceCustom,
            BindingResult result) throws InputRequestValidationException;

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource update(@Valid @RequestBody Resource resource,
            BindingResult result) throws InputRequestValidationException;

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "id") Integer id);

    /**
     * ****************** HTML   ************************
     */
    @RequestMapping(value = {"/", ""},
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resources();

    @RequestMapping(value = "/month",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesMonth();

    @RequestMapping(value = "/month/{year}/{month}",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesMonth(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    @RequestMapping(value = "/week",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesWeek();

    @RequestMapping(value = "/week/{year}/{week}",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesWeek(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week);

    @Secured("ROLE_TIC")
    @RequestMapping(value = "/admin",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesAdmin(Model model);

//    /*Exceptions*/
//    @RequestMapping(value = "/**",
//            method = RequestMethod.GET)
//    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException;
}
