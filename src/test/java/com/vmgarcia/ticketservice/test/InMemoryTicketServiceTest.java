package com.vmgarcia.ticketservice.test;

import com.vmgarcia.ticketservice.SeatHold;
import com.vmgarcia.ticketservice.InMemoryTicketService;
import com.vmgarcia.ticketservice.TicketService;
import org.junit.Test;

import java.security.cert.PKIXRevocationChecker;
import java.util.Optional;

import static java.lang.Thread.sleep;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class InMemoryTicketServiceTest {
    @Test
    public void initialNumSeatsAvailableTest(){
        TicketService ts = new InMemoryTicketService(2);
        assert ts.numSeatsAvailable(Optional.ofNullable(1)) == 25*50;
        assert ts.numSeatsAvailable(Optional.ofNullable(2)) == 20*100;
        assert ts.numSeatsAvailable(Optional.ofNullable(3)) == 15*100;
        assert ts.numSeatsAvailable(Optional.ofNullable(4)) == 15*100;
        assert ts.numSeatsAvailable(Optional.empty()) == 25*50+20*100+2*15*100;
    }

    @Test
    public void findAndHoldSeatsTest() {
        TicketService ts = new InMemoryTicketService(2);
        SeatHold hold = ts.findAndHoldSeats(50, Optional.ofNullable(1), Optional.empty(), "email@email.com");
        assert ts.numSeatsAvailable(Optional.ofNullable(1)) == 24*50;

        hold = ts.findAndHoldSeats(24*50 + 100, Optional.ofNullable(1), Optional.empty(), "email@email.com");
        assert ts.numSeatsAvailable(Optional.ofNullable(1)) == 0;
        assert ts.numSeatsAvailable(Optional.ofNullable(2)) == 19*100;

        hold = ts.findAndHoldSeats((19+15+15)*100, Optional.empty(), Optional.empty(), "email@email.com");
        assert ts.numSeatsAvailable(Optional.ofNullable(1)) == 0;
        assert ts.numSeatsAvailable(Optional.ofNullable(2)) == 0;
        assert ts.numSeatsAvailable(Optional.ofNullable(3)) == 0;
        assert ts.numSeatsAvailable(Optional.ofNullable(4)) == 0;


    }

    @Test
    public void purchaseTest() {
        TicketService ts = new InMemoryTicketService(2);
        SeatHold hold1 = ts.findAndHoldSeats(50, Optional.ofNullable(1), Optional.empty(), "email@email.com");

        SeatHold hold2 = ts.findAndHoldSeats(24*50 + 100, Optional.ofNullable(1), Optional.empty(), "email@email.com");

        ts.findAndHoldSeats((19+15+15)*100, Optional.empty(), Optional.empty(), "email@email.com");
        assert String.format("%d", hold2.getSeatHoldId()).equals(ts.reserveSeats(hold2.getSeatHoldId(),
                "email@email.com"));
        assert null == ts.reserveSeats(hold1.getSeatHoldId(), "BAD EMAIL");
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert ts.numSeatsAvailable(Optional.ofNullable(1)) == 50;
        assert ts.numSeatsAvailable(Optional.ofNullable(2)) == 19*100;
    }
}
