package modeling;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Clase que proporciona métodos para obtener información a través de una API.
 */
public class API {

    /**
     * Obtiene la información de una API a través de una solicitud GET.
     *
     * @param link el enlace de la API
     * @return la información obtenida
     */
    public static String getInfo(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Error code: " + responseCode);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                return stringBuilder.toString();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return "";
    }
}