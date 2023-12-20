package org.example.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.dto.Ticket;
import org.example.util.HelperFunctions;
import org.example.util.MyLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Airline implements Stock {
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader buff = new BufferedReader(isr);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    MongoCollection<Document> tickets;
    static Integer id = 0;

    public Airline(MongoCollection<Document> tickets) {
        this.tickets = tickets;

        Document maxIdDocument = tickets.find().sort(Sorts.descending("id")).first();
        if (maxIdDocument != null) {
            id = maxIdDocument.getInteger("id") + 1;
        }
    }
    public void saveTicket() {
        try {
            System.out.println("Enter Ticket Title : ");
            String title = buff.readLine();

            System.out.println("Enter Ticket Type : ");
            String type = buff.readLine();

            System.out.println("Enter Ticket Cost : ");
            Double cost = Double.parseDouble(buff.readLine());

            System.out.println("Enter Ticket Purchase Date : ");
            String date = buff.readLine();

            Document ticket = new Document("id", id)
                    .append("title", title)
                    .append("type", type)
                    .append("cost", cost)
                    .append("purchase_date", HelperFunctions.covertJavaStringToDate(date));

            tickets.insertOne(ticket);

        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
            return;
        }

        System.out.println("Successfully entered the ticket into the database\n");
        MyLogger.getInstance().log("Successfully entered the ticket into the database\n");
    }
    public void updateTicketById() {
        try {
            System.out.println("Enter the Ticket ID that you wish to edit : ");
            Integer id = Integer.parseInt(buff.readLine());

            System.out.println("Enter updated Ticket Title : ");
            String title = buff.readLine();

            System.out.println("Enter updated Ticket Type : ");
            String type = buff.readLine();

            System.out.println("Enter updated Ticket Cost : ");
            Double cost = Double.parseDouble(buff.readLine());

            System.out.println("Enter updated Ticket Purchase Date : ");
            String date = buff.readLine();

            Ticket ticket = new Ticket(title, type, cost, HelperFunctions.covertJavaStringToDate(date));

            Bson filter = Filters.eq("id", id);

            // Create updates for each field
            Bson updateTitle = Updates.set("title", title);
            Bson updateType = Updates.set("type", type);
            Bson updateCost = Updates.set("cost", cost);
            Bson updatePurchaseDate = Updates.set("purchase_date", HelperFunctions.covertJavaStringToDate(date));

            // Combine all updates
            Bson updates = Updates.combine(updateTitle, updateType, updateCost, updatePurchaseDate);

            // Update the ticket
            tickets.updateOne(filter, updates);

            System.out.println("Ticket with id " + id + " has been updated.");
            MyLogger.getInstance().log("Ticket with id " + id + " has been updated.");
        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void removeTicketById() {
        try {
            System.out.println("Enter the Ticket ID that you wish to delete : ");
            Integer id = Integer.parseInt(buff.readLine());

            // Create a filter to match the ticket with the given id
            Bson filter = Filters.eq("id", id);

            // Delete the ticket
            tickets.deleteOne(filter);

            System.out.println("Ticket with id " + id + " has been deleted.");
        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void findByTitle() {
        try {
            System.out.println("Enter the Ticket Title that you want to search for : ");
            String searchedTitle = buff.readLine();

            Bson filter = Filters.eq("title", searchedTitle);
            FindIterable<Document> documents = tickets.find(filter);
            ArrayList<Ticket> foundTickets = HelperFunctions.getTicketsFromDocumentAndDisplay(documents);

        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void findByType() {
        try {
            System.out.println("Enter the Ticket Type that you want to search for : ");
            String searchedType = buff.readLine();

            Bson filter = Filters.eq("type", searchedType);
            FindIterable<Document> documents = tickets.find(filter);
            ArrayList<Ticket> foundTickets = HelperFunctions.getTicketsFromDocumentAndDisplay(documents);

        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void findByCost() {
        try {
            System.out.println("Enter the cost of a Ticket that you want to search for : ");
            Double searchCost = Double.parseDouble(buff.readLine());

            Bson filter = Filters.eq("cost", searchCost);
            FindIterable<Document> documents = tickets.find(filter);
            ArrayList<Ticket> foundTickets = HelperFunctions.getTicketsFromDocumentAndDisplay(documents);

        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void findByCostRange() {
        try {
            System.out.println("Enter starting Book Cost range : ");
            Double minCost = Double.parseDouble(buff.readLine());

            System.out.println("Enter ending Book Cost range : ");
            Double maxCost = Double.parseDouble(buff.readLine());

            Bson minCostFilter = Filters.gte("cost", minCost);
            Bson maxCostFilter = Filters.lte("cost", maxCost);
            Bson costRangeFilter = Filters.and(minCostFilter, maxCostFilter);

            FindIterable<Document> documents = tickets.find(costRangeFilter);
            ArrayList<Ticket> foundTickets = HelperFunctions.getTicketsFromDocumentAndDisplay(documents);

        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void removeOldTickets() {
        try {
            // Calculate the date 5 years ago from today
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -5);
            Date fiveYearsAgoDate = cal.getTime();

            Bson deleteFilter = Filters.lt("purchase_date", fiveYearsAgoDate);

            long deletedCount = tickets.deleteMany(deleteFilter).getDeletedCount();
            System.out.println("Deleted " + deletedCount + " old tickets.");
            MyLogger.getInstance().log("Deleted " + deletedCount + " old tickets.");
        } catch(Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }
    }
    public void displayAllTickets() {
        try {
            FindIterable<Document> document = tickets.find();
            ArrayList<Ticket> foundTickets = HelperFunctions.getTicketsFromDocumentAndDisplay(document);
        } catch (Exception e) {
//            e.printStackTrace();
            MyLogger.getInstance().logException("An Exception occurred", e);
        }

    }
}



