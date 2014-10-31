/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexander
 */
public class Schedule {

    private List<Appointment> appointments;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
    private String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public Boolean checkAvailability(Appointment newAptm) {
        //check database to return just appointments from this day 
        //appointments = findByDayOfTheMonth(start);
        for (Appointment aptm : appointments) {
            if (!(((newAptm.getEndTime().before(aptm.getStartTime())) || (newAptm.getEndTime().equals(aptm.getStartTime())))
                    || (newAptm.getStartTime().after(aptm.getEndTime()) && newAptm.getEndTime().before(aptm.getStartTime()))
                    || (newAptm.getStartTime().after(aptm.getEndTime())))) {
                System.out.println("Not possible to book : there is already an appointment for this time");
                System.out.println("Appointment details :");
                System.out.println(aptm);
                return false;
            }

        }
        System.out.println("Appointment booked successfuly!");
        return true;
    }

    public List<Appointment> getDayAppointment(Schedule schedule, Calendar calendar) {
        List<Appointment> list = new ArrayList<Appointment>();
        for (Appointment a : schedule.getAppointments()) {
            Calendar c = Calendar.getInstance();
            c.setTime(a.getStartTime());
            if ((c.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) && (c.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))) {
                list.add(a);
            }
        }
        return list;
    }

    public Map<String, List<Appointment>> getWeeksAppointment(Schedule schedule, Calendar day) {
        Map<String, List<Appointment>> weeksAppointment = new HashMap<String, List<Appointment>>();
        Calendar weekStarts = getFirstDayOfWeek(day);
        Calendar weekEnds = (Calendar)weekStarts.clone();
        weekEnds. add(Calendar.DAY_OF_WEEK, 7);

        for (Appointment a : schedule.getAppointments()) {
            Calendar c = Calendar.getInstance();
            c.setTime(a.getStartTime());
            if ((c.after(weekStarts) && c.before(weekEnds)) && (c.get(Calendar.YEAR) == weekStarts.get(Calendar.YEAR))) {
                if (weeksAppointment.containsKey(headers[c.get(Calendar.DAY_OF_WEEK)-1])) {
                    weeksAppointment.get(headers[c.get(Calendar.DAY_OF_WEEK)-1]).add(a);
                } else {
                    List<Appointment> list = new ArrayList<Appointment>();
                    list.add(a);
                    weeksAppointment.put(headers[c.get(Calendar.DAY_OF_WEEK)-1], list);
                }
            }
        }

        return weeksAppointment;
    }

    public Calendar getFirstDayOfWeek(Calendar cal) {
        // "calculate" the start date of the week
        Calendar first = (Calendar) cal.clone();
        first.set(Calendar.HOUR_OF_DAY, 0);
        first.add(Calendar.DAY_OF_WEEK,
                first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        // and add six days to the end date
        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        return first;
    }

    public void addAppointment(Appointment a) throws Exception {
        if (checkAvailability(a)) {
            appointments.add(a);
        }else{
            throw new Exception();
        }
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}
