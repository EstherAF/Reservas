package com.citius.reservas.controllers;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.controllers.customModel.ReservationInstanceCustom;
import com.citius.reservas.controllers.helper.ReservationsControllerHelper;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/reservations")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class ReservationController {

    @Autowired
    private ReservationBusiness rb;
    @Autowired
    private AccessBusiness ab;
    @Autowired
    private ReservationsControllerHelper rch;

    @ResponseBody
    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<ReservationInstance> readAll() {
        return rb.readAll();
    }

    @ResponseBody
    @RequestMapping(value = "/id",
            method = RequestMethod.GET,
            produces = "application/json")
    public Reservation read(@PathVariable Integer id) {
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            produces = "application/json")
    public Reservation create(@RequestBody Reservation r) throws NotAvaliableException {

        if (r.getResources() == null || r.getResources().isEmpty()
                || r.getOwner() == null
                || r.getEventTimeDate().getEndTime() == null
                || r.getEventTimeDate().getStartTime() == null
                || r.getEventTimeDate().getStartDate() == null
                || r.getResources() == null || r.getResources().isEmpty()
                || r.getRepetition() == null
                || r.getRepetition().getType() == null) {
            throw new IllegalArgumentException();
        } else if (r.getRepetition().getType().equals(RepetitionType.WEEKLY)
                && (r.getRepetition().getDays() == null || r.getRepetition().getDays().isEmpty())) {
            throw new IllegalArgumentException();
        }

        String unique_name = ab.getLoggedUser();
        //r.getOwner().setUniqueName(unique_name);

        Reservation created = rb.createReservation(r);
        return created;
    }

    @ResponseBody
    @RequestMapping(value = "/",
            method = RequestMethod.PUT,
            produces = "application/json")
    public Reservation update(@RequestBody Integer id,
            @RequestBody String name) {
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public void delete(@PathVariable Integer id) {
        rb.deleteReservation(id);
    }

    @RequestMapping(value = "/week",
            method = RequestMethod.GET,
            produces = "text/html")
    public String redirectWeeklyReservation() {
        Calendar now = Calendar.getInstance();

        String url = "/reservations/week/" + now.get(Calendar.YEAR) + "/" + now.get(Calendar.WEEK_OF_YEAR);

        return "redirect:" + url;
    }

    @RequestMapping(value = "/week/{year}/{week}",
            method = RequestMethod.GET,
            produces = "text/html")
    public String weeklyReservationView(Model model, HttpServletRequest request,
            @PathVariable Integer year, @PathVariable Integer week) {

        //String unique_name = ab.getLoggedUser();
        String unique_name = "perico";

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR, week);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        if(year!=c.get(Calendar.YEAR)){
            String url = "/reservations/week/" + c.get(Calendar.YEAR) + "/" + c.get(Calendar.WEEK_OF_YEAR);
            return "redirect:" + url;
        }
        
        String weekDescription = rch.weekDescription(c, request);
        
        List<ReservationInstance> instances = rb.readByWeek(unique_name, c);
        List<ReservationInstanceCustom> customInstances = rch.parseToWeeklyList(instances, request);

        c.add(Calendar.WEEK_OF_YEAR, -1);
        String previous = "/Reservas/reservations/week/" + c.get(Calendar.YEAR) + "/" + c.get(Calendar.WEEK_OF_YEAR);
        c.add(Calendar.WEEK_OF_YEAR, +2);
        String next = "/Reservas/reservations/week/" + c.get(Calendar.YEAR) + "/" + c.get(Calendar.WEEK_OF_YEAR);
        
        
        model.addAttribute("weekDescription", weekDescription);
        model.addAttribute("reservationInstances", customInstances);
        model.addAttribute("next", next);
        model.addAttribute("previous", previous);
        return "weekly_reservations";
    }
}
