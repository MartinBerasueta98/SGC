package exceptions;

/**
 * Excepción que se lanza cuando no se encuentra un elemento.
 */
public class NotFoundException extends Exception {

    /**
     * Crea una nueva instancia de NotFoundException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public NotFoundException(String message) {
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
