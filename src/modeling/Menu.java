package modeling;
import exceptions.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import static modeling.FileManagement.readFile;

/**
 * La clase Menu representa el menú de un cine. Permite a los usuarios acceder a diferentes opciones
 * según su rol (administrador o público).
 * @author Martin Berasueta, Lucas Padilla, Matias Figliuolo
 * @since 06/2023
 * @version 1.0.3
 */
public class Menu {
    private final Cinema cinema;
    private final Scanner scanner;
    private final String adminPassword;

    /**
     * Crea una instancia de la clase Menu.
     * cinema: se obtiene con el método estático readFile(), el cual devuelve el Cinema anteriormente guardado, o una instancia nueva en el caso de que no exista el archivo.
     * scanner: se usa para que el usuario ingrese por teclado.
     * adminPassword: contraseña para usar el menú de administrador
     */
    public Menu() {
        this.cinema = readFile();
        this.scanner = new Scanner(System.in);
        this.adminPassword = "admin123"; // Contraseña de administrador predeterminada
    }

    /**
     * Obtiene el cine del menú.
     */
    private Cinema getCinema() {
        return cinema;
    }

    /**
     * Muestra el menú de administrador y permite realizar diferentes acciones.
     */
    private void adminMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║       Cinema Menu           ║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║ 1. Add Movie                ║");
            System.out.println("║ 2. Add Screening Room       ║");
            System.out.println("║ 3. Add Start Time           ║");
            System.out.println("║ 4. Remove Movie             ║");
            System.out.println("║ 5. Remove Start Time        ║");
            System.out.println("║ 6. Remove Screening Room    ║");
            System.out.println("║ 7. Set Ticket Price         ║");
            System.out.println("║ 8. Regenerate Ticket Stock  ║");
            System.out.println("║ 0. Exit                     ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                switch (choice) {
                    case 1 -> addMovie();
                    case 2 -> addScreeningRoom();
                    case 3 -> addShowtime();
                    case 4 -> removeMovie();
                    case 5 -> removeTime();
                    case 6 -> removeScreeningRoom();
                    case 7 -> setPrices();
                    case 8 -> regenerateTicketStock();
                    case 0 -> exit = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    /**
     * Muestra el menú público y permite realizar diferentes acciones.
     */
    private void publicMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔══════════════════════════╗");
            System.out.println("║       Public Menu        ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║ 1. Showtimes             ║");
            System.out.println("║ 2. Show Movie Details    ║");
            System.out.println("║ 3. Show Screening Rooms  ║");
            System.out.println("║ 4. Buy Ticket At Cinema  ║");
            System.out.println("║ 5. Buy Ticket Online     ║");
            System.out.println("║ 6. Redeem Ticket         ║");
            System.out.println("║ 0. Exit                  ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                switch (choice) {
                    case 1 -> showAllShowtimes();
                    case 2 -> showMovieDetails();
                    case 3 -> showAllScreeningRooms();
                    case 4 -> buyTicketAtCinema();
                    case 5 -> buyTicketOnline();
                    case 6 -> redeemTicket();
                    case 0 -> exit = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    /**
     * Muestra el menú principal y permite seleccionar el rol (administrador o público).
     */
    public void masterMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("╔══════════════════════════╗");
            System.out.println("║       Master Menu        ║");
            System.out.println("╠══════════════════════════╣");
            System.out.println("║ 1. Admin Menu            ║");
            System.out.println("║ 2. Public Menu           ║");
            System.out.println("║ 0. Exit                  ║");
            System.out.println("╚══════════════════════════╝");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                switch (choice) {
                    case 1 -> startAdminMenu();
                    case 2 -> publicMenu();
                    case 0 -> {
                        FileManagement.writeFile(getCinema());
                        exit = true;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.nextLine(); // Clear the input buffer
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    /**
     * Solicita la contraseña de administrador al usuario y muestra el menú de administrador si la contraseña es correcta.
     */
    private void startAdminMenu() {
        System.out.print("Enter the admin password: ");
        String password = scanner.nextLine();

        if (password.equals(adminPassword)) {
            adminMenu();
        } else {
            System.out.println("Incorrect password. Access denied.");
        }
    }

    /**
     * Agrega una película al cine.
     */
    private void addMovie() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();

        try {
            cinema.getShowtimes().addMovie(title);
            System.out.println("Movie added successfully.");
        } catch (MovieSearchFailedException e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (EmptyTitleException e) {
            System.out.println(e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Agrega un horario de proyección para una película.
     */
    private void addShowtime() {
        if (cinema.arePricesSet()) {
            try {
                System.out.println(cinema.getTitles());
                System.out.print("Enter movie index: ");
                int index = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (cinema.getShowtimes().hasScreeningRoomByIndex(index)) {
                    System.out.print("Enter showtime (hh:mm): ");
                    String showtimeStr = scanner.nextLine();

                    if (formatIsCorrect(showtimeStr)) {
                        String[] parts = showtimeStr.split(":");
                        int hour = Integer.parseInt(parts[0]);
                        int minute = Integer.parseInt(parts[1]);

                        if ((hour < 0) || (hour > 23)) {
                            System.out.println("Error: Hour cannot be " + hour);
                        } else if ((minute < 0) || (minute > 59)) {
                            System.out.println("Error: Minutes cannot be " + minute);
                        } else {
                            try {
                                cinema.addTime(index, hour, minute);
                                System.out.println("Showtime added successfully.");
                            } catch (NotFoundException e) {
                                System.out.println(e.getMessage());
                            } catch (AlreadyExistsException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Error: Format must be (hh:mm)");
                    }
                } else {
                    System.out.println("This movie doesn't have a screen assigned yet.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.nextLine(); // Clear the input buffer
            } catch (EmptyShowtimes e) {
                System.out.println(e.getMessage());
            } catch (InvalidIndexException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Ticket price and additional cost must be set first.");
        }
    }

    /**
     * Verifica si el formato del horario de proyección es correcto.
     *
     * @param  showtimeStr El horario de proyección en formato de cadena.
     * @return true si el formato es correcto, false de lo contrario.
     */
    private boolean formatIsCorrect(String showtimeStr) {
        // Verificar que el formato sea "hh:mm" y no contenga otros caracteres
        return showtimeStr.matches("\\d{2}:\\d{2}");
    }

    /**
     * Agrega una sala de proyección para una película.
     */
    private void addScreeningRoom() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (!cinema.getShowtimes().hasScreeningRoomByIndex(index)) {
                ScreeningRoom screeningRoom = null;
                boolean validChoice = false;
                while (!validChoice) {
                    System.out.println("Select Screening Room:");
                    System.out.println("1. Standard");
                    System.out.println("2. Dolby Atmos");
                    System.out.println("3. VIP");
                    System.out.println("4. IMAX");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1 -> {
                            screeningRoom = ScreeningRoom.STANDARD;
                            validChoice = true;
                        }
                        case 2 -> {
                            screeningRoom = ScreeningRoom.DOLBY_ATMOS;
                            validChoice = true;
                        }
                        case 3 -> {
                            screeningRoom = ScreeningRoom.VIP;
                            validChoice = true;
                        }
                        case 4 -> {
                            screeningRoom = ScreeningRoom.IMAX;
                            validChoice = true;
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                }

                try {
                    cinema.getShowtimes().addScreeningRoom(index, screeningRoom);
                    System.out.println("Screening room added successfully.");
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Error: You have to remove the existing screen from the movie first.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra toda la cartelera con sus películas, horarios ordenados cronológicamente, sala de proyección y duración de la película.
     */
    private void showAllShowtimes() {
        System.out.println(cinema.getShowtimes());
    }

    /**
     * Muestra los detalles en cartelera de una película específica.
     */
    private void showMovieDetails() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            try {
                Movie movie = cinema.getShowtimes().getMovieByIndex(index);
                System.out.println(movie);
            } catch (NotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra todas las salas de proyección del cine.
     */
    private void showAllScreeningRooms() {
        System.out.println(cinema.showAllScreeningRooms());
    }

    /**
     * Compra un boleto en el cine.
     */
    private void buyTicketAtCinema() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int movieIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (cinema.getShowtimes().isAvailableForSale(movieIndex)) {
                String movieTitle = cinema.getShowtimes().getTitleByIndex(movieIndex);

                System.out.println(cinema.getShowtimes().getMovieShowtimes(movieTitle));
                System.out.print("Enter showtime index: ");
                int startTimeIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                try {
                    Time startTime = cinema.getShowtimes().getTimeByIndex(movieTitle, startTimeIndex);
                    ScreeningRoom screen = cinema.getShowtimes().getScreeningRoomByTitle(movieTitle);

                    if (cinema.hasStock(movieTitle, startTime, screen)) {
                        // Generate the list of available seats
                        String availableSeats = cinema.listSeats(movieTitle, startTime, screen);
                        System.out.println("Available seats:\n" + availableSeats);

                        System.out.print("Enter seat number: ");
                        String seatNumber = scanner.nextLine();

                        try {
                            MovieTicket ticket = cinema.buyTicketAtCinema(movieTitle, startTime, screen, seatNumber);
                            System.out.println("Ticket purchased successfully!\nYour ticket:\n\n" + ticket);
                        } catch (NotAvailableForSaleException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println(movieTitle + " at " + startTime + " is out of stock.");
                    }
                } catch (NotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("This movie is not available for sale yet.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Compra un boleto online.
     */
    private void buyTicketOnline() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int movieIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (cinema.getShowtimes().isAvailableForSale(movieIndex)) {
                String movieTitle = cinema.getShowtimes().getTitleByIndex(movieIndex);

                System.out.println(cinema.getShowtimes().getMovieShowtimes(movieTitle));
                System.out.print("Enter showtime index: ");
                int startTimeIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                try {
                    Time startTime = cinema.getShowtimes().getTimeByIndex(movieTitle, startTimeIndex);
                    ScreeningRoom screen = cinema.getShowtimes().getScreeningRoomByTitle(movieTitle);

                    if (cinema.hasStock(movieTitle, startTime, screen)) {

                        // Generate the list of available seats
                        String availableSeats = cinema.listSeats(movieTitle, startTime, screen);
                        System.out.println("Available seats:\n" + availableSeats);

                        System.out.print("Enter seat number: ");
                        String seatNumber = scanner.nextLine();

                        String code = cinema.buyTicketOnline(movieTitle, startTime, screen, seatNumber);

                        System.out.println("Ticket purchased successfully!\nYour reservation code: " + code);
                    }
                    else {
                        System.out.println(movieTitle + " at " + startTime + " is out of stock.");
                    }
                } catch (NotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (NotAvailableForSaleException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("This movie is not available for sale yet.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Canjea un boleto utilizando un código de reserva.
     */
    private void redeemTicket() {
        System.out.println("Enter code: ");
        String code = scanner.nextLine();
        try {
            MovieTicket ticket = cinema.redeemTicket(code);
            System.out.println(ticket);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina una película de la cartelera.
     */
    private void removeMovie() {
        try {
            System.out.println(cinema.getTitles());
            System.out.println("Enter movie index: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            cinema.removeMovie(index);
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina un horario de proyección de una película.
     */
    private void removeTime() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (cinema.getShowtimes().hasStartTimesByIndex(index)) {
                String title = cinema.getShowtimes().getTitleByIndex(index);
                System.out.println(cinema.getShowtimes().getMovieShowtimes(title));
                System.out.print("Enter showtime index: ");
                int startTimeIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                try {
                    Time startTime = cinema.getShowtimes().getTimeByIndex(title, startTimeIndex);
                    cinema.removeTime(index, startTime);
                    System.out.println("Showtime removed successfully.");
                } catch (NotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("This movie doesn't have any start times assigned.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Elimina la sala de proyección de una película.
     */
    private void removeScreeningRoom() {
        try {
            System.out.println(cinema.getTitles());
            System.out.print("Enter movie index: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (cinema.getShowtimes().hasScreeningRoomByIndex(index)) {
                try {
                    cinema.removeScreeningRoom(index);
                    System.out.println("Screening room removed successfully.");
                } catch (NotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("This movie doesn't have a screening room assigned yet.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        } catch (EmptyShowtimes e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Establece los precios de los boletos.
     */
    private void setPrices() {
        try {
            System.out.println("Enter ticket price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter additional cost for VIP seats: ");
            double additionalCost = scanner.nextDouble();
            scanner.nextLine();
            cinema.setPrices(price, additionalCost);
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        }
    }

    /**
     * Regenera el stock de boletos del cine.
     */
    private void regenerateTicketStock() {
        cinema.regenerateTicketStock();
        System.out.println("Ticket Stock has been regenerated!");
    }
}
