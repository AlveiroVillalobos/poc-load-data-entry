package com.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ManagementExcel {
    public static void main(String[] args) {
        String rutaArchivo = "./src/test/resources/dataEntry.xlsx"; // Cambia esto por la ruta de tu archivo
        System.out.println("print arraylist");
//        rearExcel_v2(rutaArchivo, "test1").forEach((d) -> {System.out.println(d);});
    }

    public static void rearExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Lee la primera hoja

            for (Row row : sheet) {
                boolean filaNoVacia = false; // Bandera para verificar si la fila tiene celdas no vacías
//                System.out.print(row.getRowNum()+ "\t");
                for (Cell cell : row) {
                    // Verifica si la celda no está vacía
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        filaNoVacia = true; // La fila tiene al menos una celda no vacía
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print("|" + cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                System.out.print("|" + cell.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                System.out.print("|" + cell.getBooleanCellValue() + "\t");
                                break;
                            default:
                                System.out.print("Tipo no soportado\t");
                        }
                    }
                }

                if (filaNoVacia) {
                    System.out.println("|");
                    System.out.println(); // Nueva línea para cada fila no vacía
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> rearExcel_v2(String filePath, String sheetName, String[] headers) {

        ArrayList<String> data = new ArrayList<String>();
        StringBuilder line = new StringBuilder();

        try{
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheet(sheetName); // Lee la primera hoja
            if (sheet == null) {
                System.out.println("La hoja con el nombre '" + sheetName + "' no existe.");
                return data;
            }

            for (Row row : sheet) {
                boolean filaNoVacia = false; // Bandera para verificar si la fila tiene celdas no vacías
                if (row.getRowNum() == 0){
                    if(!validateHeaders(row, headers)){
                        System.exit(1);
                    }
                }
                for (Cell cell : row) {
                    // Verifica si la celda no está vacía
                    if (cell != null && cell.getCellType() != CellType.BLANK && row.getRowNum() != 0) {
                        filaNoVacia = true; // La fila tiene al menos una celda no vacía
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
                    System.out.println(); // Nueva línea para cada fila no vacía
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean validateHeaders(Row row, String[] headers){
        boolean validateHeaders = false;
        for (int i = 0; i < headers.length; i++) {
            if (!row.getCell(i).getStringCellValue().equals(headers[i])){
                System.out.println("no existe este titulo de celda");
                validateHeaders = false;
            }else {
                System.out.println("titulo OK");
                validateHeaders = true;
            }
        }
        return validateHeaders;
    }
}
