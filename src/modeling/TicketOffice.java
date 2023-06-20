package modeling;

import interfaces.IAdditionalCost;

import java.io.Serializable;

/**
 * La clase abstracta TicketOffice representa una taquilla de venta de entradas.
 * Implementa la interfaz IAdditionalCost para calcular el costo adicional de la entrada.
 * @author Lucas
 * @since 06/2023
 * @version 1.0.3
 */
public abstract class TicketOffice implements IAdditionalCost, Serializable {
    private double price; // Precio base de la entrada
    private double additionalCost; // Costo adicional de la entrada

    /**
     * Crea una instancia de TicketOffice con un precio y costo adicional inicial de 0.
     */
    public TicketOffice() {
        price = 0;
        additionalCost = 0;
    }

    /**
     * Obtiene el precio base de la entrada.
     *
     * @return El precio base de la entrada.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Obtiene el costo adicional de la entrada.
     *
     * @return El costo adicional de la entrada.
     */
    public double getAdditionalCost() {
        return additionalCost;
    }

    /**
     * Establece el precio base de la entrada.
     *
     * @param price El precio base de la entrada.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Establece el costo adicional de la entrada.
     *
     * @param additionalCost El costo adicional de la entrada.
     */
    public void setAdditionalCost(double additionalCost) {
        this.additionalCost = additionalCost;
    }

    /**
     * Calcula el costo total de la entrada incluyendo el precio base y el costo adicional.
     *
     * @return El costo total de la entrada.
     */
    @Override
    public double additionalCost() {
        return getPrice() + getAdditionalCost();
    }

    /**
     * Genera una clave única para un ticket de entrada utilizando el título de la película,
     * el horario y el asiento.
     *
     * @param title El título de la película.
     * @param time  El horario de la función.
     * @param seat  El número de asiento.
     * @return La clave única del ticket de entrada.
     */
    public static String generateTicketKey(String title, Time time, String seat) {
        return title + "-" + time + "-" + seat;
    }

    /**
     * Verifica si los precios de la entrada están establecidos.
     *
     * @return true si los precios están establecidos, false de lo contrario.
     */
    public boolean arePricesSet() {
        return price != 0 && additionalCost != 0;
    }
}
