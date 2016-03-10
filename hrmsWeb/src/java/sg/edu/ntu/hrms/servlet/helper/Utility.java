/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.ntu.hrms.servlet.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
public class Utility {
    
    public static Date format(String dateString, String format)throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateObj = formatter.parse(dateString);

        return dateObj;
    }
    public static double computeDaysBetween(Date d1, Date d2)
    {
        long diff = d2.getTime()-d1.getTime();
        
        double days = Math.abs(d2.getTime() - d1.getTime()) / 86400000;
        return days;
    }
    public static Date getYearBeginTime()
    {
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       try
       {
        Date current = new Date();
        Calendar cal = Calendar.getInstance(); 
        int year = cal.get(Calendar.YEAR);
        String source = "01/01/"+String.valueOf(year);
        System.out.println("source :"+source);
        Date yearBeginTime = format(source,"MM/dd/yyyy"); 
        return yearBeginTime;
       }catch(ParseException pe)
       {
           return null;
       }
       
    }
    public static Date getYearEndTime()
    {
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
       try
       {
        Date current = new Date();
        Calendar cal = Calendar.getInstance(); 
        int year = cal.get(Calendar.YEAR);
        String source = "12/31/"+String.valueOf(year);
        System.out.println("source :"+source);
        Date yearEndTime = format(source,"MM/dd/yyyy"); 
        return yearEndTime;
       }catch(ParseException pe)
       {
           return null;
       }
       
    }
    
    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) 
    {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);        

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 1;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
           //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays;
}
    
}
