/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Alexander
 */
public class Appointment implements Comparable<Appointment>{
    
    private Date startTime;
    private Date endTime;    
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa");

    public Appointment(Date start, Date end){
        startTime = start;
        endTime = end;
    }
    
    
    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(Appointment o) {
        if(startTime.before(o.startTime)){
            return -1;
        }else if(startTime.after(o.startTime)){
            return 1;
        }
        return 0;
    }
    
    public String printForWeek() {
        return formatter.format(startTime) + "  " + formatter.format(endTime);
    }

    @Override
    public String toString() {
        return "Appointment : " + "startTime=" + startTime + ", endTime=" + endTime;
    }
    
    
    
}
