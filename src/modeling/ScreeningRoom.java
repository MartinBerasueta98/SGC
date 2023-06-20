package modeling;

import java.io.Serializable;

/**
 * La enumeración ScreeningRoom representa las salas de proyección disponibles.
 * Cada sala tiene un identificador, capacidad, número máximo de asientos por fila,
 * sistema de sonido, tipo de pantalla y la opción de tener asientos VIP.
 * @author Matias
 * @version 1.0.3
 * @since 06/2023
 */
public enum ScreeningRoom implements Serializable {
    STANDARD(1, 100, 10, "Standard Sound System", "2D", false),
    DOLBY_ATMOS(2, 80, 8, "Dolby Atmos", "3D", false),
    VIP(3, 40, 4, "Premium Sound System", "2D", true),
    IMAX(4, 60, 6, "IMAX Sound System", "3D", true);

    private final int id;
    private final int capacity;
    private final int maxSeatsPerRow;
    private final String soundSystem;
    private final String screenType;
    private final boolean hasVipSeats;

    /**
     * Construye un objeto ScreeningRoom con el identificador, capacidad, número máximo de asientos por fila,
     * sistema de sonido, tipo de pantalla y opción de asientos VIP especificados.
     *
     * @param id               el identificador de la sala de proyección
     * @param capacity         la capacidad de la sala de proyección
     * @param maxSeatsPerRow   el número máximo de asientos por fila
     * @param soundSystem      el sistema de sonido de la sala de proyección
     * @param screenType       el tipo de pantalla de la sala de proyección
     * @param hasVipSeats      indica si la sala de proyección tiene asientos VIP o no
     */
    ScreeningRoom(int id, int capacity, int maxSeatsPerRow, String soundSystem, String screenType, boolean hasVipSeats) {
        this.id = id;
        this.capacity = capacity;
        this.maxSeatsPerRow = maxSeatsPerRow;
        this.soundSystem = soundSystem;
        this.screenType = screenType;
        this.hasVipSeats = hasVipSeats;
    }

    /**
     * Devuelve el identificador de la sala de proyección.
     *
     * @return el identificador de la sala
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la capacidad de la sala de proyección.
     *
     * @return la capacidad de la sala
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Devuelve el número máximo de asientos por fila en la sala de proyección.
     *
     * @return el número máximo de asientos por fila
     */
    public int getMaxSeatsPerRow() {
        return maxSeatsPerRow;
    }

    /**
     * Devuelve el sistema de sonido de la sala de proyección.
     *
     * @return el sistema de sonido
     */
    public String getSoundSystem() {
        return soundSystem;
    }

    /**
     * Devuelve el tipo de pantalla de la sala de proyección.
     *
     * @return el tipo de pantalla
     */
    public String getScreenType() {
        return screenType;
    }

    /**
     * Indica si la sala de proyección tiene asientos VIP o no.
     *
     * @return true si la sala tiene asientos VIP, false en caso contrario
     */
    public boolean hasVipSeats() {
        return hasVipSeats;
    }

    /**
     * Devuelve una representación en cadena de la sala de proyección con su información.
     *
     * @return una cadena que representa la sala de proyección
     */
    @Override
    public String toString() {
        return "╔════════════════════╗\n" +
                "║      SCREEN " + getId() + "      ║\n" +
                "╚════════════════════╝\n" +
                "Capacity: " + getCapacity() + "\n" +
                "Sound System: " + getSoundSystem() + "\n" +
                "Screen Type: " + getScreenType() + "\n" +
                "VIP Seats: " + (hasVipSeats() ? "Yes" : "No");
    }
}
