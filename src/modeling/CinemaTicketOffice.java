package modeling;

import exceptions.*;
import interfaces.ITicketManagement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Clase que representa una taquilla de cine.
 * Extiende la clase abstracta TicketOffice e
 * implementa la interfaz ITicketManagement para
 * gestionar la venta y reserva de boletos de cine.
 * @author Lucas
 * @since 06/2023
 * @version 1.0.3
 */

public class CinemaTicketOffice extends TicketOffice implements ITicketManagement<MovieTicket, Double>, Serializable {

    private final HashMap<String, MovieTicket> reservedTickets; // Mapa de boletos reservados
    private final HashMap<String, MovieTicket> ticketStock; // Mapa de boletos disponibles en el inventario

    /**
     * Constructor de la clase CinemaTicketOffice.
     * Inicializa los mapas de boletos reservados y
     * boletos disponibles en el inventario.
     */
    public CinemaTicketOffice() {
        super();
        reservedTickets = new HashMap<>();
        ticketStock = new HashMap<>();
    }

    /**
     * Obtiene el mapa de boletos reservados.
     *
     * @return El mapa de boletos reservados.
     */
    private HashMap<String, MovieTicket> getReservedTickets() {
        return reservedTickets;
    }

    /**
     * Vende un boleto de cine para una película, horario,
     * sala y asiento específicos, con un precio determinado.
     *
     * @param title         El título de la película.
     * @param time          El horario de la función.
     * @param screeningRoom La sala de proyección.
     * @param seat          El número de asiento.
     * @param price         El precio del boleto.
     * @return El boleto vendido.
     * @throws NotAvailableForSaleException Si el asiento no está disponible para la venta.
     */
    @Override
    public MovieTicket sellTicket(String title, Time time, ScreeningRoom screeningRoom, String seat, Double price) throws NotAvailableForSaleException {
        if (isTicketAvailable(title, time, seat)) {
            if (screeningRoom.hasVipSeats()) {
                price = additionalCost();
            }
            MovieTicket ticket = removeTicketFromStock(title, time, seat);
            ticket.setPrice(price);
            return ticket;
        } else {
            throw new NotAvailableForSaleException("This seat is not available.");
        }
    }

    /**
     * Agrega un boleto reservado al mapa de boletos reservados.
     *
     * @param code   El código único del boleto.
     * @param ticket El boleto reservado.
     * @throws AlreadyExistsException Si el código del boleto ya existe en el mapa de boletos reservados.
     */
    public void addReservedTicket(String code, MovieTicket ticket) throws AlreadyExistsException {
        if (!getReservedTickets().containsKey(code)) {
            getReservedTickets().put(code, ticket);
        } else {
            throw new AlreadyExistsException("This ticket code already exists: " + code);
        }
    }

    /**
     * Intercambia un boleto reservado en el mapa de boletos reservados.
     *
     * @param code El código del boleto a intercambiar.
     * @return El boleto intercambiado.
     * @throws NotFoundException Si el código del boleto no se encuentra en el mapa de boletos reservados.
     */
    public MovieTicket exchangeTicket(String code) throws NotFoundException {
        if (getReservedTickets().containsKey(code)) {
            return getReservedTickets().remove(code);
        } else {
            throw new NotFoundException("This ticket code doesn't exist: " + code);
        }
    }

    /**
     * Agrega un boleto al inventario de boletos disponibles.
     *
     * @param ticket El boleto a agregar.
     */
    public void addTicketToStock(MovieTicket ticket) {
        String key = generateTicketKey(ticket.getTitle(), ticket.getTime(), ticket.getSeat());
        ticketStock.put(key, ticket);
    }

    /**
     * Verifica si un boleto está disponible para la venta en el inventario.
     *
     * @param title El título de la película.
     * @param time  El horario de la función.
     * @param seat  El número de asiento.
     * @return true si el boleto está disponible, false en caso contrario.
     */
    public boolean isTicketAvailable(String title, Time time, String seat) {
        String key = generateTicketKey(title, time, seat);
        return ticketStock.containsKey(key);
    }

    /**
     * Elimina un boleto del inventario de boletos disponibles.
     *
     * @param title El título de la película.
     * @param time  El horario de la función.
     * @param seat  El número de asiento.
     * @return El boleto eliminado del inventario.
     */
    public MovieTicket removeTicketFromStock(String title, Time time, String seat) {
        String key = generateTicketKey(title, time, seat);
        return ticketStock.remove(key);
    }

    /**
     * Actualiza el inventario de boletos disponibles para una película en una sala de proyección y horario específicos.
     *
     * @param title         El título de la película.
     * @param time          El horario de la función.
     * @param screeningRoom La sala de proyección.
     */
    public void updateTicketStock(String title, Time time, ScreeningRoom screeningRoom) {
        char seatRow = 'A';  // Letra inicial para la fila de asientos
        int maxSeatsPerRow = screeningRoom.getMaxSeatsPerRow();

        for (int i = 1; i <= screeningRoom.getCapacity(); i++) {
            int seatNumber = i % maxSeatsPerRow;  // Número de asiento (1-10)

            // Si hemos alcanzado el asiento número 10, incrementamos la letra de la fila
            if (seatNumber == 0) {
                seatNumber = maxSeatsPerRow;
            }

            String seat = seatRow + String.valueOf(seatNumber);
            if (!isTicketAvailable(title, time, seat)) {
                MovieTicket ticket = new MovieTicket(title, time, screeningRoom, seat, 0);
                addTicketToStock(ticket);
            }
            if (seatNumber == maxSeatsPerRow) {
                seatRow++;
            }
        }
    }

    /**
     * Regenera el inventario de boletos disponibles para todas las películas y salas de proyección en todos los horarios.
     *
     * @param showtimes Los horarios de las películas y las salas de proyección.
     * @throws NotFoundException     Si no se encuentra una película en los horarios dados.
     * @throws InvalidIndexException Si se produce un índice inválido al acceder a las películas en los horarios dados.
     */
    public void regenerateTicketStock(Showtimes showtimes) throws NotFoundException, InvalidIndexException {
        for (int i = 1; i <= showtimes.getTitlesSize(); i++) {
            String title = showtimes.getTitleByIndex(i);
            if (showtimes.hasScreeningRoomByTitle(title)) {
                ScreeningRoom screeningRoom = showtimes.getScreeningRoomByTitle(title);
                TreeSet<Time> startTimes = showtimes.getStartTimesByTitle(title);
                for (Time time : startTimes) {
                    updateTicketStock(title, time, screeningRoom);
                }
            }
        }
    }


    /**
     * Genera una representación de los asientos disponibles y no disponibles para una película, vistos como un plano del cine,
     * en una sala de proyección y horario específicos.
     *
     * @param title         El título de la película.
     * @param time          El horario de la función.
     * @param screeningRoom La sala de proyección.
     * @return Una cadena de caracteres que representa los asientos disponibles y no disponibles.
     */
    public String listSeats(String title, Time time, ScreeningRoom screeningRoom) {
        StringBuilder builder = new StringBuilder();
        char seatRow = 'A';  // Letra inicial para la fila de asientos
        int maxSeatsPerRow = screeningRoom.getMaxSeatsPerRow();
        int sideRows = 2;
        if (maxSeatsPerRow <= 6) {
            sideRows = 1;
        }
        int middleRow = maxSeatsPerRow - sideRows * 2;

        for (int i = 1; i <= screeningRoom.getCapacity(); i++) {
            int seatNumber = i % maxSeatsPerRow;  // Número de asiento

            if (seatNumber == 0) {
                seatNumber = maxSeatsPerRow;
            }

            String seat = seatRow + String.valueOf(seatNumber);
            if (!isTicketAvailable(title, time, seat)) {
                builder.append("\u001B[31m"); // Color rojo para los asientos no disponibles
            } else {
                builder.append("\u001B[32m"); // Color verde para los asientos disponibles
            }
            builder.append(seat).append("\u001B[0m").append(" "); // Restablecer el color
            if ((seatNumber == sideRows) || (seatNumber == (middleRow + sideRows))) {
                builder.append("  ");
            }

            if (seatNumber == maxSeatsPerRow) {
                seatRow++;
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Verifica si hay boletos disponibles para la venta para una película en una sala de proyección y horario específicos.
     *
     * @param title         El título de la película.
     * @param time          El horario de la función.
     * @param screeningRoom La sala de proyección.
     * @return true si hay boletos disponibles, false en caso contrario.
     */
    public boolean hasStock(String title, Time time, ScreeningRoom screeningRoom) {
        char seatRow = 'A';  // Letra inicial para la fila de asientos
        int maxSeatsPerRow = screeningRoom.getMaxSeatsPerRow();
        int i = 1;
        boolean ans = false;
        while ((i <= screeningRoom.getCapacity()) && !ans) {
            int seatNumber = i % maxSeatsPerRow;  // Número de asiento

            if (seatNumber == 0) {
                seatNumber = maxSeatsPerRow;
            }
            String seat = seatRow + String.valueOf(seatNumber);
            if (isTicketAvailable(title, time, seat)) {
                ans = true;
            }
            if (seatNumber == maxSeatsPerRow) {
                seatRow++;
            }
            i++;
        }
        return ans;
    }

    /**
     * Elimina los boletos del inventario de boletos disponibles para una película en una sala de proyección y horario específicos.
     *
     * @param title         El título de la película.
     * @param time          El horario de la función.
     * @param screeningRoom La sala de proyección.
     */
    public void removeTicketStock(String title, Time time, ScreeningRoom screeningRoom) {
        char seatRow = 'A';  // Letra inicial para la fila de asientos
        int maxSeatsPerRow = screeningRoom.getMaxSeatsPerRow();

        for (int i = 1; i <= screeningRoom.getCapacity(); i++) {
            int seatNumber = i % maxSeatsPerRow;  // Número de asiento (1-10)

            // Si hemos alcanzado el asiento número 10, incrementamos la letra de la fila
            if (seatNumber == 0) {
                seatNumber = maxSeatsPerRow;
            }

            String seat = seatRow + String.valueOf(seatNumber);
            if (isTicketAvailable(title, time, seat)) {
                removeTicketFromStock(title, time, seat);
            }

            if (seatNumber == maxSeatsPerRow) {
                seatRow++;
            }
        }
    }
}
