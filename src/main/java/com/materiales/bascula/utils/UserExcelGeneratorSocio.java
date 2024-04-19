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

import com.materiales.bascula.model.Recuperador;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelGeneratorSocio {

    private List<Recuperador> recuperadores;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelGeneratorSocio(List<Recuperador> recuperadores) {
        this.recuperadores = recuperadores;
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
        createCell(row, 0, "Nombre", style);
        createCell(row, 1, "Apellido Paterno", style);
        createCell(row, 2, "Apellido Materno", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Telefono", style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Recuperador record : recuperadores) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getNombre(), style);
            createCell(row, columnCount++, record.getApPaterno(), style);
            createCell(row, columnCount++, record.getApMaterno(), style);
            createCell(row, columnCount++, record.getEmail(), style);
            createCell(row, columnCount++, record.getTelefono(), style);
        }

        IntStream.range(0, 5).forEach(i -> sheet.autoSizeColumn(i));
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
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
