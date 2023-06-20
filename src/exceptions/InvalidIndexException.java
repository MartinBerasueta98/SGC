package exceptions;

/**
 * Excepción que se lanza cuando se proporciona un índice inválido.
 */
public class InvalidIndexException extends Exception {

    /**
     * Crea una nueva instancia de InvalidIndexException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public InvalidIndexException(String message) {
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

