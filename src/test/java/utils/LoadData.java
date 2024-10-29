package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

    public static void main(String[] args) {
        String featureFilePath = "./src/test/resources/features";
        String searchText = "@externaldata";
        try {
            for (String filePath:getFeatureFilesList(featureFilePath)){
                addLineBelowText_v3(filePath, searchText);
            }
        } catch (IOException e) {
            System.err.println("Error al añadir la línea: " + e.getMessage());
        }
    }

    public static void addLineBelowText(String filePath, String searchText) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(searchText)) {
                String[] command = lines.get(i).split("@");
                ArrayList<String> data = ManagementExcel.rearExcel_v2(command[2], command[3]);
                for (int j = 0; j < data.size(); j++) {
                    lines.add(i+j+1, "      "+data.get(j));
                }
            }
        }
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
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(searchText)) {
                String[] command = lines.get(i).split("@");
                ArrayList<String> data = ManagementExcel.rearExcel_v2(command[2], command[3]);
                for (String line : data) {
                    lines.add(i + 1, "      " + line);
                    i++;
                }
            }
        }
        Files.write(path, lines);
    }

    public static ArrayList<String> getFeatureFilesList(String pathFile){
        ArrayList<String> featureFilesList = new ArrayList<>();
        try {
            File folder = new File(pathFile);
            if(folder.getName().endsWith(".feature")){
                featureFilesList.add(folder.getPath());
            } else if (folder.getName().endsWith("features") && folder.isDirectory() && folder.listFiles().length > 0) {
                for(File file:folder.listFiles()){
                    if(file.getName().endsWith(".feature")){
                        featureFilesList.add(file.getPath());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return featureFilesList;
    }
}

