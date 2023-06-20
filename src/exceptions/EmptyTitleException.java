package exceptions;

/**
 * Excepción que se lanza cuando se intenta utilizar un título vacío.
 */
public class EmptyTitleException extends Exception {

    /**
     * Crea una nueva instancia de EmptyTitleException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public EmptyTitleException(String message) {
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
