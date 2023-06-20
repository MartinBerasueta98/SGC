package exceptions;

/**
 * Excepción que se lanza cuando una entrada o una pelicula no están disponibles para la venta.
 */
public class NotAvailableForSaleException extends Exception {

    /**
     * Crea una nueva instancia de NotAvailableForSaleException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public NotAvailableForSaleException(String message) {
        super(message);
    }

    /**
     * Obtiene el mensaje de la excepción.
     *
     * @return el mensaje de la excepción
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

