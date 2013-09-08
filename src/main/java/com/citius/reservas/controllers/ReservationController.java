package com.citius.reservas.controllers;

import com.citius.reservas.controllers.controllerModel.ReservationCustom;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/reservations")
@Secured("IS_AUTHENTICATED_FULLY")
public interface ReservationController {

    @InitBinder("reservationCustom")
    public void initBinder(WebDataBinder binder);

    /*
     * Content: JSON
     * @return List<ReservationInstance>
     *      Lista de todas las instancias de reserva del sistema
     * @see ReservationInstance, Reservation
     */
    @ResponseBody
    @RequestMapping(value = {"/"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll();

    /*Get all resource's reservation instances in received month*/
    /*
     * Content: JSON
     * @param year
     *      Año
     * @param month
     *      Mes
     * @param resourceId
     *      Identificador de recurso
     * @return List<ReservationInstance>
     *      Lista de instancias de reserva ocurridas en el mes y año especificados, que reservan el recurso.
     * @throws 
     * @see ReservationInstance, Reservation, Resource
     */
    @ResponseBody
    @RequestMapping(value = "/month/{year}/{month}/resource/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllMonth(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "id") Integer resourceId);
    
    /*
     * Content: JSON
     * @param year
     *      Año
     * @param month
     *      Mes
     * @param week
     *      Semana del año
     * @return List<ReservationInstance>
     *      Lista de instancias de reserva ocurridas en la semana y año especificados que reservan el recuso
     * @see ReservationInstance, Reservation, Resource
     */
    @ResponseBody
    @RequestMapping(value = "/week/{year}/{week}/resource/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllWeek(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week,
            @PathVariable(value = "id") Integer resourceId);

    /*
     * Content: JSON
     * @param year
     *      Año
     * @param month
     *      Mes
     * @return List<ReservationInstance>
     *      Lista de instancias de reserva ocurridas en el mes y año especificados
     * @see ReservationInstance, Reservation
     */
    @ResponseBody
    @RequestMapping(value = "/month/{year}/{month}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*
     * Content: JSON
     * @param year
     *      Año
     * @param month
     *      Mes
     * @return List<ReservationInstance>
     *      Lista de instancias de reserva que las que participa el usuario validado
     * @see ReservationInstance, reservation
     */
    @ResponseBody
    @RequestMapping(value = "/owns/month/{year}/{month}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllByLoggedUser(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*
     * Content: JSON
     * @param id
     *      Identificador de la reserva
     * @return Reservation
     *      Reserva encontrada
     * @throws UnknownResourceException
     *      La reserva no existe
     * @see Reservation
     */
    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation read(@PathVariable(value = "id") Integer id) 
            throws UnknownResourceException;

    /*
     * Content: JSON
     * Description: Crea una reserva, cuyo propietario es le usuario validado
     * @param ReservationCustom
     *      Reserva a crear
     * @return Reservation
     *      ReservaCreada
     * @throws NotAvaliableException
     *      Alguno de los recursos está ocupado, y no se puede realizar la reserva
     * @throws NotPossibleInstanceException
     *      Debido a los datos de la repetición de la reserva, esta reserva no llegaría a ocurrir nunca
     * @throws InputRequestValidationException
     *      Los datos de la reserva son inválidos
     * @throws UnknownResourceException
     *      Algunos de los recursos de la reserva no existen
     * @see Resource, Reservation, ReservationInstance, NotAvaliable
     */
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation create(@Valid @RequestBody ReservationCustom reservationCustom,
            BindingResult result)
            throws NotAvaliableException, NotPossibleInstancesException,
            InputRequestValidationException, UnknownResourceException;

    /*
     * Content: JSON
     * Description: Modifica una reserva existente, cuyo propietario es le usuario validado
     * @param ReservationCustom
     *      Reserva a modificar
     * @return Reservation
     *      Reserva modificada
     * @throws NotAvaliableException
     *      Alguno de los recursos está ocupado, y no se puede realizar la reserva
     * @throws NotPossibleInstanceException
     *      Debido a los datos de la repetición de la reserva, esta reserva no llegaría a ocurrir nunca
     * @throws InputRequestValidationException
     *      Los datos de la reserva son inválidos
     * @throws UnknownResourceException
     *      Algunos de los recursos de la reserva no existen
     * @see Resource, Reservation, ReservationInstance, NotAvaliable
     */
    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation update(@Valid @RequestBody ReservationCustom reservationCustom,
            BindingResult result)
            throws NotAvaliableException, NotPossibleInstancesException,
            InputRequestValidationException, UnknownResourceException;

    /*
     * Content: JSON
     * Description: Elimina la reserva, si el usuario es administrador o el propietario de la reserva
     * @param id
     *      Identificador de la reserva
     * @throws AccessDeniedException
     *      El usuario no es ni administrador ni el propietario de la reserva. No tiene permiso para realizar esta operación
     * @see Reservation
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable(value = "id") Integer id)
            throws AccessDeniedException, UnknownResourceException;

    /**
     * ****************** HTML ************************
     */
    /*
     * Content: HTML
     * Description: Muestra la vista por defecto de visualización de reservas del usuario validado
     */
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String redirectReservation();

    /*
     * Content: HTML
     * Description: Muestra la vista de visualización de reservas del usuario validado, en la semana actual
     */
    @RequestMapping(value = "/week",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String weeklyReservation();

    /*
     * Content: HTML
     * Description: Muestra la vista de visualización de reservas del usuario validado, en la semana y año especificados
     * @param week
     *      Semana del año
     * @param year
     *      Año
     */
    @RequestMapping(value = "/week/{year}/{week}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String weeklyReservationView(Model model, HttpServletRequest request,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week);

    /*
     * Content: HTML
     * Description: Muestra la vista de visualización de reservas por mes del usuario validado, en el mes actual
     */
    @RequestMapping(value = "/month",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String monthlyReservation();

    /*
     * Content: HTML
     * Description: Muestra la vista de visualización de reservas por mes del usuario validado, en el mes y año especificados
     * @param year
     *      Año
     * @param month
     *      Mes
     */
    @RequestMapping(value = "/month/{year}/{month}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String monthlyReservation(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*
     * Content: HTML
     * Description: Muestra la vista de creación de reserva
     */
    @RequestMapping(value = {"/new"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createReservationView(Model model) throws IOException;

    /*
     * Content: HTML
     * Description: Muestra la vista de creación de reserva, con las fechas de la reserva establecidas al día, mes y año recibidos
     * @param year
     *      Año
     * @param month
     *      Mes
     * @param day
     *      Día del mes
     */
    @RequestMapping(value = {"/new/{year}/{month}/{day}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createReservationView(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "day") Integer day)  
            throws IOException;

    /*
     * Content: HTML
     * Description: Muestra la vista de detalle de reserva
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String viewReservation(Model model, @PathVariable(value = "id") Integer id) 
            throws UnknownResourceException;

    /*
     * Content: HTML
     * Description: Muestra la vista de modificación de la reserva especificada
     * @param id
     *      Identificador de la reserva
     * @throws UnknownResourceException
     *      La reserva no existe
     * @throws IOException
     *      No se pudo parsear la reserva a JSON para pasársela a la vista
     * @throws AccessDeniedException
     *      El usuario no es administrador ni el porpietario de la reserva. No tiene los permisos suficientes para realizar esta operación
     */
    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String updateReservationView(Model model, @PathVariable(value = "id") Integer id)  
            throws IOException, UnknownResourceException, AccessDeniedException;
}
