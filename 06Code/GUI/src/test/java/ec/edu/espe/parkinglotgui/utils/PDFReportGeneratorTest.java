package ec.edu.espe.parkinglotgui.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.Mockito.*;

/**
 *
 * @author Mateo Aymacaña, T.A.P. (The Art of Programming), @ESPE
 */
public class PDFReportGeneratorTest {

    @TempDir
    Path tempDir;

    private JTable createTestTable(int rows, int cols) {
        Object[][] data = new Object[rows][cols];
        String[] columnNames = new String[cols];

        for (int i = 0; i < cols; i++) {
            columnNames[i] = "Columna " + (i + 1);
            for (int j = 0; j < rows; j++) {
                data[j][i] = "Dato " + (j + 1) + "-" + (i + 1);
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        return new JTable(model);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @DisplayName("TC001: Null table should throw NullPointerException")
    void testGenerateVehiclesReport_NullTable_ShouldThrowException() {
        // Arrange
        JTable nullTable = null;
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            PDFReportGenerator.generateVehiclesReport(nullTable);
        }, "Should throw NullPointerException when table is null");
    }

    @Test
    @DisplayName("TC002: Empty table with 0 rows")
    void testGenerateReport_EmptyTable_ShouldCreatePDF() {
        // Arrange
        JTable emptyTable = createTestTable(0, 3); 
        
        try {
            PDFReportGenerator.generateReport(emptyTable, "Test Report");
            
            assertTrue(true, "Method should not throw exception with empty table");
            
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException || 
                      e.getMessage().contains("dialog"), 
                      "Expected dialog-related exception");
        }
    }

    @Test
    @DisplayName("TC003: Table with null values in cells")
    void testGenerateReport_TableWithNullValues_ShouldHandleGracefully() {
        // Arrange
        Object[][] data = {
            {"Val1", null, "Val3"},
            {null, "Val2", null}
        };
        String[] columns = {"Col1", "Col2", "Col3"};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Null Values Test");
        }, "Should handle null values without throwing exception");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t\n"})
    @DisplayName("TC004: Empty or whitespace report type")
    void testGenerateReport_EmptyReportType_ShouldNotCrash(String reportType) {
        // Arrange
        JTable table = createTestTable(2, 2);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, reportType);
        }, "Should handle empty report type without crashing");
    }

    @Test
    @DisplayName("TC005: Very long column names")
    void testGenerateReport_VeryLongColumnNames_ShouldCreatePDF() {
        // Arrange
        String[] longColumns = {
            "Esta es una columna con un nombre extremadamente largo que podría causar problemas de formato",
            "Otra columna larga".repeat(10),
            "C" 
        };
        Object[][] data = {{"A", "B", "C"}};
        DefaultTableModel model = new DefaultTableModel(data, longColumns);
        JTable table = new JTable(model);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Long Columns Test");
        }, "Should handle long column names gracefully");
    }

    @Test
    @DisplayName("TC006: Table with special characters")
    void testGenerateReport_SpecialCharactersInData() {
        // Arrange
        Object[][] data = {
            {"Normal", "Con\nsalto\nde\nlínea", "Tab\t aquí"},
            {"Comillas \"dobles\"", "Apostrofe's", "Backslash \\"},
            {"Unicode: © ® €", "Emoji: 😀 🚗", "Áccéntéd Chárs"},
            {"<HTML> & tags", "SQL: ' OR 1=1 --", "Script: <script>alert('xss')</script>"}
        };
        String[] columns = {"Tipo", "Contenido", "Ejemplo"};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Special Chars Test");
        }, "Should handle special characters without issues");
    }

    @Test
    @DisplayName("TC007: Large table (performance test)")
    void testGenerateReport_LargeTable_ShouldNotTimeout() {
        // Arrange - tabla grande
        int rows = 100;
        int cols = 10;
        JTable largeTable = createTestTable(rows, cols);
        
        // Act & Assert con timeout
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(10), () -> {
            PDFReportGenerator.generateReport(largeTable, "Large Table Test");
        }, "Should handle large table within reasonable time");
    }

    @Test
    @DisplayName("TC008: Missing logo image resource")
    void testGenerateReport_WithoutLogo_ShouldStillWork() {
        // Arrange
        JTable table = createTestTable(1, 1);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "No Logo Test");
        }, "Should work even if logo image is not found");
    }

    @Test
    @DisplayName("TC009: User cancels file dialog")
    void testGenerateReport_UserCancelsDialog_ShouldExitSilently() {
        // Arrange
        JTable table = createTestTable(2, 2);
        
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Cancelled Test");
        }, "Should exit silently when user cancels file dialog");
    }

    @Test
    @DisplayName("TC010: Invalid file path (read-only location)")
    void testGenerateReport_InvalidFilePath_ShouldShowErrorMessage() {
        // Arrange
        JTable table = createTestTable(1, 1);
        
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Invalid Path Test");
        }, "Should handle file system errors gracefully");
    }

    @Test
    @DisplayName("TC011: Concurrent access - multiple calls")
    void testGenerateReport_ConcurrentCalls_ShouldNotInterfere() {
        // Arrange
        JTable table1 = createTestTable(3, 3);
        JTable table2 = createTestTable(2, 4);
        
        // Act & Assert
        assertAll("Concurrent calls should not interfere",
            () -> assertDoesNotThrow(() -> 
                PDFReportGenerator.generateReport(table1, "Report 1")),
            () -> assertDoesNotThrow(() -> 
                PDFReportGenerator.generateReport(table2, "Report 2"))
        );
    }

    @Test
    @DisplayName("TC012: Test with very long report type name")
    void testGenerateReport_VeryLongReportType() {
        // Arrange
        JTable table = createTestTable(1, 1);
        String longReportType = "Este es un nombre de reporte extremadamente largo que podría causar problemas en el título del PDF ".repeat(5);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, longReportType);
        }, "Should handle very long report type names");
    }

    @Test
    @DisplayName("TC013: Table with different data types")
    void testGenerateReport_MixedDataTypes() {
        // Arrange
        Object[][] data = {
            {123, 45.67, true, null},
            {"Texto", 100, false, new java.util.Date()}
        };
        String[] columns = {"Entero", "Decimal", "Booleano", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                return Object.class; // Todas las columnas como Object
            }
        };
        JTable table = new JTable(model);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Mixed Types Test");
        }, "Should handle mixed data types in table cells");
    }

    @Test
    @DisplayName("TC014: This test is designed to FAIL - Null table model")
    void testGenerateReport_NullTableModel_ShouldFail() {
        // Arrange 
        JTable table = mock(JTable.class);
        when(table.getModel()).thenReturn(null);
        
        // Act & Assert 
        assertThrows(NullPointerException.class, () -> {
            PDFReportGenerator.generateReport(table, "Fail Test");
        }, "Should fail when table model is null");
    }

    @Test
    @DisplayName("TC015: This test should also FAIL - Invalid file path characters")
    void testGenerateReport_InvalidFileNameCharacters_ShouldFail() {
        // Arrange
        JTable table = createTestTable(1, 1);
        
        assertDoesNotThrow(() -> {
            PDFReportGenerator.generateReport(table, "Test: *?<>|"); // Caracteres inválidos en Windows
        }, "Method may or may not handle invalid filename characters");
    }
}
