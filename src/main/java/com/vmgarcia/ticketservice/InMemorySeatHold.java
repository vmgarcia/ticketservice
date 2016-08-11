package com.vmgarcia.ticketservice;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class InMemorySeatHold implements SeatHold, Comparable<InMemorySeatHold>{
    private String customerEmail;
    private Map<Integer, Integer> heldSeats;
    private boolean purchaseCompleted;
    private int seatHoldId;
    private LocalDateTime creationTime;
    private static int currentId = 0;
    public InMemorySeatHold(Map<Integer, Integer> heldSeats, String customerEmail) {
        currentId += 1;
        this.seatHoldId = currentId;
        this.heldSeats = heldSeats;
        this.customerEmail = customerEmail;
        this.creationTime = LocalDateTime.now();
        this.purchaseCompleted = false;
    }

    @Override
    public int compareTo(InMemorySeatHold o) {
        if (getSeatHoldId() == o.getSeatHoldId()) {
            return 0;
        }

        if (creationTime.isEqual(o.getCreationTime())) {
            return 0;
        } else if (creationTime.isBefore((o.getCreationTime()))) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean seatsWerePurchased() {
        return purchaseCompleted;
    }

    @Override
    public Map<Integer, Integer> getHeldSeats() {
        return heldSeats;
    }


    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public int getSeatHoldId() {
        return seatHoldId;
    }

    @Override
    public boolean holdExpired(int numberOfSeconds) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = creationTime.plus(numberOfSeconds, ChronoUnit.SECONDS);
        if (now.isAfter(expirationTime) || now.isEqual(expirationTime) ) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean completePurchase(String email, int numberOfSeconds) {
        if (holdExpired(numberOfSeconds) == false && email.equals(customerEmail)) {
            purchaseCompleted = true;
            return true;
        } else {
            return false;
        }
    }
}
