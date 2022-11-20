package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.event.EventUser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl {

    public ByteArrayInputStream export(List<EventUser> eventUsers) throws IOException {
        String[] columns = {"STT","User Code","User Name","User Class"};
        try(Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            CreationHelper creationHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Event Statistical");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            for (int col=0;col <columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(cellStyle);
            }

            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

            int rowIndex = 1;
            for (EventUser user: eventUsers) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rowIndex-1);
                row.createCell(1).setCellValue(user.getUser().getCode());
                row.createCell(2).setCellValue(user.getUser().getName());
                row.createCell(3).setCellValue(user.getUser().getCourse().getName()+"-"+user.getUser().getClassUser().getName());
            }

            workbook.write(out);
            return  new ByteArrayInputStream(out.toByteArray());
        }
    }
}
