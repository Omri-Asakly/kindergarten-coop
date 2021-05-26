package com.uni.haifa.kgco_op;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    int id;
    String morning;
    String evening;
    Date date;

    public Schedule(String morning, String evening, Date date) {
        this.morning = morning;
        this.evening = evening;
        this.date = date;
    }

    public Schedule() {

    }


    public Schedule(String morning, String evening, String date) throws ParseException {
        this.date = stringToDate(date);
        this.morning = morning;
        this.evening = evening;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", morning='" + morning + '\'' +
                ", evening='" + evening + '\'' +
                ", date=" + date +
                '}';
    }

    public boolean compare(Date s) {
        if (s != null)
            if (s.getDay() == this.date.getDay() && s.getMonth() == this.date.getMonth() && s.getYear() == this.date.getYear())
                return true;
        return false;
    }

    public static Date stringToDate(String str) throws ParseException {
        System.out.println(str);
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
        return date;
    }

    public static Date getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }
}
