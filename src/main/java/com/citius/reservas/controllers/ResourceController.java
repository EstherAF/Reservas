package com.citius.reservas.controllers;

import com.citius.reservas.controllers.controllerModel.ResourceCustom;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.UnknownResourceException;
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

    /*
     * Content: JSON
     * @return List<ReosurceGroup> Lista de todos los recursos
     * @see ResourceGroup, Resource
     */
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<ResourceGroup> read();

    /*
     * Content: JSON
     * Description: Busca y devuelve un recurso del sistema
     * @param id
     *      Identificador del recurso buscado
     * @return Resource 
     *      Recurso encontrado
     * @throws UnknownResourceException
     *      El recurso no existe
     * @see Resource
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Resource read(@PathVariable(value = "id") Integer id) 
            throws UnknownResourceException;

    /*
     * Content: JSON
     * Description: Crea un recurso
     * @param ResourceCustom
     *      Recurso a crear
     * @return List<Resource>
     *      Lista de recursos creados
     * @throws InputRequestValidationException
     *      Los datos del recurso no son válidos
     * @see ResourceCustom, Resource
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resource> create(@Valid @RequestBody ResourceCustom resourceCustom,
            BindingResult result) throws InputRequestValidationException, UnknownResourceException;

    /*
     * Content: JSON
     * Description: Modifica un recurso ya existente
     * @param resource
     *      Recurso a modificar
     * @return Resource
     *      Recurso modificado
     * @throws InputRequestValidationException
     *      Los datos del recurso no son válidos
     * @see Resource
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource update(@Valid @RequestBody Resource resource,
            BindingResult result) throws InputRequestValidationException, UnknownResourceException;
    /*
     * Content: JSON
     * Description: Elimina un recurso del sistema y de las reservas. Si alguna reserva queda sin recursos se elimina también.
     * @param id
     *      Identificador del recurso a eliminar
     * @throws UnknownResourceException
     *      El recurso no existe
     * @see Resource, Invitation
     */
    @Secured("ROLE_TIC")
    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable(value = "id") Integer id) throws UnknownResourceException;

    /*
     * Content: HTML
     * Description: Muestra la vista de disponibilidad de recursos por defecto
     */
    @RequestMapping(value = {"/", ""},
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resources();
    
    /*
     * Content: HTML
     * Description: Muestra la vista de disponibilidad de recursos por mes, en el mes actual
     */
    @RequestMapping(value = "/month",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesMonth();

    /*
     * Content: HTML
     * Description: Muestra la vista de disponibilidad de recursos por mes, del mes especificado
     * @param year
     *      Año
     * @param month
     *      Mes
     */
    @RequestMapping(value = "/month/{year}/{month}",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesMonth(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*
     * Content: HTML
     * Description: Muestra la vista de disponibilidad de recursos por semana, de la semana actual
     */
    @RequestMapping(value = "/week",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesWeek();

    
    /*
     * Content: HTML
     * Description: Muestra la vista de disponibiliad de recursos por semana de la semana especificada
     * @param year
     *      Año
     * @param week
     *      Semana del año
     */
    @RequestMapping(value = "/week/{year}/{week}",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesWeek(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week);

    /*
     * Content: HTML
     * Description: Se muestra la vista de gestión de recursos
     */
    @Secured("ROLE_TIC")
    @RequestMapping(value = "/admin",
            produces = MediaType.TEXT_HTML_VALUE,
            method = RequestMethod.GET)
    public String resourcesAdmin(Model model);

}
