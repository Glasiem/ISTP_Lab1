package com.glasiem.export;

import com.glasiem.entity.MediaEntity;
import com.glasiem.entity.VTuberEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MediaToExcel {

    private XSSFWorkbook excelWorkbook;
    private XSSFSheet excelSheet;
    private List<MediaEntity> media;

    public MediaToExcel(List<MediaEntity> media) {
        this.media = media;
        excelWorkbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        excelSheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long){
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeader() {
        excelSheet = excelWorkbook.createSheet("Brands");

        Row row = excelSheet.createRow(0);

        CellStyle style = excelWorkbook.createCellStyle();
        XSSFFont font = excelWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "CONTENTS", style);
        createCell(row, 2, "VTUBER", style);
    }

    private void writeData() {
        int rowCount = 1;

        CellStyle style = excelWorkbook.createCellStyle();
        XSSFFont font = excelWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (MediaEntity media : media) {
            Row row = excelSheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, media.getId(), style);
            createCell(row, columnCount++, media.getContents(), style);
            createCell(row, columnCount++, media.getVtuber().getName(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeader();
        writeData();

        ServletOutputStream outputStream = response.getOutputStream();
        excelWorkbook.write(outputStream);
        excelWorkbook.close();

        outputStream.close();

    }
}
