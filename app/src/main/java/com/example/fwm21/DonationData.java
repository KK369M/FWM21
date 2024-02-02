package com.example.fwm21;

public class DonationData {
    private double amount;
    private String name;
    private String email;

    // Empty constructor (required for Firebase)
    public DonationData() {
    }

    public DonationData(double amount, String name, String email) {
        this.amount = amount;
        this.name = name;
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
