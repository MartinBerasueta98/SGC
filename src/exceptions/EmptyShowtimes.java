package exceptions;

/**
 * Excepción que se lanza cuando la cartelera está vacía.
 */
public class EmptyShowtimes extends Exception {

    /**
     * Crea una nueva instancia de EmptyShowtimes con un mensaje específico.
     *
     * @param message el mensaje que describe la excepción
     */
    public EmptyShowtimes(String message) {
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

