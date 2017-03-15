package ir.ripz.monify.model;

import java.util.Calendar;

public class DutyModel {
    public final static String DATA_TITLE = "کل هزینه ها";

    private String title;
    private InterestModel interest;
    private DateModel dateModel;
    private String date;
    private long duty;

    private String id;

    public DutyModel() {
        Calendar c = Calendar.getInstance();
        this.id = c.get(Calendar.MILLISECOND) + "-" + c.get(Calendar.SECOND) + "-"
                + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.HOUR) + "-"
                + c.get(Calendar.DAY_OF_YEAR) + "-" + c.get(Calendar.YEAR);
    }

    public DateModel getDateModel() {
        return dateModel;
    }

    public void setDateModel(DateModel dateModel) {
        Calendar c = Calendar.getInstance();
        this.date = dateModel.getYear()
                + "/" + dateModel.getMonth()
                + "/" + dateModel.getDay()
                + "/" + c.get(Calendar.HOUR)
                + "/" + c.get(Calendar.MINUTE)
                + "/" + c.get(Calendar.SECOND);
        this.dateModel = dateModel;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InterestModel getInterest() {
        return interest;
    }

    public void setInterest(InterestModel interest) {
        this.interest = interest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuty() {
        return duty;
    }

    public void setDuty(long duty) {
        this.duty = duty;
    }
}
