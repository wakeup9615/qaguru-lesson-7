package github;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ContributorsTest {

    @Test
    void solntsevShouldBeFirstContributor(){
        // открыть страницу репозитория
        Configuration.browserSize = "800x400";
        open("https://github.com/selenide/selenide");
        // навести на аватар первого контрибьютера
        $(".Layout-sidebar").$$("h2").filterBy(text("Contributors"))
                .first()
                .sibling(0).$$("li").first().hover();
        //.parent().$$("ul li").first().hover();
        //проверить, что на первом попапе отображается Андрей Солнцев
        $$(".Popover-message").findBy(visible).shouldHave(text("Andrei Solntsev")); //если 2 эл-та Popover-message то взять тот что видитмый можно
        //$(".Popover-message").shouldHave(text("Andrei Solntsuev"));
    }
}
