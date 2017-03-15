package ir.ripz.monify.model;

public class DateModel {
    private int month;
    private String month_str;
    private int year;
    private int day;
    private String day_week;
    private int hour;
    private int minute;

    public DateModel(int day, int month, String day_week, String month_str, int year) {
        this.day = day;
        this.month = month;
        this.day_week = day_week;
        this.month_str = month_str;
        this.year = year;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDay_week() {
        return day_week;
    }

    public void setDay_week(String day_week) {
        this.day_week = day_week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonth_str() {
        return month_str;
    }

    public void setMonth_str(String month_str) {
        this.month_str = month_str;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}
