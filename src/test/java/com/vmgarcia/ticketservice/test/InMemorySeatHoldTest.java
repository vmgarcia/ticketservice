package com.vmgarcia.ticketservice.test;

import com.vmgarcia.ticketservice.InMemorySeatHold;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.*;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class InMemorySeatHoldTest {

    @Test
    public void testIfExpires() {
        Map<Integer, Integer> testSeatMap = new HashMap<>();
        testSeatMap.put(1, 25*50);
        testSeatMap.put(2, 20*100);
        testSeatMap.put(3, 15*100);
        testSeatMap.put(4, 15*100);

        InMemorySeatHold seatHold = new InMemorySeatHold(testSeatMap, "testemail@email.com");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert seatHold.holdExpired(1) == true;
        assert seatHold.holdExpired(2) == false;
    }

    @Test
    public void testCompareTo() {
        Map<Integer, Integer> testSeatMap = new HashMap<>();
        InMemorySeatHold seatHold1 = new InMemorySeatHold(testSeatMap, "testemail@email.com");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InMemorySeatHold seatHold2 = new InMemorySeatHold(testSeatMap, "testemail@email.com");

        InMemorySeatHold seatHold3 = new InMemorySeatHold(testSeatMap, "testemail@email.com");

        assert seatHold1.compareTo(seatHold2) == -1;
        assert seatHold2.compareTo(seatHold1) == 1;
        assert seatHold3.compareTo(seatHold2) == 0;

    }

    @Test
    public void testPurchase() {
        Map<Integer, Integer> testSeatMap = new HashMap<>();
        testSeatMap.put(1, 25*50);
        testSeatMap.put(2, 20*100);
        testSeatMap.put(3, 15*100);
        testSeatMap.put(4, 15*100);

        InMemorySeatHold seatHold1 = new InMemorySeatHold(testSeatMap, "testemail@email.com");
        InMemorySeatHold seatHold2 = new InMemorySeatHold(testSeatMap, "testemail@email.com");

        seatHold1.completePurchase("testemail@email.com", 1);
        assert seatHold1.seatsWerePurchased() == true;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert seatHold2.completePurchase("testemail@email.com", 1) == false;

    }

    @Test
    public void testCorrectIdCreation() {
        Map<Integer, Integer> testSeatMap = new HashMap<>();
        testSeatMap.put(1, 25*50);
        testSeatMap.put(2, 20*100);
        testSeatMap.put(3, 15*100);
        testSeatMap.put(4, 15*100);
        InMemorySeatHold.reinitializeId();
        InMemorySeatHold seatHold1 = new InMemorySeatHold(testSeatMap, "testemail@email.com");
        InMemorySeatHold seatHold2 = new InMemorySeatHold(testSeatMap, "testemail@email.com");
        InMemorySeatHold seatHold3 = new InMemorySeatHold(testSeatMap, "testemail@email.com");

        assert  seatHold1.getSeatHoldId() == 1;
        assert  seatHold2.getSeatHoldId() == 2;
        assert  seatHold3.getSeatHoldId() == 3;
    }

}
