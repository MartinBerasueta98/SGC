package exceptions;

/**
 * Excepción que se lanza cuando se intenta agregar un objeto ya existente.
 */
public class AlreadyExistsException extends Exception {

    /**
     * Crea una nueva instancia de AlreadyExistsException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public AlreadyExistsException(String message) {
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
