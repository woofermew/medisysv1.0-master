/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class ScheduleTest {

    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");
    static SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMMMM-yyyy");

    public static void main(String args[]) {

        Schedule schedule = new Schedule();
        //Loads schedule with some dummy data
        loadScheduleWithDummyData(schedule);
        //Tests the method 'checkAvailability' from the Schedule class and load more dummy data
        testCheckAvailabilityMethod(schedule);
        //Tests thew method 'getDayAppointment' with todays date
        testDisplayOnlyTodaysAppointments(schedule);
        
        

        //schedule.printSchedule(schedule);

    }
    
    public static void testDisplayOnlyTodaysAppointments(Schedule schedule){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatter2.parse("25-September-2014"));
            List <Appointment> list = schedule.getDayAppointment(schedule, cal);
            Collections.sort(list);
            for (Appointment a : list) {
                System.out.println(formatter.format(a.getStartTime()) + " - " + formatter.format(a.getEndTime()));
            }
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testCheckAvailabilityMethod(Schedule schedule) {
        try {
            schedule.addAppointment(new Appointment(formatter.parse("25-10-2014 11:00 am"), formatter.parse("25-10-2014 12:00 pm")));//Success
            schedule.addAppointment(new Appointment(formatter.parse("24-10-2014 12:15 pm"), formatter.parse("24-10-2014 1:15 pm")));//Fail
            schedule.addAppointment(new Appointment(formatter.parse("25-10-2014 10:00 am"), formatter.parse("25-10-2014 10:30 am")));//Sucess            
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadScheduleWithDummyData(Schedule schedule) {
        try {
            ArrayList<Appointment> aptmList = new ArrayList<Appointment>();
            aptmList.add(new Appointment(formatter.parse("18-10-2014 7:00 pm"), formatter.parse("18-10-2014 8:15 pm")));
            aptmList.add(new Appointment(formatter.parse("18-10-2014 12:00 pm"), formatter.parse("18-10-2014 1:00 pm")));
            aptmList.add(new Appointment(formatter.parse("19-10-2014 7:00 pm"), formatter.parse("19-10-2014 8:15 pm")));
            aptmList.add(new Appointment(formatter.parse("20-10-2014 12:00 pm"), formatter.parse("20-10-2014 1:00 pm")));
            aptmList.add(new Appointment(formatter.parse("21-10-2014 5:00 pm"), formatter.parse("21-10-2014 5:15 pm")));
            aptmList.add(new Appointment(formatter.parse("22-10-2014 7:00 pm"), formatter.parse("22-10-2014 8:15 pm")));
            aptmList.add(new Appointment(formatter.parse("23-10-2014 12:00 pm"), formatter.parse("23-10-2014 1:00 pm")));
            aptmList.add(new Appointment(formatter.parse("23-10-2014 5:00 pm"), formatter.parse("23-10-2014 5:15 pm")));
            aptmList.add(new Appointment(formatter.parse("24-10-2014 7:00 pm"), formatter.parse("24-10-2014 8:15 pm")));
            aptmList.add(new Appointment(formatter.parse("24-10-2014 12:00 pm"), formatter.parse("24-10-2014 1:00 pm")));
            aptmList.add(new Appointment(formatter.parse("24-10-2014 5:00 pm"), formatter.parse("24-10-2014 5:15 pm")));
            aptmList.add(new Appointment(formatter.parse("24-10-2014 3:00 pm"), formatter.parse("24-10-2014 4:00 pm")));
            aptmList.add(new Appointment(formatter.parse("24-10-2014 1:00 pm"), formatter.parse("24-10-2014 2:15 pm")));
            aptmList.add(new Appointment(formatter.parse("25-10-2014 2:30 pm"), formatter.parse("25-10-2014 3:00 pm")));
            aptmList.add(new Appointment(formatter.parse("25-10-2014 1:30 pm"), formatter.parse("25-10-2014 2:00 pm")));
            aptmList.add(new Appointment(formatter.parse("25-10-2014 3:30 pm"), formatter.parse("25-10-2014 4:00 pm")));

            aptmList.add(new Appointment(formatter.parse("26-10-2014 1:30 pm"), formatter.parse("26-10-2014 2:00 pm")));
            aptmList.add(new Appointment(formatter.parse("26-10-2014 3:30 pm"), formatter.parse("26-10-2014 4:00 pm")));

            schedule.setAppointments(aptmList);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
