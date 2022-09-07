package guru.qa;

import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.domain.Person;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFilesTest {

    private final ClassLoader classLoader = ZipFilesTest.class.getClassLoader();

    private InputStream getFileFromZip(String fileName) throws Exception {
        ZipFile zip = new ZipFile(new File("src/test/resources/resources.zip"));
        return zip.getInputStream(zip.getEntry(fileName));
    }

    @Test
    void pdfTest() throws Exception {
        Pdf pdf = new Pdf(new String(getFileFromZip("sample.pdf").readAllBytes(), UTF_8));
        assertThat(pdf.getContent()).contains("The end, and just as well");
    }

    @Test
    void xlsxTest() throws Exception {
        XLS xlsx = new XLS(getFileFromZip("Financial Sample.xlsx"));
        assertThat(xlsx.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue())
                .isEqualTo("Government");
    }

    @Test
    void csvTest() throws Exception {
        CSVReader csv = new CSVReader(
                new InputStreamReader(getFileFromZip("username-password-recovery-code.csv"), UTF_8)
        );
        List<String[]> csvArray = csv.readAll();
        assertThat(csvArray).contains(new String[]{"jenkins46;9346;14ju73;mj9346;Mary;Jenkins;Engineering;Manchester"});
    }

    @Test
    void jsonTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("person.json")) {
            ObjectMapper mapper = new ObjectMapper();
            Person person = mapper.readValue(is, Person.class);
            String[] accounts = new String[]{"acc1$@domain", "acc2$@domain", "acc3$@domain"};
            assertThat(person.getAccounts()).isEqualTo(accounts);
            assertThat(person.getDisplayName()).isEqualTo("Ivan Terentiev");
            assertThat(person.getWork().getTelephone()).isEqualTo("+78009002010");
        }
    }
}
