package com.materiales.bascula.utils;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.materiales.bascula.model.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelGenerator {

    private List<User> users;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelGenerator(List<User> users) {
        this.users = users;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Usuarios");
        Row row = sheet.createRow(0);
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        font.setColor(IndexedColors.DARK_BLUE.index);
        style.setFont(font);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.LIME.getIndex());
        createCell(row, 0, "Usuario", style);
        createCell(row, 1, "Nombre", style);
        createCell(row, 2, "Apellido", style);
        createCell(row, 3, "Rol", style);
        createCell(row, 4, "Sucursal", style);
        createCell(row, 5, "Habilitado", style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User record : users) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getUsername(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getLastName(), style);
            createCell(row, columnCount++, record.getRol(), style);
            createCell(row, columnCount++, record.getSucursal(), style);
            createCell(row, columnCount++, record.isEnabled(), style);
        }

        IntStream.range(0, 5).forEach(i -> sheet.autoSizeColumn(i));
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
 
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}