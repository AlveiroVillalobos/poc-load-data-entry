package com.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

    public static void main(String[] args) {
        loadDataFromExcel("./src/test/resources/features");
    }

    public static void loadDataFromExcel(String featureFilePath) {
        String searchText = "@externaldata";
        try {
            for (String filePath : getFeatureFilesList(featureFilePath)) {
                addLineBelowText(filePath, searchText);
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
                String[] headers = ArrayUtils.remove(lines.get(i - 1)
                        .replace(" ", "").split("\\|"), 0);

                String[] command = lines.get(i).split("@");
                ArrayList<String> data = readExcel(command[2], command[3], headers);
                lines.remove(i+1);
                for (String line : data) {
                    lines.add(i + 1, "      " + line);
                    i++;
                }
            }
        }
        Files.write(path, lines);
    }


    public static ArrayList<String> getFeatureFilesList(String pathFile) {
        ArrayList<String> featureFilesList = new ArrayList<>();
        try {
            File folder = new File(pathFile);
            if (folder.getName().endsWith(".feature")) {
                featureFilesList.add(folder.getPath());
            } else if (folder.getName().endsWith("features") && folder.isDirectory() && folder.listFiles().length > 0) {
                for (File file : folder.listFiles()) {
                    if (file.getName().endsWith(".feature")) {
                        featureFilesList.add(file.getPath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return featureFilesList;
    }

    public static ArrayList<String> readExcel(String filePath, String sheetName, String[] headers) {
        ArrayList<String> data = new ArrayList<String>();
        StringBuilder line = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("La hoja con el nombre '" + sheetName + "' no existe.");
                return data;
            }
            for (Row row : sheet) {
                boolean filaNoVacia = false;
                if (row.getRowNum() == 0) {
                    if (!validateHeaders(row, headers)) {
                        System.exit(1);
                    }
                }
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK && row.getRowNum() != 0) {
                        filaNoVacia = true;
                        switch (cell.getCellType()) {
                            case STRING:
                                line.append("|").append(cell.getStringCellValue());
                                System.out.print("|" + cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String formattedDate = dateFormat.format(cell.getDateCellValue());
                                    line.append("|").append(formattedDate);
                                    System.out.print("|" + formattedDate + "\t");
                                } else {
                                    line.append("|").append(cell.getNumericCellValue());
                                    System.out.print("|" + cell.getNumericCellValue() + "\t");
                                }
                                break;
                            case BOOLEAN:
                                line.append(cell.getBooleanCellValue());
                                System.out.print("|" + cell.getBooleanCellValue() + "\t");
                                break;
                            default:
                                System.out.print("Tipo no soportado\t");
                        }
                    }
                }
                if (filaNoVacia) {
                    line.append("|");
                    data.add(line.toString());
                    line = new StringBuilder();
                    System.out.println("|");
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean validateHeaders(Row row, String[] headers) {
        boolean validateHeaders = false;
        for (int i = 0; i < headers.length; i++) {
            if (!row.getCell(i).getStringCellValue().equals(headers[i])) {
                System.out.println("no existe este titulo de celda");
                validateHeaders = false;
            } else {
                System.out.println("titulo OK");
                validateHeaders = true;
            }
        }
        return validateHeaders;
    }
}
