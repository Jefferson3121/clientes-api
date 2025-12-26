package com.ClientHub.api.domain.enums;

public enum PlanDuration {
    MONTLY(30),
    ANNUAL(360);


    private final int daysDuration;

    PlanDuration(int daysDuration) {
        this.daysDuration = daysDuration;
    }

    public int getDaysDuration(){
        return daysDuration;
    }
}
