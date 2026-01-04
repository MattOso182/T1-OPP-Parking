package ec.edu.espe.parkinglotgui.utils;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFReportGenerator {
    public static void generateVehiclesReport(JTable table) {

        try {
            String fileName = "Reporte" +
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".pdf";

            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            InputStream logoStream = PDFReportGenerator.class
                    .getResourceAsStream("/images/logo.png");

            if (logoStream != null) {
                ImageData logoData = ImageDataFactory.create(logoStream.readAllBytes());
                Image logo = new Image(logoData);
                logo.setWidth(100);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
            }

           
            Paragraph title = new Paragraph("Sistema de Parqueo - Reporte")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);

          
            Paragraph date = new Paragraph("Generated on: " + LocalDate.now())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);

            document.add(date);
            document.add(new Paragraph("\n"));

         
            TableModel model = table.getModel();
            int columns = model.getColumnCount();

            Table pdfTable = new Table(UnitValue.createPercentArray(columns))
                    .useAllAvailableWidth();

          
            for (int i = 0; i < columns; i++) {
                Cell header = new Cell()
                        .add(new Paragraph(model.getColumnName(i)))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER);
                pdfTable.addHeaderCell(header);
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < columns; col++) {
                    Object value = model.getValueAt(row, col);
                    pdfTable.addCell(
                            new Cell()
                                    .add(new Paragraph(String.valueOf(value)))
                                    .setTextAlignment(TextAlignment.CENTER)
                    );
                }
            }

            document.add(pdfTable);
            document.close();

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "PDF generated successfully:\n" + fileName
            );

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Error generating PDF: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }

}
