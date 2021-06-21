package com.uni.haifa.kgco_op;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule {
    int id;
    String morning;
    String evening;
    Date date;
    List<String> morningKidsList;
    List<String> eveningKidsList;

    public Schedule(String morning, String evening, Date date) {
        this.morning = morning;
        this.evening = evening;
        this.date = date;
    }

    public Schedule() {

    }


    public List<String> getMorningKidsList() {
        return morningKidsList;
    }

    public void setMorningKidsList(List<String> morningKidsList) {
        this.morningKidsList = morningKidsList;
    }

    public List<String> getEveningKidsList() {
        return eveningKidsList;
    }

    public void setEveningKidsList(List<String> eveningKidsList) {
        this.eveningKidsList = eveningKidsList;
    }

    public Schedule(int id, String morning, String evening, Date date, List<String> morningKidsList, List<String> eveningKidsList) {
        this.id = id;
        this.morning = morning;
        this.evening = evening;
        this.date = date;
        this.morningKidsList = morningKidsList;
        this.eveningKidsList = eveningKidsList;
    }


    public Schedule(String morning, String evening, String date, List<String> morningKids, List<String> eveningKids) throws ParseException {
        this.date = stringToDate(date);
        this.morning = morning;
        this.evening = evening;
        this.morningKidsList = morningKids;
        this.eveningKidsList = eveningKids;
    }

    public Schedule(int id, String morning, String evening, Date date, String[] morningKids, String[] eveningKids) {
        this.id = id;
        this.morning = morning;
        this.evening = evening;
        this.date = date;
        this.morningKidsList = Arrays.asList(morningKids);
        this.eveningKidsList = Arrays.asList(eveningKids);
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

    public List<String> getMorningKids() {
        return morningKidsList;
    }

    public String getMorningKidsString() {
        String str = "";
        if (morningKidsList != null) {
            for (String s : morningKidsList) {
                str += s + ", ";
            }
        } else {
            for (String s : morningKidsList) {
                str += s + ", ";
            }
        }
        return str;
    }


    public String getEveningKidsString() {
        String str = "";
        if (eveningKidsList != null) {
            for (String s : eveningKidsList) {
                str += s + ", ";
            }
        } else {
            for (String s : eveningKidsList) {
                str += s + ", ";
            }
        }
        return str;
    }

    public void setMorningKids(List<String> morningKids) {
        this.morningKidsList = morningKids;
    }

    public List<String> getEveningKids() {
        return eveningKidsList;
    }

    public void setEveningKids(List<String> eveningKids) {
        this.eveningKidsList = eveningKids;
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
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(str);
        return date;
    }

    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }

    private String[] stringToArray(String str) {
        str = str.replaceAll("\\s", "");
        String[] array = str.split(",");
        return array;
    }
}
