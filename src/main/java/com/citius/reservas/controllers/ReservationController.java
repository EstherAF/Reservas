package com.citius.reservas.controllers;

import com.citius.reservas.controllers.customModel.ReservationCustom;
import com.citius.reservas.exceptions.InputRequestValidationException;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.exceptions.NotPossibleInstancesException;
import com.citius.reservas.exceptions.UnknownResourceException;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
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
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
@RequestMapping(value = "/reservations")
@Secured("IS_AUTHENTICATED_FULLY")
public interface ReservationController {

    @InitBinder("reservationCustom")
    public void initBinder(WebDataBinder binder);

    /*Get all reservation instances*/
    @ResponseBody
    @RequestMapping(value = {"/"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll();

    /*Get all reservation instances in month*/
    @ResponseBody
    @RequestMapping(value = {"/{year}/month/{month}"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*Get all resource's reservation instances in received month*/
    @ResponseBody
    @RequestMapping(value = {"/{year}/month/{month}/resource/{resource}"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "resource") Integer resourceId);

    /*Get all user's reservation instances in received month*/
    @ResponseBody
    @RequestMapping(value = "/owns/{year}/month/{month}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllByLoggedUser(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*Get reservation by its Id*/
    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation read(@PathVariable(value = "id") Integer id);

    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation create(@Valid @RequestBody ReservationCustom reservationCustom,
            BindingResult result)
            throws NotAvaliableException, NotPossibleInstancesException,
            InputRequestValidationException;

    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation update(@Valid @RequestBody ReservationCustom reservationCustom,
            BindingResult result)
            throws NotAvaliableException, NotPossibleInstancesException, 
            InputRequestValidationException, UnknownResourceException;

    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "id") Integer id)
            throws AccessDeniedException;

    /**
     * ****************** HTML   ************************
     */
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String redirectReservation();

    @RequestMapping(value = "/week",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String weeklyReservation();

    @RequestMapping(value = "/week/{year}/{week}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String weeklyReservationView(Model model, HttpServletRequest request,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week);

    @RequestMapping(value = "/month",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String monthlyReservation();

    @RequestMapping(value = "/month/{year}/{month}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String monthlyReservation(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    @RequestMapping(value = {"/new"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createReservationView(Model model);

    @RequestMapping(value = {"/new/{year}/{month}/{day}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createReservationView(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "day") Integer day);

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String viewReservation(Model model, @PathVariable(value = "id") Integer id);

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String updateReservationView(Model model, @PathVariable(value = "id") Integer id);

    /*Exceptions*/
    @RequestMapping(value = "/**",
            method = RequestMethod.GET)
    public String mismatch(HttpServletRequest request, Model model) throws NoSuchRequestHandlingMethodException;
}
