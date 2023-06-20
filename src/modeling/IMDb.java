package modeling;

import exceptions.EmptyTitleException;
import exceptions.NotFoundException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase que proporciona métodos para buscar películas en la API de IMDb.
 * @author Matias, Lucas
 * @version 1.0.3
 * @since 06/2023
 */
public class IMDb {
    /**
     * Genera la URL requerida por la API de IMDb a partir del título de la película.
     * Si se le pasa un título vacío, arroja una excepción EmptyTitleException.
     *
     * @param title el título de la película
     * @return la URL generada para la película
     * @throws EmptyTitleException si el título está vacío
     */
    private static String generateTitleURL(String title) throws EmptyTitleException {
        if (title.isEmpty()) {
            throw new EmptyTitleException("Error: Title cannot be empty.");
        }
        String[] titleArray = title.split(" ");
        StringBuilder url = new StringBuilder("http://www.omdbapi.com/?t=");
        for (int i = 0; i < titleArray.length; i++) {
            url.append(titleArray[i]);
            if (i < (titleArray.length - 1)) {
                url.append("+");
            }
        }
        url.append("&apikey=8738b1fe");
        return url.toString();
    }

    /**
     * Obtiene el JSON correspondiente a la película utilizando la URL generada por generateTitleURL.
     * Si no se encuentra la película, arroja una excepción NotFoundException.
     *
     * @param title el título de la película
     * @return el JSON correspondiente a la película
     * @throws NotFoundException   si no se encuentra la película
     * @throws EmptyTitleException si el título está vacío
     * @throws JSONException       si ocurre un error al procesar el JSON
     */
    private static String fetchJsonFromUrl(String title) throws NotFoundException, EmptyTitleException, JSONException {
        String url = generateTitleURL(title);
        String json = API.getInfo(url);
        JSONObject jsonObject = new JSONObject(json);
        boolean response = jsonObject.getBoolean("Response");
        if (!response) {
            throw new NotFoundException("Error: Movie not found - " + title);
        }
        return json;
    }

    /**
     * Busca una película en la API de IMDb utilizando el título y devuelve un objeto Movie con la información de la película.
     * Si no se encuentra la película, arroja una excepción NotFoundException.
     *
     * @param title el título de la película
     * @return un objeto Movie con la información de la película
     * @throws NotFoundException   si no se encuentra la película
     * @throws EmptyTitleException si el título está vacío
     */
    public static Movie searchMovie(String title) throws NotFoundException, EmptyTitleException {
        Movie movie = new Movie();
        try {
            String json = fetchJsonFromUrl(title);
            JSONObject jsonObject = new JSONObject(json);
            movie.setTitle(jsonObject.getString("Title"));
            movie.setYear(jsonObject.getString("Year"));
            movie.setRated(jsonObject.getString("Rated"));
            movie.setReleased(jsonObject.getString("Released"));
            movie.setRuntime(jsonObject.getString("Runtime"));
            movie.setGenre(jsonObject.getString("Genre"));
            movie.setDirector(jsonObject.getString("Director"));
            movie.setWriter(jsonObject.getString("Writer"));
            movie.setActors(jsonObject.getString("Actors"));
            movie.setPlot(jsonObject.getString("Plot"));
            movie.setLanguage(jsonObject.getString("Language"));
            return movie;
        } catch (JSONException exception) {
            System.out.println("No internet connection, please try again later.");
            return null;
        }
    }
}
