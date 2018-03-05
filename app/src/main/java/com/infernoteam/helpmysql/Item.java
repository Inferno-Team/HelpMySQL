package com.infernoteam.helpmysql;

/**
 * Created by Mohammed Issa on 3/4/2018.
 **/

public class Item {
   private String name,company_name;
   private double salary;

    public Item(String name, String company_name, double salary) {
        this.name = name;
        this.company_name = company_name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
