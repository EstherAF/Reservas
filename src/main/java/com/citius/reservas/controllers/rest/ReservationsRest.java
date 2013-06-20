package com.citius.reservas.controllers.rest;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/reservations")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class ReservationsRest {
    
    @Autowired
    private ReservationBusiness rb;
    @Autowired
    private AccessBusiness ab;
    
    @ResponseBody 
    @RequestMapping(value = "/", 
            method = RequestMethod.GET, 
            produces="application/json")
    public List<ReservationInstance> readAll() {
        return rb.readAll();
    }
    
    @ResponseBody 
    @RequestMapping(value = "/id", 
            method = RequestMethod.GET, 
            produces="application/json")
    public Reservation read(@PathVariable Integer id) {
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", 
            method = RequestMethod.POST, 
            produces="application/json")
    public Reservation create(@RequestBody Reservation r) throws NotAvaliableException {
        
        if(r.getResources()==null || r.getResources().isEmpty() ||
            r.getOwner()==null ||
            r.getEventTimeDate().getEndTime() == null ||
            r.getEventTimeDate().getStartTime() ==null ||
            r.getEventTimeDate().getStartDate() == null ||
            r.getResources() == null || r.getResources().isEmpty() ||
            r.getRepetition() == null ||
            r.getRepetition().getType() == null)
            throw new IllegalArgumentException();
        else if (r.getRepetition().getType().equals(RepetitionType.WEEKLY) 
                && (r.getRepetition().getDays()==null || r.getRepetition().getDays().isEmpty()))
                    throw new IllegalArgumentException();
        
        String unique_name = ab.getLoggedUser();
        //r.getOwner().setUniqueName(unique_name);
        
        Reservation created = rb.createReservation(r);
        return created;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", 
            method = RequestMethod.PUT, 
            produces="application/json")
    public Reservation update(@RequestBody Integer id,
                                    @RequestBody String name) {
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}", 
            method = RequestMethod.DELETE, 
            produces="application/json")
    public void delete(@PathVariable Integer id) {
        rb.deleteReservation(id);
    }

    
    @RequestMapping(value="/week", 
         method = RequestMethod.GET, 
            produces="text/html")
    public String redirectWeeklyReservation(@PathVariable Integer year, @PathVariable Integer week) {
        Calendar now = Calendar.getInstance();
        
        String url = "/week/" + now.get(Calendar.YEAR)+ "/" + now.get(Calendar.WEEK_OF_YEAR);
        
        return "redirect:" + url;
    }
    
    @RequestMapping(value="/week/{year}/{week}", 
            method = RequestMethod.GET, 
            produces="text/html")
    public String weeklyReservationView(Model model, @PathVariable Integer year, @PathVariable Integer week) {
        
        //String unique_name = ab.getLoggedUser();
        String unique_name="perico";
        
        List<ReservationInstance> l = rb.readByWeek(unique_name, week, year);
        model.addAttribute("reservationInstances", l);
        return "weekly_reservations";
    }
    
}
