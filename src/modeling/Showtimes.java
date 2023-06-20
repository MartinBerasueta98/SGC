package modeling;

import exceptions.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;
/**
 * La clase Showtimes representa una cartelera de películas en un cine.
 * Permite agregar y eliminar películas, asignar y eliminar salas de proyección,
 * así como agregar y eliminar horarios de proyección para cada película.
 * @author Lucas
 * @since 06/2023
 * @version 1.0.3
 */
public class Showtimes implements Serializable {
    private final HashMap<String, Movie> movies;
    private final HashMap<String, TreeSet<Time>> startTimes;
    private final HashMap<String, ScreeningRoom> screeningRooms;
    private final LinkedList<String> titles;
    /**
     * Crea una instancia de Showtimes.
     * Inicializa los mapas de películas, horarios de inicio y salas de proyección,
     * así como la lista de títulos de películas.
     */
    public Showtimes() {
        movies = new HashMap<>();
        startTimes = new HashMap<>();
        screeningRooms = new HashMap<>();
        titles = new LinkedList<>();
    }

    /**
     * Obtiene el mapa de películas.
     *
     * @return El mapa de películas.
     */
    private HashMap<String, Movie> getMovies() {
        return movies;
    }

    /**
     * Obtiene el mapa de horarios de inicio.
     *
     * @return El mapa de horarios de inicio.
     */
    private HashMap<String, TreeSet<Time>> getStartTimes() {
        return startTimes;
    }

    /**
     * Obtiene el mapa de salas de proyección.
     *
     * @return El mapa de salas de proyección.
     */
    private HashMap<String, ScreeningRoom> getScreeningRooms() {
        return screeningRooms;
    }

    /**
     * Obtiene la lista de títulos de películas.
     *
     * @return La lista de títulos de películas.
     */
    private LinkedList<String> getTitles() {
        return titles;
    }

    /**
     * Agrega una película al sistema de horarios.
     *
     * @param title El título de la película a agregar.
     * @throws AlreadyExistsException     Si la película ya existe en el sistema.
     * @throws NotFoundException         Si no se encuentra la película en la base de datos externa.
     * @throws EmptyTitleException        Si el título de la película está vacío.
     * @throws MovieSearchFailedException Si falla la búsqueda de la película en la base de datos externa.
     */
    public void addMovie(String title) throws AlreadyExistsException, NotFoundException, EmptyTitleException, MovieSearchFailedException {
        Movie movie = IMDb.searchMovie(title);
        if (movie != null) {
            title = movie.getTitle();
            // Verificar si la película ya existe en el mapa de películas
            if (!getMovies().containsKey(title)) {
                getMovies().put(title, movie);
                getStartTimes().put(title, new TreeSet<>());
                getTitles().addFirst(title);
            } else {
                throw new AlreadyExistsException("Error: Esta película ya existe: " + title);
            }
        } else {
            throw new MovieSearchFailedException("Error: No se pudo agregar la película.");
        }
    }

    /**
     * Agrega un horario de proyección para una película en particular.
     *
     * @param index               El índice de la película.
     * @param hour                La hora del horario de proyección.
     * @param minute              El minuto del horario de proyección.
     * @param cinemaTicketOffice  La taquilla del cine.
     * @throws NotFoundException     Si no se encuentra la película en el sistema.
     * @throws AlreadyExistsException Si el horario de proyección ya existe para la película.
     * @throws InvalidIndexException  Si el índice de la película es inválido.
     */
    public void addTime(int index, int hour, int minute, CinemaTicketOffice cinemaTicketOffice) throws NotFoundException, AlreadyExistsException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (getStartTimes().containsKey(title)) {
            Time time = new Time(hour, minute);
            if (!getStartTimes().get(title).add(time)) {
                throw new AlreadyExistsException("Error: Este horario de inicio ya existe para esta película: " + title + " - " + time);
            }
            cinemaTicketOffice.updateTicketStock(title, time, getScreeningRoomByTitle(title));
        } else {
            throw new NotFoundException("Error: Película no encontrada en el índice: " + index);
        }
    }

    /**
     * Agrega una sala de proyección a una película en particular.
     *
     * @param index          El índice de la película.
     * @param screeningRoom  La sala de proyección a agregar.
     * @throws NotFoundException    Si no se encuentra la película en el sistema.
     * @throws InvalidIndexException Si el índice de la película es inválido.
     */
    public void addScreeningRoom(int index, ScreeningRoom screeningRoom) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (getMovies().containsKey(title)) {
            getScreeningRooms().put(title, screeningRoom);
        } else {
            throw new NotFoundException("Error: Película no encontrada en el índice: " + index);
        }
    }

    /**
     * Verifica si una película tiene asignada una sala de proyección por su título.
     *
     * @param title El título de la película.
     * @return `true` si la película tiene una sala de proyección asignada, `false` en caso contrario.
     */
    public boolean hasScreeningRoomByTitle(String title) {
        return getScreeningRooms().containsKey(title);
    }

    /**
     * Verifica si una película tiene asignada una sala de proyección por su índice.
     *
     * @param index El índice de la película.
     * @return `true` si la película tiene una sala de proyección asignada, `false` en caso contrario.
     * @throws InvalidIndexException Si el índice de la película es inválido.
     */
    public boolean hasScreeningRoomByIndex(int index) throws InvalidIndexException {
        String title = getTitleByIndex(index);
        return hasScreeningRoomByTitle(title);
    }

    /**
     * Obtiene los horarios de inicio de proyección de una película por su título.
     *
     * @param title El título de la película.
     * @return Un conjunto ordenado de los horarios de inicio de proyección de la película.
     * @throws NotFoundException Si no se encuentra la película en el sistema.
     */
    public TreeSet<Time> getStartTimesByTitle(String title) throws NotFoundException {
        TreeSet<Time> startTimes = getStartTimes().get(title);
        if (startTimes != null) {
            return startTimes;
        } else {
            throw new NotFoundException(title + " no tiene horarios de inicio asignados.");
        }
    }

    /**
     * Obtiene una película por su índice.
     *
     * @param index El índice de la película.
     * @return La película correspondiente al índice.
     * @throws NotFoundException    Si no se encuentra la película en el sistema.
     * @throws InvalidIndexException Si el índice de la película es inválido.
     */
    public Movie getMovieByIndex(int index) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (getMovies().containsKey(title)) {
            return getMovies().get(title);
        } else {
            throw new NotFoundException("Error: Película no encontrada en el índice: " + index);
        }
    }

    /**
     * Obtiene la sala de proyección asignada a una película por su título.
     *
     * @param title El título de la película.
     * @return La sala de proyección asignada a la película.
     * @throws NotFoundException Si no se encuentra la película en el sistema.
     */
    public ScreeningRoom getScreeningRoomByTitle(String title) throws NotFoundException {
        if (getScreeningRooms().containsKey(title)) {
            return getScreeningRooms().get(title);
        } else {
            throw new NotFoundException(title + " no tiene una sala asignada.");
        }
    }

    /**
     * Obtiene la cantidad de títulos de películas disponibles.
     *
     * @return La cantidad de títulos de películas disponibles.
     */
    public int getTitlesSize() {
        return getTitles().size();
    }

    /**
     * Obtiene el título de una película por su índice.
     *
     * @param index El índice de la película.
     * @return El título de la película correspondiente al índice.
     * @throws InvalidIndexException Si el índice de la película es inválido.
     */
    public String getTitleByIndex(int index) throws InvalidIndexException {
        int i = index - 1;
        if (i < 0 || i >= getTitles().size()) {
            throw new InvalidIndexException("Error: Este índice de película no existe");
        } else {
            return getTitles().get(i);
        }
    }

    /**
     * Convierte los títulos de las películas a una representación de cadena de caracteres.
     *
     * @return Una cadena de caracteres con los títulos de las películas disponibles.
     * @throws EmptyShowtimes Si no hay películas disponibles.
     */
    public String titlesToString() throws EmptyShowtimes {
        if (!getTitles().isEmpty()) {
            int maxTitleLength = getMaxTitleLength();

            StringBuilder builder = new StringBuilder();
            builder.append("╔════╦").append("═".repeat(maxTitleLength + 2)).append("╗\n");
            builder.append("║ ID ║ ").append(String.format("%-" + maxTitleLength + "s", "TÍTULO")).append(" ║\n");
            builder.append("╠════╬").append("═".repeat(maxTitleLength + 2)).append("╣\n");

            for (int i = 0; i < titles.size(); i++) {
                int index = i + 1;
                String title = titles.get(i);
                builder.append("║ ").append(String.format("%2d", index)).append(" ║ ").append(String.format("%-" + maxTitleLength + "s", title)).append(" ║\n");
            }

            builder.append("╚════╩").append("═".repeat(maxTitleLength + 2)).append("╝");

            return builder.toString();
        } else {
            throw new EmptyShowtimes("No hay películas disponibles.");
        }
    }

    /**
     * Obtiene la longitud máxima de los títulos de las películas.
     *
     * @return La longitud máxima de los títulos de las películas.
     */
    private int getMaxTitleLength() {
        int maxTitleLength = 0;
        for (String title : getTitles()) {
            int length = title.length();
            if (length > maxTitleLength) {
                maxTitleLength = length;
            }
        }
        return maxTitleLength;
    }


    /**
     * Devuelve una representación en forma de cadena de texto del objeto actual.
     * La representación incluye los horarios de las películas y la información de la sala de proyección asignada.
     * Si no hay películas disponibles, se devuelve "Coming soon...".
     *
     * @return Representación en forma de cadena de texto del objeto actual.
     */
    @Override
    public String toString() {
        if (!getTitles().isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("╔═══════════════════════╗\n");
            builder.append("║      SHOWTIMES        ║\n");
            builder.append("╚═══════════════════════╝\n");

            for (String title : getMovies().keySet()) {
                builder.append("\nTitle: ").append(title).append("\n");
                TreeSet<Time> times = getStartTimes().get(title);

                if (times.isEmpty()) {
                    builder.append("No showtimes available.\n");
                } else {
                    builder.append("Showtimes:\n");
                    for (Time time : times) {
                        builder.append("   • ").append(time).append("\n");
                    }
                }

                if (hasScreeningRoomByTitle(title)) {
                    ScreeningRoom screeningRoom = getScreeningRooms().get(title);
                    builder.append("Screen: ").append(screeningRoom.getId()).append("\n");
                } else {
                    builder.append("Screening Room: Not assigned\n");
                }

                Movie movie = getMovies().get(title);
                builder.append("Runtime: ").append(movie.getRuntime()).append("\n");
            }
            return builder.toString();
        } else {
            return "Coming soon...";
        }
    }

    /**
     * Devuelve los horarios de proyección de una película específica.
     *
     * @param title Título de la película.
     * @return Cadena de texto con los horarios de proyección de la película.
     * @throws NotFoundException Si no se encuentra la película.
     */
    public String getMovieShowtimes(String title) throws NotFoundException {
        if (startTimes.containsKey(title)) {
            TreeSet<Time> times = startTimes.get(title);
            Time[] timesArray = times.toArray(new Time[0]);

            StringBuilder builder = new StringBuilder();
            builder.append("Showtimes for ").append(title).append(":\n");

            for (int i = 0; i < timesArray.length; i++) {
                int index = i + 1;
                builder.append(index).append(". ").append(timesArray[i]).append("\n");
            }

            return builder.toString();
        } else {
            throw new NotFoundException("Error: Movie not found: " + title);
        }
    }

    /**
     * Devuelve el horario de proyección de una película específica basado en su índice.
     *
     * @param title Título de la película.
     * @param index Índice del horario de proyección.
     * @return Horario de proyección de la película.
     * @throws NotFoundException    Si no se encuentra la película.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public Time getTimeByIndex(String title, int index) throws NotFoundException, InvalidIndexException {
        index = index - 1;
        if (getStartTimes().containsKey(title)) {
            TreeSet<Time> times = getStartTimes().get(title);
            Time[] timesArray = times.toArray(new Time[0]);

            if (index >= 0 && index < timesArray.length) {
                return timesArray[index];
            } else {
                throw new InvalidIndexException("Error: This start time index doesn't exist");
            }
        } else {
            throw new NotFoundException("Error: Movie not found: " + title);
        }
    }

    /**
     * Verifica si una película está disponible para la venta basándose en su índice.
     *
     * @param index Índice de la película.
     * @return Verdadero si la película está disponible para la venta, falso en caso contrario.
     * @throws NotFoundException    Si no se encuentra la película.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public boolean isAvailableForSale(int index) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (hasScreeningRoomByTitle(title)) {
            TreeSet<Time> times = getStartTimes().get(title);
            return !times.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * Elimina un horario de proyección de una película específica.
     *
     * @param index               Índice de la película.
     * @param time                Horario de proyección a eliminar.
     * @param cinemaTicketOffice  Objeto CinemaTicketOffice asociado.
     * @throws NotFoundException    Si no se encuentra la película o el horario de proyección.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public void removeTime(int index, Time time, CinemaTicketOffice cinemaTicketOffice) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (getStartTimes().containsKey(title)) {
            TreeSet<Time> times = getStartTimes().get(title);
            if (times.remove(time)) {
                cinemaTicketOffice.removeTicketStock(title, time, getScreeningRoomByTitle(title));
            } else {
                throw new NotFoundException("Error: This start time doesn't exist for this movie: " + title + " - " + time);
            }
        } else {
            throw new NotFoundException("Error: Movie not found at index: " + index);
        }
    }

    /**
     * Verifica si una película tiene horarios de proyección basándose en su índice.
     *
     * @param index Índice de la película.
     * @return Verdadero si la película tiene horarios de proyección, falso en caso contrario.
     * @throws NotFoundException    Si no se encuentra la película.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public boolean hasStartTimesByIndex(int index) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (getStartTimes().containsKey(title)) {
            TreeSet<Time> times = getStartTimes().get(title);
            return !times.isEmpty();
        } else {
            throw new NotFoundException("Error: Movie not found at index: " + index);
        }
    }

    /**
     * Elimina la sala de proyección de una película específica.
     * También elimina todos los horarios de proyección asociados a la película.
     *
     * @param index               Índice de la película.
     * @param cinemaTicketOffice  Objeto CinemaTicketOffice asociado.
     * @throws NotFoundException    Si no se encuentra la película o la sala de proyección.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public void removeScreeningRoom(int index, CinemaTicketOffice cinemaTicketOffice) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);
        if (hasScreeningRoomByTitle(title)) {

            // Verificar y eliminar los horarios de la película
            if (hasStartTimesByIndex(index)) {
                TreeSet<Time> times = getStartTimes().get(title);
                Time[] timesArray = times.toArray(new Time[0]);

                // Eliminar todos los horarios de la película
                for (Time time : timesArray) {
                    try {
                        removeTime(index, time, cinemaTicketOffice);
                    } catch (NotFoundException e) {
                        // Manejar excepción si no se encuentra el horario (opcional)
                        System.out.println("Error: Failed to remove time - " + e.getMessage());
                    }
                }
            }
            // Eliminar la sala de proyección
            getScreeningRooms().remove(title);
        } else {
            throw new NotFoundException("Error: " + title + "doesn't have a screen assigned.");
        }
    }

    /**
     * Elimina una película específica.
     * También elimina la sala de proyección, los horarios de proyección y el título de la película.
     *
     * @param index               Índice de la película.
     * @param cinemaTicketOffice  Objeto CinemaTicketOffice asociado.
     * @throws NotFoundException    Si no se encuentra la película.
     * @throws InvalidIndexException Si el índice proporcionado es inválido.
     */
    public void removeMovie(int index, CinemaTicketOffice cinemaTicketOffice) throws NotFoundException, InvalidIndexException {
        String title = getTitleByIndex(index);

        // Eliminar la sala de proyección asociada a la película
        removeScreeningRoom(index, cinemaTicketOffice);

        // Eliminar los horarios de la película
        getStartTimes().remove(title);

        // Eliminar la película del mapa de películas
        getMovies().remove(title);

        // Eliminar el título de la película del LinkedList
        getTitles().remove(title);
    }
}