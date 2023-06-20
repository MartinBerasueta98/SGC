package modeling;

import java.io.Serializable;
import java.util.Random;

/**
 * La clase RandomCodeGenerator se utiliza para generar códigos aleatorios.
 *  * CHARACTERS: cadena que contiene todos los caracteres posibles.
 *  * CODE_LENGTH: tamaño del código.
 *  * @author Martin Berasueta. Ayudante Chat GPT.
 */
public class RandomCodeGenerator implements Serializable {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    /**
     * Genera un código aleatorio utilizando caracteres alfanuméricos.
     *
     * @return el código aleatorio generado
     */
    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
