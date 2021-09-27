package com.cassandra.example.export;

import com.cassandra.example.model.Customer;
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

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Customer> listCustomer;

    public UserExcelExporter(List<Customer> listCustomer){
        this.listCustomer=listCustomer;
        workbook= new XSSFWorkbook();
    }

    private  void writeHeaderLine(){
        sheet=workbook.createSheet();
        Row row= sheet.createRow(0);

        CellStyle style=workbook.createCellStyle();
        XSSFFont font= workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "BillingAccountNumber", style);
        createCell(row, 1, "First Name", style);
        createCell(row, 2, "Last Name", style);
        createCell(row, 3, "E-Mail", style);
        createCell(row, 4, "PhoneNumber", style);
        createCell(row, 5, "Zip Code", style);
        createCell(row, 6, "ConversationID", style);
        createCell(row, 7, "Address", style);
    }
    private void createCell(Row row,int columnCount,Object value,CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell=row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Customer customer : listCustomer) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, customer.getBillingAccountNumber(), style);
            createCell(row, columnCount++, customer.getFirstName(), style);
            createCell(row, columnCount++, customer.getLastName(), style);
            createCell(row, columnCount++, customer.getEmailId(), style);
            createCell(row, columnCount++, customer.getPhoneNumber(), style);
            createCell(row, columnCount++, customer.getZip(), style);
            createCell(row, columnCount++, customer.getConversationId(), style);
            createCell(row, columnCount++, customer.getAddress().toString(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
