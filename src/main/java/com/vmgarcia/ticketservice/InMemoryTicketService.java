package com.vmgarcia.ticketservice;


import java.util.*;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class InMemoryTicketService implements TicketService {
    private int secondsForExpiration;
    private Map<Integer, SeatHold> seatHoldLookup;
    private PriorityQueue<SeatHold> expirationQueue;
    private Map<Integer, Integer> availableSeating;
    private Map<Integer, SeatHold> reservations;

    public InMemoryTicketService(int secondsForExpiration) {
        this.secondsForExpiration = secondsForExpiration;
        this.seatHoldLookup = new HashMap<>();
        this.availableSeating = new HashMap<>();
        this.availableSeating.put(1, 25*50);
        this.availableSeating.put(2, 20*100);
        this.availableSeating.put(3, 15*100);
        this.availableSeating.put(4, 15*100);
        this.reservations = new HashMap<>();
        this.expirationQueue = new PriorityQueue<>();


    }

    private void freeExpiredHolds() {
        while (expirationQueue.size() > 0 && expirationQueue.peek().holdExpired(secondsForExpiration)) {
            SeatHold hold = expirationQueue.poll();
            if (hold.seatsWerePurchased() == false) {

                Map<Integer, Integer> heldSeats = hold.getHeldSeats();
                for(Map.Entry<Integer, Integer> e: heldSeats.entrySet()) {
                    int seatLevel = e.getKey();
                    int numberOfSeats = e.getValue();
                    availableSeating.replace(seatLevel, availableSeating.get(seatLevel) + numberOfSeats);
                }
            }
        }
    }
    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        if (venueLevel.isPresent()) {
            int level = venueLevel.get();
            return availableSeating.get(level);
        } else {
            int seatCount = availableSeating.get(1) + availableSeating.get(2) + availableSeating.get(3)
                    + availableSeating.get(4);
            return seatCount;
        }
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
        int lowerBound = minLevel.isPresent()? minLevel.get(): 0;
        int upperBound = maxLevel.isPresent()? maxLevel.get(): 4;
        int seatsReserved = 0;
        Map<Integer, Integer> reservedSeating = new HashMap<>();
        for (int level = lowerBound;  seatsReserved < numSeats && level <= upperBound; level++) {
            int seatsAvailableAtLevel = numSeatsAvailable(Optional.ofNullable(level));
            int seatsAtLevel = 0;
            if (seatsAvailableAtLevel + seatsReserved <= numSeats) {
                seatsAtLevel = seatsAvailableAtLevel;
                seatsReserved += seatsAtLevel;
            } else if (seatsAvailableAtLevel + seatsReserved > numSeats) {
                seatsAtLevel = numSeats - seatsReserved;
                seatsReserved += seatsAtLevel;
            }
            if (seatsAtLevel > 0) {
                reservedSeating.put(level, seatsAtLevel);
            }
        }
        if (seatsReserved == numSeats) {
            SeatHold hold = new InMemorySeatHold(reservedSeating, customerEmail);
            for (Map.Entry<Integer, Integer> e: reservedSeating.entrySet()) {
                int level = e.getKey();
                int seatCount = e.getValue();
                availableSeating.replace(level, availableSeating.get(level) - seatCount);

            }
            seatHoldLookup.put(hold.getSeatHoldId(), hold);
            expirationQueue.add(hold);
            return hold;
        } else {
            return null;
        }
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold hold = seatHoldLookup.get(seatHoldId);
        boolean purchaseSuccess = hold.completePurchase(customerEmail, secondsForExpiration);
        if (purchaseSuccess) {
            reservations.put(hold.getSeatHoldId(), hold);
            return String.format("%d", hold.getSeatHoldId());
        } else {
            return null;
        }
    }
}
