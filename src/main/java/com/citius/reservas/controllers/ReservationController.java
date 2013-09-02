package com.citius.reservas.controllers;

import com.citius.reservas.controllers.customModel.ReservationCustom;
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

    /*Get all reservation instances*/
    @ResponseBody
    @RequestMapping(value = {"/"},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll();

    /*Get all resource's reservation instances in received month*/
    @ResponseBody
    @RequestMapping(value = "/month/{year}/{month}/resource/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllMonth(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "id") Integer another);
    
    /*Get all resource's reservation instances in received month*/
    @ResponseBody
    @RequestMapping(value = "/week/{year}/{week}/resource/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAllWeek(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "week") Integer week,
            @PathVariable(value = "id") Integer another);

    /*Get all reservation instances in month*/
    @ResponseBody
    @RequestMapping(value = "/month/{year}/{month}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReservationInstance> readAll(
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month);

    /*Get all user's reservation instances in received month*/
    @ResponseBody
    @RequestMapping(value = "/owns/month/{year}/{month}",
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
            InputRequestValidationException, UnknownResourceException;

    @ResponseBody
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Reservation update(@Valid @RequestBody ReservationCustom reservationCustom,
            BindingResult result)
            throws NotAvaliableException, NotPossibleInstancesException,
            InputRequestValidationException, UnknownResourceException;

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete(@PathVariable(value = "id") Integer id)
            throws AccessDeniedException, UnknownResourceException;

    /**
     * ****************** HTML ************************
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
    public String createReservationView(Model model) throws IOException;

    @RequestMapping(value = {"/new/{year}/{month}/{day}"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String createReservationView(Model model,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "month") Integer month,
            @PathVariable(value = "day") Integer day)  
            throws IOException;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String viewReservation(Model model, @PathVariable(value = "id") Integer id) 
            throws UnknownResourceException;

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String updateReservationView(Model model, @PathVariable(value = "id") Integer id)  
            throws IOException, UnknownResourceException;
}
