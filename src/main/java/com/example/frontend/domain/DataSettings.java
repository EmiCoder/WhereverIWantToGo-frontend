package com.example.frontend.domain;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Getter
public class DataSettings {

    public static String setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }

    public static String setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(calendar.getTime());
    }
}
