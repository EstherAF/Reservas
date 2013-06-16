package com.citius.reservas.controllers.rest;

import com.citius.reservas.business.AccessBusiness;
import com.citius.reservas.business.ReservationBusiness;
import com.citius.reservas.exceptions.NotAvaliableException;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/reservations")
//@Secured("IS_AUTHENTICATED_FULLY")
public class ReservationsController {
    
    @Autowired
    private ReservationBusiness rb;
    @Autowired
    private AccessBusiness ab;
    
    @ResponseBody 
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public Reservation read(@PathVariable Integer id) {
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
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
        Reservation created = rb.createReservation(r);
        return created;
    }
    
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Reservation update(@RequestBody Integer id,
                                    @RequestBody String name) {
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        
    }
    
}
