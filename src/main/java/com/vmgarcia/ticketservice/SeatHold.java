package com.vmgarcia.ticketservice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Victor Garcia on 8/10/2016.
 */
public interface SeatHold {
/**
 * Tell if the seats being held have been purchased
 *
 * @return false if the max amount of time for a reservation has passed. true otherwise.
 */
    boolean seatsWerePurchased();

/**
 * Get the information on which seats are being held.
 *
 * @return a Map from the level of the seat being held to the number of seats being held on that level
 */
    Map<Integer, Integer> getHeldSeats();


/**
 * Get the timestamp from when this SeatHold was created
 *
 * @return a LocalDateTime object that indicates the time when this hold was created.
 */
    LocalDateTime getCreationTime();

/**
 * Get the id of the SeatHold
 *
 * @return an int ID that is generated when the object is created
 */
    int getSeatHoldId();

    /**
 * Says if the amount of seconds the seats can be reserved for has passed.
 *
 * @param numberOfSeconds the number of seconds seats can be reserved for
 * @return True if the number of seconds seats can be reserved for has passed.
 *          False otherwise.
 */
    boolean holdExpired(int numberOfSeconds);

/**
 * Complete the purchase of the seats being held.
 *
 * @param email the email of the purchaser. Used to confirm that the right person is
 *              completing the purchase
 * @return returns true if the purchase was successful, false otherwise.
 */
    boolean completePurchase(String email, int numberOfSeconds);
}
