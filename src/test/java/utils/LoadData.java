package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

    public static void main(String[] args) {
        String filePath = "./src/test/resources/features/test1.feature"; // Cambia esto a tu archivo
        String searchText = "@externaldata"; // Texto que deseas encontrar
//        String newContent = "      "+"|usertest1|supertest102*|usetest@yopmail.com|";

        try {
            addLineBelowText_v3(filePath, searchText);
            System.out.println("Línea añadida exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al añadir la línea: " + e.getMessage());
        }
    }

    public static void addLineBelowText(String filePath, String searchText) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        // Buscar el texto y añadir la nueva línea debajo
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(searchText)) {
                String[] command = lines.get(i).split("@");
//                lines.add(i + 1, getData(command[3])); // Añadir debajo de la línea encontrada
                ArrayList<String> data = ManagementExcel.rearExcel_v2(command[2], command[3]);
                for (int j = 0; j < data.size(); j++) {
                    lines.add(i+j+1, "      "+data.get(j));
                }
//                lines.add(i + 1, newContent); // Añadir debajo de la línea encontrada
//                break; // Salir después de encontrar la primera ocurrencia
            }
        }

        // Escribir el contenido de nuevo al archivo
        Files.write(path, lines);
    }

    public static void addLineBelowText_v2(String filePath, String searchText) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        List<String> addLines = new ArrayList<>(lines);
        for (int i = 0; i < addLines.size(); i++) {
            if (lines.get(i).contains(searchText)) {
                String[] command = lines.get(i).split("@");
                ArrayList<String> data = ManagementExcel.rearExcel_v2(command[2], command[3]);
                for (String line : data) {
                    addLines.add(i + 1, "      " + line);
                    i++;
                }
            }
            Files.write(path, addLines);
        }
    }

    public static void addLineBelowText_v3(String filePath, String searchText) throws IOException {
        Path path = Paths.get(filePath);

        // Leer todas las líneas del archivo
        List<String> lines = Files.readAllLines(path);
        List<String> modifiedLines = new ArrayList<>(lines); // Crear una lista para las líneas modificadas

        // Buscar el texto y añadir las nuevas líneas debajo
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(searchText)) {
                String[] command = lines.get(i).split("@");

                // Obtener los datos que se van a añadir
                ArrayList<String> data = ManagementExcel.rearExcel_v2(command[2], command[3]);
                for (String line : data) {
                    lines.add(i + 1, "      " + line); // Añadir debajo de la línea encontrada
                    i++; // Incrementar el índice para evitar conflictos con el índice original
                }
            }
        }

        // Escribir el contenido de nuevo al archivo
        Files.write(path, lines);
    }

}

