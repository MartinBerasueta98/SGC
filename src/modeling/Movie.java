package modeling;

import java.io.Serializable;

/**
 * La clase Movie representa una película con sus detalles.
 * @author Martin Berasueta.
 * @since 06/2023
 * @version 1.0.3
 */
public class Movie implements Serializable {
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;

    /**
     * Crea una instancia de la clase Movie sin valores iniciales.
     */
    public Movie() {
    }

    /**
     * Obtiene el título de la película.
     *
     * @return el título de la película
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título de la película.
     *
     * @param title el título de la película
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene el año de lanzamiento de la película.
     *
     * @return el año de lanzamiento de la película
     */
    public String getYear() {
        return year;
    }

    /**
     * Establece el año de lanzamiento de la película.
     *
     * @param year el año de lanzamiento de la película
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Obtiene la clasificación de la película.
     *
     * @return la clasificación de la película
     */
    public String getRated() {
        return rated;
    }

    /**
     * Establece la clasificación de la película.
     *
     * @param rated la clasificación de la película
     */
    public void setRated(String rated) {
        this.rated = rated;
    }

    /**
     * Obtiene la fecha de lanzamiento de la película.
     *
     * @return la fecha de lanzamiento de la película
     */
    public String getReleased() {
        return released;
    }

    /**
     * Establece la fecha de lanzamiento de la película.
     *
     * @param released la fecha de lanzamiento de la película
     */
    public void setReleased(String released) {
        this.released = released;
    }

    /**
     * Obtiene la duración de la película.
     *
     * @return la duración de la película
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * Establece la duración de la película.
     *
     * @param runtime la duración de la película
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * Obtiene el género de la película.
     *
     * @return el género de la película
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Establece el género de la película.
     *
     * @param genre el género de la película
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Obtiene el director de la película.
     *
     * @return el director de la película
     */
    public String getDirector() {
        return director;
    }

    /**
     * Establece el director de la película.
     *
     * @param director el director de la película
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Obtiene el escritor de la película.
     *
     * @return el escritor de la película
     */
    public String getWriter() {
        return writer;
    }

    /**
     * Establece el escritor de la película.
     *
     * @param writer el escritor de la película
     */
    public void setWriter(String writer) {
        this.writer = writer;
    }

    /**
     * Obtiene los actores de la película.
     *
     * @return los actores de la película
     */
    public String getActors() {
        return actors;
    }

    /**
     * Establece los actores de la película.
     *
     * @param actors los actores de la película
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * Obtiene la trama de la película.
     *
     * @return la trama de la película
     */
    public String getPlot() {
        return plot;
    }

    /**
     * Establece la trama de la película.
     *
     * @param plot la trama de la película
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * Obtiene el idioma de la película.
     *
     * @return el idioma de la película
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Establece el idioma de la película.
     *
     * @param language el idioma de la película
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Devuelve una representación en forma de cadena de los detalles de la película.
     *
     * @return una cadena con los detalles de la película
     */
    @Override
    public String toString() {
        return "╔════════════════════════════════════════╗\n" +
                "║               MOVIE DETAILS            ║\n" +
                "╚════════════════════════════════════════╝\n" +
                "Title: " + getTitle() + "\n" +
                "Year: " + getYear() + "\n" +
                "Rated: " + getRated() + "\n" +
                "Released: " + getReleased() + "\n" +
                "Runtime: " + getRuntime() + "\n" +
                "Genre: " + getGenre() + "\n" +
                "Director: " + getDirector() + "\n" +
                "Writer: " + getWriter() + "\n" +
                "Actors: " + getActors() + "\n" +
                "Plot: " + getPlot() + "\n" +
                "Language: " + getLanguage() + "\n";
    }
}
