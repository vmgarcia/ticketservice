import java.time.LocalDateTime;

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
 * Get the level of the seats being held.
 *
 * @return an integer indicating the level of the seat being held
 */
    int getSeatLevel();

/**
 * Get the number of seats being held.
 *
 * @return an integer indicating the number of seats being held.
 */
    int getSeatCount();

/**
 * Get the timestamp from when this SeatHold was created
 *
 * @return a LocalDateTime object that indicates the time when this hold was created.
 */
    LocalDateTime getTimeOfCreation();

/**
 * Says if the amount of seconds the seats can be reserved for has passed.
 *
 * @param numberOfSeconds the number of seconds seats can be reserved for
 * @return True if the number of seconds seats can be reserved for has passed.
 *          False otherwise.
 */
    boolean holdExpired(int numberOfSeconds);
}
