package com.example.emanager.models;

public class Recommend extends RealmObject{
    private double budget;
    private double saving;
    private double privateActivity;
    private double necessity;
    private double rentAndFee;
    @PrimaryKey
    private long id;

    public Recommend(){}

    public Recommend(Double budget){
        this.budget = budget;
    }
    public double getRentAndFee() {
        return rentAndFee;
    }

    public void setRentAndFee(double rentAndFee) {
        this.rentAndFee = rentAndFee;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }


    public double getPrivateActivity() {
        return privateActivity;
    }

    public void setPrivateActivity(double privateActivity) {
        this.privateActivity = privateActivity;
    }

    public double getNecessity() {
        return necessity;
    }

    public void setNecessity(double necessity) {
        this.necessity = necessity;
    }




}
