package com.elebras1.message.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static String formatDate(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(timestamp));
    }
}
