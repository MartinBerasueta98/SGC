package modeling;
import exceptions.*;

import java.io.Serializable;
import java.util.LinkedList;

 /**
 * La clase Cinema representa un cine que gestiona horarios, taquillas y salas de proyección.
 * Proporciona métodos para gestionar horarios, comprar entradas, listar asientos y más.
 * @author Lucas
 * @since 06/2023
 * @version 1.0.3
 */
public class Cinema implements Serializable {
    private final Showtimes showtimes;
    private final CinemaTicketOffice cinemaTicketOffice;
    private final OnlineTicketOffice onlineTicketOffice;
    private final LinkedList<ScreeningRoom> screeningRooms;

    /**
     * Construye un nuevo objeto Cinema con valores predeterminados.
     */
    public Cinema() {
        showtimes = new Showtimes();
        cinemaTicketOffice = new CinemaTicketOffice();
        onlineTicketOffice = new OnlineTicketOffice();
        screeningRooms = createScreeningRooms();
    }

    /**
     * Obtiene el objeto Showtimes asociado al cine.
     *
     * @return El objeto Showtimes.
     */
    public Showtimes getShowtimes() {
        return showtimes;
    }

    /**
     * Obtiene la taquilla del cine.
     *
     * @return El objeto CinemaTicketOffice.
     */
    private CinemaTicketOffice getCinemaTicketOffice() {
        return cinemaTicketOffice;
    }

    /**
     * Obtiene la taquilla online del cine.
     *
     * @return El objeto OnlineTicketOffice.
     */
    private OnlineTicketOffice getOnlineTicketOffice() {
        return onlineTicketOffice;
    }

    /**
     * Obtiene una copia de la lista de salas de proyección asociadas al cine.
     *
     * @return Una LinkedList de objetos ScreeningRoom.
     */
    private LinkedList<ScreeningRoom> getScreeningRooms() {
        return new LinkedList<>(screeningRooms);
    }

    /**
     * Crea y devuelve una lista de salas de proyección predeterminadas.
     *
     * @return Una LinkedList de objetos ScreeningRoom.
     */
    private LinkedList<ScreeningRoom> createScreeningRooms() {
        LinkedList<ScreeningRoom> rooms = new LinkedList<>();
        rooms.add(ScreeningRoom.STANDARD);
        rooms.add(ScreeningRoom.DOLBY_ATMOS);
        rooms.add(ScreeningRoom.VIP);
        rooms.add(ScreeningRoom.IMAX);
        return rooms;
    }

    /**
     * Obtiene la sala de proyección en el índice especificado.
     *
     * @param index El índice de la sala de proyección.
     * @return El objeto ScreeningRoom.
     */
    public ScreeningRoom getScreeningRoomByIndex(int index) {
        return getScreeningRooms().get(index);
    }

    /**
     * Devuelve una representación en forma de cadena de todas las salas de proyección del cine.
     *
     * @return Una cadena que contiene los detalles de todas las salas de proyección.
     */
    public String showAllScreeningRooms() {
        StringBuilder builder = new StringBuilder();
        builder.append("╔═══════════════════════════╗\n");
        builder.append("║  ╔═════════════════════╗  ║\n");
        builder.append("║  ║   SALAS DE PROYECCIÓN ║  ║\n");
        builder.append("║  ╚═════════════════════╝  ║\n");
        builder.append("╚═══════════════════════════╝\n");

        for (int i = 0; i < getScreeningRooms().size(); i++) {
            ScreeningRoom screeningRoom = getScreeningRoomByIndex(i);
            builder.append(screeningRoom).append("\n");
        }

        return builder.toString();
    }

    /**
     * Establece los precios base y costos adicionales de las entradas en las taquillas del cine.
     *
     * @param price          El precio base de una entrada.
     * @param additionalCost El costo adicional de una entrada, en el caso de que la sala de proyección tenga asientos VIP.
     */
    public void setPrices(double price, double additionalCost) {
        getCinemaTicketOffice().setPrice(price);
        getOnlineTicketOffice().setPrice(price);
        getCinemaTicketOffice().setAdditionalCost(additionalCost);
        getOnlineTicketOffice().setAdditionalCost(additionalCost);
    }

    /**
     * Compra una entrada en línea para una película, hora, sala de proyección y asiento específicos.
     *
     * @param title         El título de la película.
     * @param time          La hora de la proyección.
     * @param screeningRoom La sala de proyección.
     * @param seat          El número del asiento.
     * @return Una cadena con el código para canjear la entrada comprada.
     * @throws NotAvailableForSaleException si la entrada no está disponible para la venta.
     */
    public String buyTicketOnline(String title, Time time, ScreeningRoom screeningRoom, String seat)
            throws NotAvailableForSaleException {
        return getOnlineTicketOffice().sellTicket(title, time, screeningRoom, seat, getCinemaTicketOffice());
    }

    /**
     * Agrega una nueva hora para una película en el índice especificado.
     *
     * @param index  El índice de la película.
     * @param hour   La hora de la nueva proyección.
     * @param minute El minuto de la nueva proyección.
     * @throws NotFoundException      si la película no se encuentra.
     * @throws AlreadyExistsException si la hora ya existe para la película.
     * @throws InvalidIndexException  si el índice es inválido.
     */
    public void addTime(int index, int hour, int minute)
            throws NotFoundException, AlreadyExistsException, InvalidIndexException {
        getShowtimes().addTime(index, hour, minute, getCinemaTicketOffice());
    }

    /**
     * Compra una entrada en el cine para una película, hora, sala de proyección y asiento específicos.
     *
     * @param title         El título de la película.
     * @param time          La hora de la proyección.
     * @param screeningRoom La sala de proyección.
     * @param seat          El número del asiento.
     * @return El objeto MovieTicket que representa la entrada comprada.
     * @throws NotAvailableForSaleException si la entrada no está disponible para la venta.
     */
    public MovieTicket buyTicketAtCinema(String title, Time time, ScreeningRoom screeningRoom, String seat)
            throws NotAvailableForSaleException {
        return getCinemaTicketOffice().sellTicket(title, time, screeningRoom, seat, cinemaTicketOffice.getPrice());
    }

    /**
     * Devuelve una representación en forma de cadena de los asientos disponibles para una película,
     * hora y sala de proyección específicos.
     *
     * @param title         El título de la película.
     * @param time          La hora de la proyección.
     * @param screeningRoom La sala de proyección.
     * @return Una cadena que contiene los detalles de los asientos.
     */
    public String listSeats(String title, Time time, ScreeningRoom screeningRoom) {
        return getCinemaTicketOffice().listSeats(title, time, screeningRoom);
    }

    /**
     * Elimina una película de la cartelera en el índice especificado.
     *
     * @param index El índice de la película.
     * @throws NotFoundException     si la película no se encuentra.
     * @throws InvalidIndexException si el índice es inválido.
     */
    public void removeMovie(int index) throws NotFoundException, InvalidIndexException {
        getShowtimes().removeMovie(index, getCinemaTicketOffice());
    }

    /**
     * Elimina una hora de proyección de una película en el índice especificado.
     *
     * @param index El índice de la película.
     * @param time  La hora a eliminar.
     * @throws NotFoundException     si la película o la hora no se encuentran.
     * @throws InvalidIndexException si el índice es inválido.
     */
    public void removeTime(int index, Time time) throws NotFoundException, InvalidIndexException {
        getShowtimes().removeTime(index, time, getCinemaTicketOffice());
    }

    /**
     * Elimina una sala de proyección de una película en el índice especificado.
     *
     * @param index El índice de la sala de proyección.
     * @throws NotFoundException     si la sala de proyección no se encuentra.
     * @throws InvalidIndexException si el índice es inválido.
     */
    public void removeScreeningRoom(int index) throws NotFoundException, InvalidIndexException {
        getShowtimes().removeScreeningRoom(index, getCinemaTicketOffice());
    }

    /**
     * Obtiene una representación en forma de cadena de todos los títulos de las películas en cartelera.
     *
     * @return Una cadena que contiene todos los títulos de las películas.
     * @throws EmptyShowtimes si la cartelera está vacía.
     */
    public String getTitles() throws EmptyShowtimes {
        return getShowtimes().titlesToString();
    }

    /**
     * Verifica si los precios de las entradas han sido establecidos tanto en la taquilla del cine
     * como en la taquilla en línea.
     *
     * @return true si los precios están establecidos, false en caso contrario.
     */
    public boolean arePricesSet() {
        return getCinemaTicketOffice().arePricesSet() && getOnlineTicketOffice().arePricesSet();
    }

    /**
     * Canjea una entrada con el código especificado.
     *
     * @param code El código de la entrada a canjear.
     * @return El objeto MovieTicket que representa la entrada canjeada.
     * @throws NotFoundException si el código no se encuentra.
     */
    public MovieTicket redeemTicket(String code) throws NotFoundException {
        return getCinemaTicketOffice().exchangeTicket(code);
    }

    /**
     * Regenera el inventario de entradas del cine.
     */
    public void regenerateTicketStock() {
        try {
            getCinemaTicketOffice().regenerateTicketStock(getShowtimes());
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidIndexException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Verifica si el cine tiene stock disponible para una película, hora y sala de proyección específicos.
     *
     * @param title         El título de la película.
     * @param time          La hora de la proyección.
     * @param screeningRoom La sala de proyección.
     * @return true si hay stock disponible, false en caso contrario.
     */
    public boolean hasStock(String title, Time time, ScreeningRoom screeningRoom) {
        return getCinemaTicketOffice().hasStock(title, time, screeningRoom);
    }
}
