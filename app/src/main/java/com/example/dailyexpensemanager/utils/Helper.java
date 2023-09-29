package com.example.dailyexpensemanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        return simpleDateFormat.format(date);
    }

    public static String formatDateByMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, yyyy");
        return simpleDateFormat.format(date);
    }
}