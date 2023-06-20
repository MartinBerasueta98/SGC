package modeling;

import exceptions.AlreadyExistsException;
import exceptions.NotAvailableForSaleException;
import interfaces.ITicketManagement;

import java.io.Serializable;

/**
 * Representa una taquilla de boletos en línea que vende boletos en colaboración con una taquilla de boletos de cine.
 * Extiende la clase `TicketOffice` e implementa la interfaz `ITicketManagement` con claves de tipo `String` y un valor de tipo `CinemaTicketOffice`.
 * @author Lucas
 * @since 06/2023
 * @version 1.0.3
 */
public class OnlineTicketOffice extends TicketOffice implements ITicketManagement<String, CinemaTicketOffice>, Serializable {

    /**
     * Crea una nueva instancia de `OnlineTicketOffice`.
     * Llama al constructor de la clase padre `TicketOffice`.
     */
    public OnlineTicketOffice() {
        super();
    }

    /**
     * Vende un boleto para una película en un horario y sala de proyección específicos, en colaboración con una taquilla de boletos de cine.
     * En lugar de devolver el boleto, devuelve el código de reserva para luego canjear la entrada en el cine.
     *
     * @param title              El título de la película.
     * @param time               El horario de la función.
     * @param screeningRoom      La sala de proyección.
     * @param seat               El asiento deseado.
     * @param cinemaTicketOffice La taquilla de boletos de cine asociada.
     * @return El código de reserva del boleto vendido.
     * @throws NotAvailableForSaleException Si el asiento no está disponible para la venta.
     */
    @Override
    public String sellTicket(String title, Time time, ScreeningRoom screeningRoom, String seat, CinemaTicketOffice cinemaTicketOffice) throws NotAvailableForSaleException {
        if (cinemaTicketOffice.isTicketAvailable(title, time, seat)) {
            double price = getPrice();
            if (screeningRoom.hasVipSeats()) {
                price = additionalCost();
            }
            MovieTicket ticket = cinemaTicketOffice.removeTicketFromStock(title, time, seat);
            ticket.setPrice(price);

            String code = RandomCodeGenerator.generateRandomCode();
            try {
                cinemaTicketOffice.addReservedTicket(code, ticket);
            } catch (AlreadyExistsException exception) {
                ticket.setPrice(0);
                cinemaTicketOffice.addTicketToStock(ticket);
                sellTicket(title, time, screeningRoom, seat, cinemaTicketOffice);
            }
            return code;
        }
        else {
            throw new NotAvailableForSaleException("This seat is not available.");
        }
    }
}
