package modeling;

import java.io.Serializable;

/**
 * La clase MovieTicket representa un boleto de cine para una película específica.
 * @author Martin Berasueta.
 * @since 06/2023
 * @version 1.0.3
 */
public class MovieTicket implements Serializable {
    private final String title;
    private final Time time;
    private final ScreeningRoom screeningRoom;
    private final String seat;
    private double price;

    /**
     * Crea una instancia de la clase MovieTicket con los detalles proporcionados.
     *
     * @param title          el título de la película
     * @param time           la hora de inicio de la película
     * @param screeningRoom  la sala de proyección
     * @param seat           el asiento del boleto
     * @param price          el precio del boleto
     */
    public MovieTicket(String title, Time time, ScreeningRoom screeningRoom, String seat, double price) {
        this.title = title;
        this.time = time;
        this.screeningRoom = screeningRoom;
        this.seat = seat;
        this.price = price;
    }

    /**
     * Obtiene el título de la película del boleto.
     *
     * @return el título de la película
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtiene la hora de inicio de la película del boleto.
     *
     * @return la hora de inicio de la película
     */
    public Time getTime() {
        return time;
    }

    /**
     * Obtiene la sala de proyección del boleto.
     *
     * @return la sala de proyección
     */
    public ScreeningRoom getScreeningRoom() {
        return screeningRoom;
    }

    /**
     * Obtiene el asiento del boleto.
     *
     * @return el asiento
     */
    public String getSeat() {
        return seat;
    }

    /**
     * Obtiene el precio del boleto.
     *
     * @return el precio del boleto
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece el precio del boleto.
     *
     * @param price el precio del boleto
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Devuelve una representación en forma de cadena del boleto de cine.
     *
     * @return una cadena con los detalles del boleto de cine
     */
    @Override
    public String toString() {
        return "╔═══════════════════════╗\n" +
                "║      MOVIE TICKET     ║\n" +
                "╚═══════════════════════╝\n" +
                "Title: " + getTitle() + "\n" +
                "Start Time: " + getTime().toString() + "\n" +
                "Screening Room: " + getScreeningRoom().getId() + "\n" +
                "Seat: " + getSeat() + "\n" +
                "Price: " + priceToString();
    }

    /**
     * Convierte el precio del boleto a una representación en forma de cadena.
     *
     * @return una cadena que representa el precio del boleto
     */
    private String priceToString() {
        return String.format("$%.2f", getPrice());
    }
}