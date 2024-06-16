import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Reservation {
    private int id;
    private String name;
    private String date;
    private int numberOfGuests;

    public Reservation(int id, String name, String date, int numberOfGuests) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.numberOfGuests = numberOfGuests;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
}

class ReservationSystem {
    private List<Reservation> reservations = new ArrayList<>();
    private int nextId = 1;

    public Reservation makeReservation(String name, String date, int numberOfGuests) {
        Reservation reservation = new Reservation(nextId++, name, date, numberOfGuests);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Reservation getReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    public boolean cancelReservation(int id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservations.remove(reservation);
            return true;
        }
        return false;
    }
}

public class ReservationSystemUI {
    private ReservationSystem reservationSystem = new ReservationSystem();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. View all reservations");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Exit");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.nextLine(); // clear the invalid input
                continue;
            }
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Date: ");
                    String date = scanner.nextLine();
                    System.out.print("Number of guests: ");
                    int numberOfGuests;
                    try {
                        numberOfGuests = scanner.nextInt();
                        if (numberOfGuests <= 0) {
                            throw new IllegalArgumentException("Number of guests must be positive.");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Number of guests must be a positive integer.");
                        scanner.nextLine(); // clear the invalid input
                        continue;
                    }
                    scanner.nextLine(); // consume the newline

                    Reservation reservation = reservationSystem.makeReservation(name, date, numberOfGuests);
                    System.out.println("Reservation made with ID " + reservation.getId());
                    break;
                case 2:
                    System.out.println("Reservations:");
                    for (Reservation r : reservationSystem.getReservations()) {
                        System.out.println(r.getId() + " - " + r.getName() + " - " + r.getDate() + " - " + r.getNumberOfGuests());
                    }
                    break;
                case 3:
                    System.out.print("Reservation ID to cancel: ");
                    int id;
                    try {
                        id = scanner.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid input. Reservation ID must be an integer.");
                        scanner.nextLine(); // clear the invalid input
                        continue;
                    }
                    scanner.nextLine(); // consume the newline

                    if (reservationSystem.cancelReservation(id)) {
                        System.out.println("Reservation canceled");
                    } else {
                        System.out.println("Reservation not found");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        ReservationSystemUI obj = new ReservationSystemUI();
        obj.start();
    }
}

