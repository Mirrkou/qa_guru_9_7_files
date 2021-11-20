package guru.qa.junit;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class JUnitTest {

    @ValueSource(strings = {"шапка-ушанка", "пила строительная"})
    @Tag("blocker")
    @DisplayName("Поиск на wildberries.ru")
    @ParameterizedTest(name = "Поиск слова {0} и проверка отображения текста {0}")
    void searchGihubTestValue(String searchQuery) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchQuery);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(searchQuery)).shouldBe(visible);
    }

    @CsvSource(value = {
            "шапка-ушанка| Шапка ушанка женская меховая",
            "пила строительная| ППТ-255-П"
    },
            delimiter = '|')
    @Tag("blocker")
    @DisplayName("Поиск на wildberries.ru")
    @ParameterizedTest(name = "Поиск на wildberries.ru товара {0} и проверка отображения текста {1}")
    void searchHatAndSawTest(String searchQuery, String expectedResult) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchQuery);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(expectedResult)).shouldBe(visible);
    }

    @CsvSource(value = {
            "шуруповерт| 1",
            "пила строительная| 1"
    },
            delimiter = '|')
    @Tag("blocker")
    @DisplayName("Поиск на wildberries.ru")
    @ParameterizedTest(name = "Поиск на wildberries.ru товара {0} и проверка отображения текста {1}")
    void searchScrewGunAndSawTest(String searchQuery, int expectedResult) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchQuery);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(String.valueOf(expectedResult)))
                                       .shouldBe(visible);
    }

    static Stream<Arguments> searchScrewGunAndSaw2Test() {
        return Stream.of(
                Arguments.of("шапка-ушанка", List.of("шапка")),
                Arguments.of("пила строительная", List.of("пила"))
        );
    }

    @MethodSource
    @Tag("blocker")
    @DisplayName("Поиск на wildberries.ru")
    @ParameterizedTest(name = "Поиск на wildberries.ru товара {0} и проверка отображения текста {1}")
    void searchScrewGunAndSaw2Test(String searchQuery, List<String> expectedResult) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchQuery);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(expectedResult.get(0))).shouldBe(visible);
    }

    @EnumSource(SearchQuery.class)
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск на wildberries.ru товара {0}")
    void commonGithubSearchWithEnumTest(SearchQuery searchQuery) {
        open("https://www.wildberries.ru/");
        $("#searchInput").setValue(searchQuery.name());
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(searchQuery.name())).shouldBe(visible);
    }
}















