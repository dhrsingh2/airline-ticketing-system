package org.example;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.database.DatabaseConnection;
import org.example.impl.Airline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buff = new BufferedReader(isr);

        int selectedOperation = 0;

        // connecting to database
        String connectionString = "mongodb://localhost:27017";
        DatabaseConnection mongoClient = new DatabaseConnection();
        mongoClient.connect(connectionString);

        String databaseName = "airline_ticketing_db";
        mongoClient.getDatabase(databaseName);

        String collectionName = "tickets";
        MongoCollection<Document> tickets = mongoClient.getCollection(collectionName);

        // menu-driven program starts here
        System.out.print("\nWelcome to the Airline Ticketing System\n\n");
        do {
           System.out.print("\nSelect an option : \n");
           System.out.print("0. Exit\n1. Save a ticket\n2. Update a ticket by ID\n3. Remove a ticket by ID\n4. Find tickets by title\n5. Find tickets by cost range\n6. Find tickets by type\n7. Find tickets by cost\n8. Remove tickets that are more than two years old\n9. Display all tickets\n");

            try {
                selectedOperation = Integer.parseInt(buff.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("User selected option " + selectedOperation + "\n\n");

            Airline airline = new Airline(tickets);

            switch (selectedOperation) {
                case 1 : airline.saveTicket(); break;
                case 2 : airline.updateTicketById(); break;
                case 3 : airline.removeTicketById(); break;
                case 4 : airline.findByTitle(); break;
                case 5 : airline.findByCostRange(); break;
                case 6 : airline.findByType(); break;
                case 7 : airline.findByCost(); break;
                case 8 : airline.removeOldTickets(); break;
                case 9 : airline.displayAllTickets(); break;
            }
        } while (selectedOperation != 0);

        // closing connection to database
        mongoClient.close();
    }
}