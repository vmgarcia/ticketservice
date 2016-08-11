package com.vmgarcia.ticketservice;


import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by Victor Garcia on 8/11/2016.
 */
public class CommandLineTicketService {
    public static void main(String[] args) {
        TicketService ts = new InMemoryTicketService(20);

        System.out.println("Welcome to the Ticket Service App!");
        Scanner scan = new Scanner(System.in);

        while(true) {
            System.out.println("1. Check what seats are available.");
            System.out.println("2. Find and hold seats.");
            System.out.println("3. Buy and reserve seats.");
            System.out.println("4. Exit.");
            System.out.println("Please select the option you wish to use: ");
            String choice = "";
            choice = scan.nextLine();

            if (choice.equals("1")) {
                printAvailableSeats(ts, scan);
            } else if (choice.equals("2")) {
                processSeatHold(ts, scan);
            } else if (choice.equals("3")) {
                processSeatReservation(ts, scan);
            } else {
                //scan.close();
                break;
            }


        }
    }

    private static void processSeatReservation(TicketService ts, Scanner scan) {
        System.out.println("To finish reserving your seats please input");
        System.out.println("email: ");
        String email = scan.nextLine();
        System.out.println("Hold id: ");
        int holdId = Integer.parseInt(scan.nextLine());
        String confirmation = ts.reserveSeats(holdId, email);
        if (confirmation != null) {
            System.out.println("Congratulations on successfully reserving your seats!");

        } else {
            System.out.println("Sorry it seems that you either didn't put in a hold for the seats or your hold expired.");
            System.out.println("Please try again.");
        }
    }

    private static void processSeatHold(TicketService ts, Scanner scan) {
        System.out.println("What is the minimum level you want to buy tickets for?");
        System.out.println("Leave blank to see availability for no minimum.");
        String minString = scan.nextLine();
        System.out.println("What is the maximum level you want to buy tickets for?");
        System.out.println("Leave blank to see availability for no minimum.");
        String maxString = scan.nextLine();
        System.out.println("How many seat do you wish to purchase?");
        String seatCountString = scan.nextLine();
        System.out.println("What is your email?");
        String customerEmail = scan.nextLine();
        int min = minString.isEmpty()? 1: Integer.parseInt(minString);
        int max = maxString.isEmpty()? 4: Integer.parseInt(maxString);
        int seatCount = Integer.parseInt(seatCountString);

        SeatHold hold = ts.findAndHoldSeats(seatCount, Optional.ofNullable(min),
                Optional.ofNullable(max), customerEmail);
        if (hold == null) {
            System.out.println("We are sorry, there are no seats available on the levels you requested");
            return;
        }
        System.out.println(String.format("Congrats! You have a hold with id %d and email %s for ",
                hold.getSeatHoldId(), customerEmail));
        for (Map.Entry<Integer, Integer> e: hold.getHeldSeats().entrySet()) {
            System.out.println(String.format("%d seats on level %d", e.getKey(), e.getValue()));
        }

        System.out.println("You have 20 seconds to confirm your order");

    }

    private static void printAvailableSeats(TicketService ts, Scanner scan) {
        System.out.println("What levels do you want to see seat availability for?");
        System.out.println("Leave blank to see availability for all levels.");
        String levelString = scan.nextLine();
        if (levelString.isEmpty()) {
            for (int level = 1; level <= 4; ++level) {
                int availability = ts.numSeatsAvailable(Optional.ofNullable(level));
                System.out.println(String.format("Level %d has %d seats available.", level, availability));
            }
        } else {
            int level = Integer.parseInt(levelString);
            int availability = ts.numSeatsAvailable(Optional.ofNullable(level));
            System.out.println(String.format("Level %d has %d seats available.", level, availability));
        }
    }
}
