package toexcel;

import domain.PaymentSchedule;
import domain.Payments;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.Map;

public class ExcelDocument extends AbstractXlsxView {
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        // change the file name
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"pay_reports.xlsx\"");

        @SuppressWarnings("unchecked")
        Payments payments = (Payments) map.get("payments");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Отчет");

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Период платежа");
        header.createCell(1).setCellValue("Платеж");
        header.createCell(2).setCellValue("Начисленные %");
        header.createCell(3).setCellValue("Сумма платежа (платеж+%");
        header.createCell(4).setCellValue("Остаток");

        // Create data cells
        int rowCount = 1;
        for (PaymentSchedule schedule : payments.getPaymentSchedules()) {
            rowCount++;
            Row supplyRow = sheet.createRow(rowCount);
            supplyRow.createCell(0).setCellValue(rowCount-1);
            supplyRow.createCell(1).setCellValue(schedule.getArrayMoney());
            supplyRow.createCell(2).setCellValue(schedule.getPersent());
            supplyRow.createCell(3).setCellValue(schedule.getPaymentAmount());
            supplyRow.createCell(4).setCellValue(schedule.getPrincipalBalance());
        }
    }

}
