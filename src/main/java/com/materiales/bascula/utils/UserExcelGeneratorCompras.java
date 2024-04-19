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

import com.materiales.bascula.model.CompraReporteExcel;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelGeneratorCompras {

    private List<CompraReporteExcel> compraReporteExcel;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelGeneratorCompras(List<CompraReporteExcel> compraReporteExcel) {
        this.compraReporteExcel = compraReporteExcel;
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
        createCell(row, 0, "Folio", style);
        createCell(row, 1, "Fecha", style);
        createCell(row, 2, "Sucursal", style);
        createCell(row, 3, "Recuperador", style);
        createCell(row, 4, "Usuario", style);
        createCell(row, 5, "Flete", style);
        createCell(row, 6, "Material", style);
        createCell(row, 7, "Peso Neto", style);
        createCell(row, 8, "Precio", style);
        createCell(row, 9, "Total", style);
        createCell(row, 10, "Estatus", style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (CompraReporteExcel record : compraReporteExcel) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getFolio(), style);
            createCell(row, columnCount++, record.getFecha(), style);
            createCell(row, columnCount++, record.getSucursal(), style);
            createCell(row, columnCount++, record.getRecuperador(), style);
            createCell(row, columnCount++, record.getUsuario(), style);
            createCell(row, columnCount++, record.getFlete(), style);
            createCell(row, columnCount++, record.getMaterial(), style);
            createCell(row, columnCount++, record.getPesoNeto(), style);
            createCell(row, columnCount++, record.getPrecio(), style);
            createCell(row, columnCount++, record.getTotal(), style);
            createCell(row, columnCount++, record.getEstatus(), style);
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