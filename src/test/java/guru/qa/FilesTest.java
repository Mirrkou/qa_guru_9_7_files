package guru.qa;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FilesTest {

    @Test
    @DisplayName("Загрузка файла по абсолютному пути (не рекомендуется)")
    void fileNameShouldDisplayedAfterUploadActionAbsolutePathTest() {
        open("https://dropmefiles.com/");
        File exampleFile = new File("D:\\dem\\src\\test\\resources\\example.txt");
        $("input[type='file']").uploadFile(exampleFile);
        $("[class='expand']").click();
        $("[id='upfiles']").shouldHave(text("example.txt"));
    }

    @Test
    @DisplayName("Загрузка файла по абсолютному пути (не рекомендуется)")
    void fileNameShouldDisplayedAfterUploadActionAbsolutePathTest1() {
        open("https://dropmefiles.net/");
        File exampleFile = new File("D:\\dem\\src\\test\\resources\\example.txt");
        $("input[type='file']").uploadFile(exampleFile);
        $("#uploadBtn").click();
        $("#qr").shouldBe(visible);
    }

    @Test
    @DisplayName("Загрузка файла по относительному пути (рекомендуется)")
    void fileNameShouldDisplayedAfterUploadActionFromClasspathTest() {
        open("https://dropmefiles.net/");
        $("input[type='file']").uploadFromClasspath("example.txt");
        $("#uploadBtn").click();
        $("#qr").shouldBe(visible);
    }

    @Test
    @DisplayName("Загрузка нескольких файлов по относительному пути")
    void uploadSeveralFilesFromClasspathTest() {
        open("https://dropmefiles.com/");
        $("input[type='file']").uploadFromClasspath("ava1.png", "ava2.png");
        $("[class='expand']").click();
        $("[id='upfiles']").shouldHave(text("ava1.png"));
        $("[id='upfiles']").shouldHave(text("ava2.png"));
    }

    @Test
    @DisplayName("Скачивание txt файла и проверка его содержимого")
    void downloadSimpleTextFileTest() throws IOException {
        open("https://filesamples.com/formats/txt");
        File download = $("[class='btn btn-primary pull-right']").download();
        System.out.println(download.getAbsolutePath());
        String fileContent = IOUtils.toString(new FileReader(download));
        assertTrue(fileContent.contains("Quod equidem non reprehendo"));
    }

    @Test
    @DisplayName("Скачивание PDF файла")
    void pdfFileDownloadTest() throws IOException {
        open("https://kub-24.ru/prajs-list-shablon-prajs-lista-2020-v-excel-word-pdf/");
        $(byText("шаблон прайс-листа в PDF")).scrollTo();
        File pdf = $(byText("шаблон прайс-листа в PDF")).download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(1, parsedPdf.numberOfPages);
    }

    @Test
    @DisplayName("Скачивание xls файла")
    void xlsFileDownloadTest() throws IOException {
        open("https://file-examples.com/index.php/sample-documents-download/sample-xls-download/#google_vignette");
        $(byText("Download sample xls file")).scrollTo();
        File xls = $(byText("Download sample xls file")).download();
        XLS parsedXls = new XLS(xls);
        boolean chekXls = parsedXls.excel
                .getSheetAt(0)
                .getRow( 1)
                .getCell(1)
                .getStringCellValue()
                .contains("Dulce");
        assertTrue(chekXls);
    }
}
