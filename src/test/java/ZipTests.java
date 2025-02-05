import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;

public class ZipTests {

    private ClassLoader cl = ZipTests.class.getClassLoader();

    @Test
    void checkPdfFileFromZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Homework.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().toLowerCase().endsWith(".pdf")){
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(pdf.text.contains("Сердце"));
                }
            }
        }
    }
    @Test
    void checkXlsxFileFromZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Homework.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().toLowerCase().endsWith(".xlsx")){
                    XLS xls = new XLS(zis);
                    String actualValue = xls.excel.getSheetAt(0).getRow(0).getCell(2).getStringCellValue();

                    Assertions.assertTrue(actualValue.contains("Название"));
                }
            }
        }
    }
    @Test
    void checkCsvFileFromZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Homework.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().toLowerCase().endsWith(".csv")){
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(7, data.size());
                    Assertions.assertArrayEquals(
                            new String []{"Камышовый кот"},  data.get(2));
                }
            }
        }
    }
}