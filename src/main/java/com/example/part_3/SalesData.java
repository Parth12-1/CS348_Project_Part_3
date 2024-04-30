package com.example.part_3;

public class SalesData {
    private int customerID;
    private int employeeID;
    private String dateTime;
    private String customerName;
    private String customerEmail;
    private int itemID;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private double totalPrice;
    private double totalCommission;
    private double totalHoursWorked;
    private double aveHoursPerCustomer;

    public SalesData(int customerID, int employeeID, String dateTime, String customerName,
                     String customerEmail, int itemID, String itemName, double itemPrice,
                     int quantity, double totalPrice, double totalCommission, double totalHoursWorked,
                     double aveHoursPerCustomer) {
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.dateTime = dateTime;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.totalCommission = totalCommission;
        this.totalHoursWorked = totalHoursWorked;
        this.aveHoursPerCustomer = aveHoursPerCustomer;
    }

    // Getters and setters for all properties

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(double totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public double getAveHoursPerCustomer() {
        return aveHoursPerCustomer;
    }

    public void setAveHoursPerCustomer(double aveHoursPerCustomer) {
        this.aveHoursPerCustomer = aveHoursPerCustomer;
    }
}
