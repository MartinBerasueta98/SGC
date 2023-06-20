package exceptions;

/**
 * Excepción que se lanza cuando la búsqueda de una película falla.
 */
public class MovieSearchFailedException extends Exception {

    /**
     * Crea una nueva instancia de MovieSearchFailedException con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public MovieSearchFailedException(String message) {
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

