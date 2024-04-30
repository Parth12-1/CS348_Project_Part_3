package com.example.part_3;

import java.util.Date;

public class Hours_Worked {

    int Employee_ID;
    String Date;

    double Hours;

    public Hours_Worked(int employee_ID, String Date, double hours) {
        Employee_ID = employee_ID;
        this.Date = Date;
        Hours = hours;
    }

    public int getEmployee_ID() {
        return Employee_ID;
    }

    public void setEmployee_ID(int employee_ID) {
        Employee_ID = employee_ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String DATE) {
        this.Date = DATE;
    }

    public double getHours() {
        return Hours;
    }

    public void setHours(double hours) {
        Hours = hours;
    }
}
