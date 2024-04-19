package com.materiales.bascula.utils;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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

import com.materiales.bascula.model.ExcelDetalle;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelGeneratorComprasSimple {

    private List<ExcelDetalle> compraReporteExcel;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelGeneratorComprasSimple(List<ExcelDetalle> compraReporteExcel) {
        this.compraReporteExcel = compraReporteExcel;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Usuarios");

        XSSFCellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        Row row = sheet.createRow(0);
        createCell(row, 0, "Recuperador", style);
        createCell(row, 1, compraReporteExcel.get(0).getRecuperador(), style);

        row = sheet.createRow(1);
        createCell(row, 0, "Folio", style);
        createCell(row, 1, compraReporteExcel.get(0).getFolio(), style);

        row = sheet.createRow(2);
        createCell(row, 0, "Usuario", style);
        createCell(row, 1, compraReporteExcel.get(0).getUsuario(), style);

        row = sheet.createRow(3);
        createCell(row, 0, "Sucursal", style);
        createCell(row, 1, compraReporteExcel.get(0).getSucursal(), style);

        row = sheet.createRow(4);
        createCell(row, 0, "Total", style);
        createCell(row, 1,
                NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("US").build())
                        .format(compraReporteExcel.get(0).getTotal()),
                style);

        row = sheet.createRow(5);
        createCell(row, 0, "Fecha", style);
        createCell(row, 1, compraReporteExcel.get(0).getFecha(), style);

        row = sheet.createRow(6);
        createCell(row, 0, "Flete", style);
        createCell(row, 1, compraReporteExcel.get(0).getFlete(), style);


        row = sheet.createRow(7);
        style = workbook.createCellStyle();
        font = workbook.createFont();

        font.setBold(true);
        font.setFontHeight(16);
        font.setColor(IndexedColors.DARK_BLUE.index);
        style.setFont(font);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.LIME.getIndex());
        createCell(row, 0, "Codigo", style);
        createCell(row, 1, "Peso Bruto", style);
        createCell(row, 2, "Peso Neto", style);
        createCell(row, 3, "Tara", style);
        createCell(row, 4, "Precio", style);
        createCell(row, 5, "Total", style);

    }

    private void write() {
        int rowCount = 8;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (ExcelDetalle record : compraReporteExcel) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getCode(), style);
            createCell(row, columnCount++, record.getPesoBruto(), style);
            createCell(row, columnCount++, record.getPesoNeto(), style);
            createCell(row, columnCount++, record.getTara(), style);
            createCell(row, columnCount++,
                    NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("US").build())
                            .format(record.getPrecio()),
                    style);
            createCell(row, columnCount++,
                    NumberFormat.getCurrencyInstance(new Locale.Builder().setLanguage("en").setRegion("US").build())
                            .format(record.getSubtotal()),
                    style);
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
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
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