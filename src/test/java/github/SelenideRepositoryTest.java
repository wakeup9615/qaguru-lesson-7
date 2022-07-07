package github;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideRepositoryTest {

    @Test
    void solntsevShouldBeFirstContributor() {
        // открыть страницу гитхаба
        open("https://github.com/");
        // ввести в поле поиска selenide и нажать Enter
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();
        // нажать на линк от первого результата поиска
        $$("ul.repo-list li").first().$("a").click();
        //проверить что в загловке встречается selenide/selenide
        $("#repository-container-header").shouldHave(text("selenide / selenide"));
        //sleep(5000);
    }

    //ARRANGE - предусловие
    //ACT - действие
    //ASSERT - проверка
    //ACT
    //ASSERT
    //ACT
    //ASSERT

}