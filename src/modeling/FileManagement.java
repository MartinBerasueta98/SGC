package modeling;

import java.io.*;

/**
 * Clase que se encarga de la gestión de archivos para leer y escribir objetos Cinema.
 * @author Matias
 * @version 1.0.3
 * @since 06/2023
 */
public class FileManagement {
    private static final String FILE_PATH = "cinema_data.txt";

    /**
     * Lee el archivo y devuelve un objeto Cinema.
     * Si el archivo no existe, crea un objeto Cinema vacío y lo guarda en el archivo.
     *
     * @return el objeto Cinema leído o el objeto Cinema vacío si el archivo no existe
     */
    public static Cinema readFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Si el archivo no existe, creamos un objeto Cinema vacío y lo guardamos
            Cinema cinema = new Cinema();
            writeFile(cinema);
            return cinema;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            // Si el archivo existe, lo leemos y devolvemos el objeto Cinema almacenado
            return (Cinema) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Escribe el objeto Cinema en el archivo.
     *
     * @param cinema el objeto Cinema a escribir
     */
    public static void writeFile(Cinema cinema) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            // Guardamos el objeto Cinema en el archivo
            objectOutputStream.writeObject(cinema);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

