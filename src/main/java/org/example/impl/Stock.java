package org.example.impl;

import java.util.Date;

public interface Stock {
    void saveTicket();
    void updateTicketById();
    void removeTicketById();
    void findByTitle();
    void findByType();
    void findByCostRange();
    void removeOldTickets();
    void findByCost();
    void displayAllTickets();
}
