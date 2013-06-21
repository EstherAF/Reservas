/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citius.reservas.controllers.helper;

import com.citius.reservas.controllers.customModel.ReservationCustom;
import com.citius.reservas.controllers.customModel.ReservationInstanceCustom;
import com.citius.reservas.models.Reservation;
import com.citius.reservas.models.ReservationInstance;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Esther Álvarez Feijoo
 */
@Component
public class ReservationsControllerHelper {

    @Autowired
    private i18nControllerHelper i18n;

    private String dateWeeklyReservations(Date date, Locale locale) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Integer monthI = c.get(Calendar.MONTH) + 1;
        Integer monthlyDay = c.get(Calendar.DAY_OF_MONTH);
        Integer year = c.get(Calendar.YEAR);
        Integer weeklyDayI = c.get(Calendar.DAY_OF_WEEK);
        weeklyDayI = ((weeklyDayI + 5) % 7) + 1;

        String month = i18n.getMessage(locale, "date.month." + monthI);
        String day = i18n.getMessage(locale, "date.week.day." + weeklyDayI);

        String result = day + ", " + monthlyDay + " " + month + " " + year;

        return result;
    }

    public String weekDescription(Calendar week, HttpServletRequest request) {
        Locale locale = i18n.getLocale(request);
        String s = new String();
        
        week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Integer monthI = week.get(Calendar.MONTH) + 1;
        Integer monthlyDay = week.get(Calendar.DAY_OF_MONTH);
        String month = i18n.getMessage(locale, "date.month." + monthI);
        s+=monthlyDay +" "+month+" - ";
        
        week.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        monthI = week.get(Calendar.MONTH) + 1;
        monthlyDay = week.get(Calendar.DAY_OF_MONTH);
        month = i18n.getMessage(locale, "date.month." + monthI);
        s+=monthlyDay +" "+month;
        return s;
    }

    public List<ReservationInstanceCustom> parseToWeeklyList(List<ReservationInstance> reservationInstances, HttpServletRequest request) {
        Locale locale = i18n.getLocale(request);
        List<ReservationInstanceCustom> l = new ArrayList<>();

        for (ReservationInstance i : reservationInstances) {
            ReservationInstanceCustom r = new ReservationInstanceCustom(i);
            r.setDate(dateWeeklyReservations(i.getStartTimeDate(), locale));
            l.add(r);
        }

        return l;
    }
}
