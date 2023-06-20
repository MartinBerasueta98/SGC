package modeling;

import java.io.Serializable;

/**
 * La clase Time representa una hora específica con horas y minutos.
 * Proporciona métodos para convertir la hora en una cadena formateada,
 * calcular la hora en minutos y comparar dos objetos Time.
 * @author Matias
 * @since 06/2023
 * @version 1.0.3
 */
public class Time implements Comparable<Time>, Serializable {
    private final int hour;
    private final int minute;

    /**
     * Construye un objeto Time con la hora y los minutos especificados.
     *
     * @param hour   el valor de la hora (0-23)
     * @param minute el valor de los minutos (0-59)
     */
    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Devuelve una representación en cadena del objeto Time en el formato "HH:MMam/pm".
     *
     * @return una cadena formateada que representa la hora
     */
    @Override
    public String toString() {
        int adjustedHour = hour % 12;
        String h = String.format("%02d", adjustedHour);
        String m = String.format("%02d", minute);
        String period = (hour >= 12) ? "pm" : "am";
        return h + ":" + m + period;
    }

    /**
     * Convierte la hora a minutos.
     *
     * @return la hora en minutos
     */
    private int toMinutes() {
        return hour * 60 + minute;
    }

    /**
     * Compara este objeto Time con otro objeto Time.
     *
     * @param otherTime el objeto Time a comparar
     * @return un entero negativo, cero o un entero positivo si esta hora es menor que,
     *         igual a o mayor que la otra hora, respectivamente
     * @throws NullPointerException si otherTime es nulo
     */
    @Override
    public int compareTo(Time otherTime) {
        if (otherTime == null) {
            throw new NullPointerException("No se puede comparar con un objeto nulo.");
        }
        return Integer.compare(toMinutes(), otherTime.toMinutes());
    }
}
