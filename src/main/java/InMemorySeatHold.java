import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class InMemorySeatHold implements SeatHold, Comparable<InMemorySeatHold>{
    private String customerEmail;
    private int numSeats;
    private int level;
    private boolean purchaseCompleted;
    private UUID seatHoldId;
    private LocalDateTime creationTime;

    public InMemorySeatHold(int numSeats, int level, String customerEmail) {
        this.seatHoldId = UUID.randomUUID();
        this.numSeats = numSeats;
        this.level = level;
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
    public int getSeatLevel() {
        return level;
    }

    @Override
    public int getNumSeats() {
        return numSeats;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public UUID getSeatHoldId() {
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
