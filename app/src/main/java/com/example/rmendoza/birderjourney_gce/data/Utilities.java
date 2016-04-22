package com.example.rmendoza.birderjourney_gce.data;

import com.example.rmendoza.birderjourney_gce.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodrigoshariff on 4/21/2016.
 */
public class Utilities {

    public static String getBeginTime(String period) {
        String beginTime = "";
        Calendar calendar = Calendar.getInstance();
        Date date;
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
        }

        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyy");
        beginTime = dt.format(calendar.getTime()).toString();
        return beginTime + " 00:00:01";
    }
}

