package interfaces;

import exceptions.NotAvailableForSaleException;
import modeling.ScreeningRoom;
import modeling.Time;

/**
 * Interfaz para la gestión de entradas.
 *
 * @param <T> el tipo de entrada que se va a vender
 * @param <E> el tipo de elemento de gestión de entradas
 */
public interface ITicketManagement<T, E> {

    /**
     * Vende una entrada para una película.
     *
     * @param title          el título de la película
     * @param time           el tiempo de la función
     * @param screeningRoom  la sala de proyección
     * @param seat           el asiento de la entrada
     * @param element        el elemento de gestión de entradas
     * @return la entrada vendida
     * @throws NotAvailableForSaleException si la entrada no está disponible para la venta
     */
    T sellTicket(String title, Time time, ScreeningRoom screeningRoom, String seat, E element) throws NotAvailableForSaleException;
}

