package com.example.rmendoza.birderjourney_gce;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rodrigoshariff on 4/21/2016.
 */
public class Utilities {

    public static String getBeginTime(String period) {
        String beginTime = "";
        Calendar calendar = Calendar.getInstance();
        switch (period) {
            //as you go.
            case "today": {
//              do nothing
                break;
            }
            case "week": {
                calendar.set(Calendar.DAY_OF_WEEK, 1);
                break;
            }
            case "month": {
                calendar.set(Calendar.DATE, 1);
                break;
            }
            case "year": {
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                break;
            }
            case "ever": {
                calendar.set(Calendar.YEAR, 1900);
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                break;
            }
        }

        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyy");
        beginTime = dt.format(calendar.getTime()).toString();
        return beginTime + " 00:00:01";
    }

    public static String getTimeOnly(String datetime) {

        SimpleDateFormat dtinput = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        SimpleDateFormat dtoutput = new SimpleDateFormat("h:mm a");
        String timeOnly = null;
        try {
            timeOnly = dtoutput.format(dtinput.parse(datetime)).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeOnly;
    }



    public static String getDayOnly(String datetime) {

        SimpleDateFormat dtinput = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        SimpleDateFormat dtoutput = new SimpleDateFormat("EEE, d MMM yyyy");
        String dayOnly = null;
        try {
            dayOnly = dtoutput.format(dtinput.parse(datetime)).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOnly;
    }



    public static String getPeriodName(String period) {
        String PeriodName = "";
        switch (period) {
            //as you go.
            case "today": {
                PeriodName = "Today";
                break;
            }
            case "week": {
                PeriodName = "This Week";
                break;
            }
            case "month": {
                PeriodName = "This Month";
                break;
            }
            case "year": {
                PeriodName = "This Year";
                break;
            }
            case "ever": {
                PeriodName = "Ever";
                break;
            }
        }
        return PeriodName;
    }







}

