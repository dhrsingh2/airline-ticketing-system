package org.example.database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.util.MyLogger;

public class DatabaseConnection {
    MongoClient mongoClient = null;
    MongoDatabase database = null;

    public void connect(String connectionString) {
        try {
            this.mongoClient = MongoClients.create(connectionString);
//            System.out.println("Connected to MongoDB");
            MyLogger.getInstance().log("Connected to MongoDB");
        } catch (Exception e) {
//            System.out.println("Error while connecting to MongoDB: " + e.getMessage());
            MyLogger.getInstance().log("Error while connecting to MongoDB: " + e.getMessage());
        }
    }

    public MongoDatabase getDatabase(String databaseName) {
        if (mongoClient != null) {
            this.database = mongoClient.getDatabase(databaseName);
            return database;
        } else {
//            System.out.println("Error: No connection to MongoDB");
            MyLogger.getInstance().log("Error: No connection to MongoDB");
            return null;
        }
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        if (database != null) {
            return database.getCollection(collectionName);
        } else {
//            System.out.println("Error: No database selected");
            MyLogger.getInstance().log("Error: No database selected");
            return null;
        }
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
//            System.out.println("Connection to MongoDB closed");
            MyLogger.getInstance().log("Connection to MongoDB closed");
        }
    }
}