import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipTesting {
    ClassLoader classLoader = ZipTesting.class.getClassLoader();

    @Test
    @DisplayName("Testing ZIP file")
    void zipTest() throws Exception {
        try (InputStream inStream = classLoader.getResourceAsStream("zipTest.zip")) {
            assert inStream != null;
            try (ZipInputStream zipStream = new ZipInputStream(inStream)) {
                ZipEntry entry;
                while ((entry = zipStream.getNextEntry()) != null) {

                    if (entry.getName().contains("csv")) {
                        CSVReader csvFileReader = new CSVReader(new InputStreamReader(zipStream, UTF_8));
                        List<String[]> csvList = csvFileReader.readAll();
                        assertThat(csvList).contains(
                                new String[]{"Testing"});

                    } else if (entry.getName().contains("pdf")) {
                        PDF pdfFile = new PDF(zipStream);
                        assertThat(pdfFile.text).contains("CHECKLIST");

                    } else if (entry.getName().contains("xls")) {
                        XLS xls = new XLS(zipStream);
                        assertThat(xls.excel.getSheetAt(0).getRow(2)
                                .getCell(4).getStringCellValue()).contains("Great Britain");
                    }
                }
            }
        }
    }
}