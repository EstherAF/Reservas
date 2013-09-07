package com.citius.reservas.controllers;

import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
@RequestMapping(value = "/resources/groups",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("IS_AUTHENTICATED_FULLY")
public interface ResourcesGroupsController {

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ResourceGroup> read();

    @ResponseBody
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResourceGroup read(@PathVariable(value = "id") Integer id);

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public ResourceGroup create(@Valid @RequestBody ResourceGroup resourceGroup,
            BindingResult result) throws InputRequestValidationException;

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    public ResourceGroup update(@Valid @RequestBody ResourceGroup resourceGroup,
            BindingResult result) throws InputRequestValidationException;

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOnlyGroup(@PathVariable(value = "id") Integer id);

    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}/all", method = RequestMethod.DELETE)
    public Boolean delete(@PathVariable(value = "id") Integer id) throws UnknownResourceException;

//    /*Exceptions*/
//    @RequestMapping(value = "/**",
//            method = RequestMethod.GET)
//    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException;
}
