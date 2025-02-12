package com.joseph;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecretNote {

    String title;
    String note;
    String dateStamp;

    public SecretNote(String title, String note) {
        this.title = title;
        this.note = note;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateStamp = LocalDateTime.now().format(formatter);
    }
}
