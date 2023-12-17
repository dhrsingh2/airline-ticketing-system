package org.example.util;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.example.dto.Ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HelperFunctions {
    public static Date covertJavaStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static ArrayList<Ticket> getTicketsFromDocumentAndDisplay(FindIterable<Document> document) {
        ArrayList<Ticket> foundTickets = new ArrayList<Ticket>();
        try {
            for (Document ticket : document) {
                Integer id = ticket.getInteger("id");
                String title = ticket.getString("title");
                String type = ticket.getString("type");

                // Check the data type of the "cost" field and get the value accordingly
                Double cost;
                if (ticket.get("cost") instanceof Integer) {
                    cost = ((Integer) ticket.get("cost")).doubleValue();
                } else {
                    cost = ticket.getDouble("cost");
                }

                Date purchaseDate;
                if (ticket.get("purchase_date") instanceof String) {
                    String dateString = ticket.getString("purchase_date");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    purchaseDate = dateFormat.parse(dateString);
                } else {
                    purchaseDate = ticket.getDate("purchase_date");
                }

                Ticket ticketObj = new Ticket(id, title, type, cost, purchaseDate);
                ticketObj.displayDetails();
                foundTickets.add(ticketObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundTickets;
    }
}
