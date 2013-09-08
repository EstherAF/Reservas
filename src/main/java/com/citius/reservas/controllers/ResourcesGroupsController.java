package com.citius.reservas.controllers;

import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.ResourceGroup;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/resources/groups",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Secured("IS_AUTHENTICATED_FULLY")
public interface ResourcesGroupsController {

    /*
     * Content: JSON
     * @return List<ResourceGroup> Lista de todos los recursos
     * @see ResourceGroup, Resource
     */
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ResourceGroup> read();

    /*
     * Content: JSON
     * @param id
     *      Id del grupo de recursos
     * @return ResourceGroup 
     *      Grupo de recursos encontrado
     * @throws UnknownResourceException
     *      El grupo de recursos no existe
     * @see ResourceGroup
     */
    @ResponseBody
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResourceGroup read(@PathVariable(value = "id") Integer id) throws UnknownResourceException;

    /*
     * Content: JSON
     * Description: Crea un grupo de recursos
     * @param ResourceGroup 
     *       Grupo de recursos a crear
     * @return ResourceGroup
     *       Grupo de recursos creado
     * @throws InputRequestValidationExcetion
     *      Los datos son incorrectos
     * @see ResourceGroup
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public ResourceGroup create(@Valid @RequestBody ResourceGroup resourceGroup,
            BindingResult result) throws InputRequestValidationException;

    /*
     * Content: JSON
     * Description: Modifica un grupo de recursos existente
     * @param ResourceGroup
     *      Grupo de recursos a modificar
     * @return ResourceGroup
     *      Grupo de recursos modificado
     * @throws InputRequestValidationException
     *      Los datos son incorrectos
     * @see ResourcegGroup
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    public ResourceGroup update(@Valid @RequestBody ResourceGroup resourceGroup,
            BindingResult result) throws InputRequestValidationException;

    /*
     * Content: JSON
     * Description: Elimina el grupo de recursos, y los recursos que contiene pasan a pertenecer al grupo por defecto
     * @param id
     *      Identificador del grupo de recursos
     * @throws UnknownResourceException
     *      El grupo de recursos no existe
     * @see ResourceGroup
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Boolean deleteOnlyGroup(@PathVariable(value = "id") Integer id) throws UnknownResourceException;

    
    /*
     * Content: JSON
     * Description: Elimina un grupo de recursos y los recursos que contiene
     * @param id
     *      Identificador del grupo de recursos
     * @throws UnknownResourceException
     *      El grupo de recursos no existe
     * @see ResourceGroup
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}/all", method = RequestMethod.DELETE)
    public Boolean delete(@PathVariable(value = "id") Integer id) throws UnknownResourceException;
}
