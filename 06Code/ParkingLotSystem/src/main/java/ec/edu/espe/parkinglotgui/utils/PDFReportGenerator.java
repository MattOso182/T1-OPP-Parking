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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFReportGenerator {

    public static void generateVehiclesReport(JTable table) {
        generateReport(table, "Reporte");
    }

    public static void generateReport(JTable table, String reportType) {

        try {
            String defaultFileName = "Reporte_" + reportType + "_" +
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".pdf";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte PDF");
            fileChooser.setSelectedFile(new File(defaultFileName));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; 
            }

            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            PdfWriter writer = new PdfWriter(fileToSave.getAbsolutePath());
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

            Paragraph title = new Paragraph("Sistema de Parqueo - " + reportType)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            Paragraph date = new Paragraph("Generado el: " + LocalDate.now())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(date);
            document.add(new Paragraph("\n"));

            TableModel model = table.getModel();
            int columns = model.getColumnCount();

            Table pdfTable = new Table(UnitValue.createPercentArray(columns))
                    .useAllAvailableWidth();

            for (int i = 0; i < columns; i++) {
                pdfTable.addHeaderCell(
                        new Cell()
                                .add(new Paragraph(model.getColumnName(i)))
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setBold()
                                .setTextAlignment(TextAlignment.CENTER)
                );
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < columns; col++) {
                    pdfTable.addCell(
                            new Cell()
                                    .add(new Paragraph(String.valueOf(model.getValueAt(row, col))))
                                    .setTextAlignment(TextAlignment.CENTER)
                    );
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(
                    null,
                    "PDF generado correctamente en:\n" + fileToSave.getAbsolutePath()
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error generando archivo PDF: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}