package org.example.dto;

import org.bson.types.ObjectId;

import java.util.Date;

public class Ticket {
    Integer id;
    String title;
    String type;
    Double cost;
    Date purchaseDate;

    public Ticket() {}

    public Ticket(Integer id, String title, String type, Double cost, Date purchaseDate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public Ticket(String title, String type, Double cost, Date purchaseDate) {
        this.title = title;
        this.type = type;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void displayDetails() {
        System.out.println("Ticket id : " + id);
        System.out.println("Ticket title : " + title);
        System.out.println("Ticket type : " + type);
        System.out.println("Ticket cost : " + cost);
        System.out.println("Ticket purchase date : " + purchaseDate + "\n");
    }
}
