package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.event.EventUser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl {
    @Autowired
    private EventUserService eventUserService;

    public void importData(String path , String id) {
        try {
//            String excelPath = "D:\\data\\"+path;
            String excelPath = "D:\\data\\DAa.xlsx";
            System.out.println(excelPath);
            XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
            XSSFSheet sheet = workbook.getSheet("Event Statistical");
            int rowCount = sheet.getPhysicalNumberOfRows();
            String[] studentList = new String[rowCount];
            DataFormatter formatter = new DataFormatter();
            for (int i = 1 ; i <rowCount ; i++) {
                String value = formatter.formatCellValue(sheet.getRow(i).getCell(1));
                studentList[i-1] = value;
            }
            for (int i =0 ; i <studentList.length; i++) {
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                EventUser eventUser = eventUserService.getEventUserByEventAndUser(Long.parseLong(id),Long.parseLong(studentList[i]));
                if (eventUser == null) {
                    eventUserService.addEventUser(format.format(date),Long.parseLong(id),Long.parseLong(studentList[i]));
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public ByteArrayInputStream export(List<EventUser> eventUsers) throws IOException {
        String event = "";
        String location = "";
        String start = "";
        String end = "";
        String[] columns = {"STT","Code","Name","Time Checkin"};
        try(Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Event Statistical");
            Row row0 = sheet.createRow(0);
            Cell cell0 = row0.createCell(0);
            Row row1 = sheet.createRow(0);
            Cell cell1= row1.createCell(1);
            Row row2 = sheet.createRow(0);
            Cell cell2 = row2.createCell(2);
            Row row3 = sheet.createRow(0);
            Cell cell3 = row3.createCell(3);
            for (EventUser user: eventUsers) {
                 event = user.getEvent().getName();
                 location = user.getEvent().getLocation();
                 start = user.getEvent().getStartTime().toString();
                 end = user.getEvent().getEndTime().toString();
            }
            cell0.setCellValue("Event: " + event);
            cell1.setCellValue("Location: " + location);
            cell2.setCellValue("Start: " + start);
            cell3.setCellValue("End: " + end);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(1);

            for (int col=1;col <columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(cellStyle);
            }

            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

            int rowIndex = 2;
            for (EventUser user: eventUsers) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rowIndex-1);
                row.createCell(1).setCellValue(user.getUser().getCode());
                row.createCell(2).setCellValue(user.getUser().getName());
                row.createCell(3).setCellValue(user.getCheckin().toString());
            }

            workbook.write(out);
            return  new ByteArrayInputStream(out.toByteArray());
        }
    }
}
