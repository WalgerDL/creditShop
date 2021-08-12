package topdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.PaymentSchedule;
import domain.Payments;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Slf4j
public class PdfDocument  {
    public static ByteArrayInputStream employeePDFReport (Payments payments) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            //BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251", BaseFont.EMBEDDED);
            //Paragraph p=new Paragraph("чета там",new Font(times,14));

            //BaseFont baseFont = BaseFont.createFont("src/Components/font/cyr.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            //Font font = new Font(baseFont, 14, Font.NORMAL);
            //document.add(new com.itextpdf.text.Paragraph(text, font));

            Font font = FontFactory.getFont(FontFactory.COURIER, 14,
                    BaseColor.BLACK);
            Paragraph para = new Paragraph("Payment schedule", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            // Add PDF Table Header ->
            Stream.of("NN", "Payment", "Accounting %", "Payment amount", "Principal Balance").forEach(headerTitle ->
            {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            int rowCount = 1;
            for (PaymentSchedule schedule : payments.getPaymentSchedules()) {
                PdfPCell idCell = new PdfPCell(new Phrase(String.format("%d", rowCount)));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(idCell);

                PdfPCell firstNameCell = new PdfPCell(new Phrase(String.format("%.2f", schedule.getArrayMoney())));
                firstNameCell.setPaddingLeft(4);
                firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(firstNameCell);

                PdfPCell lastNameCell = new PdfPCell(new Phrase(String.format("%.2f", schedule.getPersent())));
                lastNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lastNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                lastNameCell.setPaddingRight(4);
                table.addCell(lastNameCell);

                PdfPCell paymentAmountNameCell = new PdfPCell(new Phrase(String.format("%.2f", schedule.getPaymentAmount())));
                paymentAmountNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                paymentAmountNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                paymentAmountNameCell.setPaddingRight(4);
                table.addCell(paymentAmountNameCell);

                PdfPCell principalBalanceNameCell = new PdfPCell(new Phrase(String.format("%.2f", schedule.getPrincipalBalance())));
                principalBalanceNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                principalBalanceNameCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                principalBalanceNameCell.setPaddingRight(4);
                table.addCell(principalBalanceNameCell);

                rowCount++;
            }
            document.add(table);
            document.close();

        } catch (DocumentException e) {
            log.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
