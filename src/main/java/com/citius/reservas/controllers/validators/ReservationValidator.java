/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.validators;

import com.citius.reservas.controllers.customModel.ReservationCustom;
import com.citius.reservas.controllers.customModel.ResourceGroupCustom;
import com.citius.reservas.models.Repetition;
import com.citius.reservas.models.RepetitionType;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.Resource;
import org.springframework.validation.Errors;

/**
 *
 * @author Esther Álvarez Feijoo
 */
public class ReservationValidator extends GenericValidator<Reservation> {
    
    public ReservationValidator(){
        super();
    }

    @Override
    public void validate(Object target, Errors errors) {

        Reservation bean = (Reservation) target;
        Repetition repetition = bean.getRepetition();

        //Default validation of Reservation, and its invitations and Repetition
        this.standardValidator.validate(bean, errors);

        //Start and End
        if (!errors.hasFieldErrors("start")
                && !errors.hasFieldErrors("end")
                && bean.getStart().after(bean.getEnd())) {
            errors.rejectValue("end", "error.form.reservation.end.after");
        }

        //End repetition
        if (!errors.hasFieldErrors("end")
                && !errors.hasFieldErrors("endDate")
                && repetition.getEndDate().before(bean.getEnd())) {
            errors.rejectValue("endDate", "error.form.reservation.repetition.endDate.after");
        }


        //Repetition
        if (!errors.hasFieldErrors("type")
                && !repetition.getType().equals(RepetitionType.ONCE)) {

            if (repetition.getInterval() == null) {
                errors.rejectValue("interval", "error.form.reservation.repetition.interval.required");
            }

            if (repetition.getType().equals(RepetitionType.WEEKLY)
                    && repetition.getWeekDays() == null) {
                errors.rejectValue("weekDays", "error.form.reservation.repetition.weekDays.min");
            }
        }
        
        //Depending of class of object
        if(target.getClass().equals(ReservationCustom.class))
            this.validateReservationCustom((ReservationCustom)target, errors);
        else
            this.validateReservation((ReservationCustom)target, errors);
    }
    
    private void validateReservation(Reservation bean, Errors errors) {
        
        if (bean.getResources().isEmpty()) {
            errors.rejectValue("resources", "error.form.reservation.resources.size");
        }else{
            for (Resource r : bean.getResources()) {
                if (r.getId() == null || r.getId() < 1) {
                    errors.rejectValue("resources", "error.form.reservations.resources.id.required");
                    break;
                }
            }
        }
    }
    
    private void validateReservationCustom(ReservationCustom bean, Errors errors) {

        //Resources
        if (bean.getGroups().isEmpty() && bean.getResources().isEmpty()) {
            errors.rejectValue("resources", "error.form.reservation.resources.size");
        }

        if (!bean.getGroups().isEmpty()) {
            for (ResourceGroupCustom r : bean.getGroups()) {
                if (r.getId() == null || r.getId() < 1) {
                    errors.rejectValue("resourceGroups", "error.form.reservations.resources.id.required");
                    break;
                }

                if (r.getQuantity() == null || r.getQuantity() < 1) {
                    errors.rejectValue("resourceGroups", "error.form.reservations.resourceGroups.quenatity.required");
                    break;
                }
            }
        }

        if (!bean.getResources().isEmpty()) {
            for (Resource r : bean.getResources()) {
                if (r.getId() == null || r.getId() < 1) {
                    errors.rejectValue("resources", "error.form.reservations.resources.id.required");
                    break;
                }
            }
        }
    }
}
