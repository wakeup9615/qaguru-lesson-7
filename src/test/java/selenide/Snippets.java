package selenide;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Snippets {
    //РАБОТА С БРАУЗЕРОМ
    void browser_command_examples() {

        open("https://google.com");
        open("/customer/orders");    //относительный урл // -Dselenide.baseUrl=http://123.23.23.1
        open("/", AuthenticationType.BASIC,
                new BasicAuthCredentials("user", "password"));

        Selenide.back(); //нажать на кнопку назад в бразуере
        Selenide.refresh(); //зарефрешить страницу

        Selenide.clearBrowserCookies(); //удаление куки (хранятся данные аутентификации)
        Selenide.clearBrowserLocalStorage(); //удаление локал стораж
        executeJavaScript("sessionStorage.clear();"); // no Selenide command for this yet //удаление сессмон стораж

        //относятся к аллертам барузера
        Selenide.confirm(); // OK in alert dialogs
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); // close active tab закрывает текущее окно
        Selenide.closeWebDriver(); // close browser completely закрывает все что есть

        //iframe страница в странице
        Selenide.switchTo().frame("id фрейма"); //перейти во фрейм
        Selenide.switchTo().defaultContent(); //выйти из фрейма

        Selenide.switchTo().window("The Internet"); //переключение между окнами
    }

    //СЕЛЕКТОРЫ
    void selectors_examples() {
        $("div").click();
        element("div").click(); //в котлине element (тоже самое что и $)

        $("div", 2).click(); // the third div поиск эл-та в дереве который будет 3им

        // поиск по xpath
        $x("//h1/div").click();
        $(byXpath("//h1/div")).click();

        //поиск по тексту
        $(byText("full text")).click(); //поиск по полному тексту
        $(withText("ull tex")).click(); //поиск по подстроке

        //поиск текста под каким то тегом
        $(byTagAndText("div","full text"));
        $(withTagAndText("div","ull text"));

        $("").parent(); //ищет родителя
        $("").sibling(1); //братья сестра вниз ищет соседний эл-т внизу
        $("").preceding(1); //братья сестра вверх ищет соседний эл-т сверху
        $("").closest("div"); //вниз по дереву (найти в дереве следующий элемент с тегом)
        $("").ancestor("div"); // the same as closest вверх по дереву (найти в дереве следующий элемент с тегом)
        $("div:last-child"); //псевдо селекторы last-child

        $("div").$("h1").find(byText("abc")).click(); ////find синоним $

        // very optional
        //поиск по атрибуту
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();

        //поиск по id
        $(byId("mytext")).click();
        $("#mytext").click(); //!!!

        //поиск по классу
        $(byClassName("red")).click();
        $(".red").click(); //!!!
    }

    //ДЕЙСТВИЯ
    void actions_examples() {
        $("").click();
        $("").doubleClick();
        $("").contextClick(); //правый клик

        $("").hover(); //навести мышку и не нажимать

        $("").setValue("text"); //ввести текст, если был текст введн, то заменит введенный (для input)
        $("").append("text"); //добавит в конец текст
        $("").clear(); //очищает
        $("").setValue(""); // clear

        $("div").sendKeys("c"); // hotkey c on element (для НЕ input)
        //горячие клавиши
        actions().sendKeys("c").perform(); //hotkey c on whole application
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Ctrl + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f"));

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();


        // complex actions with keybord and mouse, example
        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 200).release().perform();
        //actions - режим команд, всегда заканчивается perform()
        // moveToElement - подвинуть мышку к div
        // clickAndHold - нажать на левую клавишу мыши и не отпускать
        // moveByOffset - сдвинуться вправо и вниз (отсчет от мышкы 300 вправо 200 вниз)
        // release - отпустить мышку

        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option"); //select только для select элемента
        $("").selectRadio("radio_options");

    }

    //ПРОВЕРКИ
    void assertions_examples() {
        //все should взаимозаменяемы
        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear); //что то появляется
        $("").shouldNot(appear);


        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30)); //ожидание того места который мы проверяем (в данном случае максимум 30 сек)

    }

    //УСЛОВИЯ внутри проверок
    void conditions_examples() {
        $("").shouldBe(visible); //появилось
        $("").shouldBe(hidden); //исчезло

        $("").shouldHave(text("abc"));//ищет подстроку игнорирует регистр
        $("").shouldHave(exactText("abc"));//точное сравнение игнорирует регистр
        $("").shouldHave(textCaseSensitive("abc")); ///ищет подстроку НЕ игнорирует регистр
        $("").shouldHave(exactTextCaseSensitive("abc"));//точное сравнение НЕ игнорирует регистр
        $("").should(matchText("[0-9]abc$"));//regex

        $("").shouldHave(cssClass("red"), cssClass("red")); //проверка реального цвета эл-та
        $("").shouldHave(cssValue("font-size", "12"));

        $("").shouldHave(value("25"));//проверка текста внутри поля НЕ игнорирует регистр
        $("").shouldHave(exactValue("25"));////проверка текста внутри поля игнорирует регистр
        $("").shouldBe(empty);

        //проверка атрибутов
        $("").shouldHave(attribute("disabled")); //есть или нет
        $("").shouldHave(attribute("name", "example"));//с каким то значением
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));//regex

        $("").shouldBe(checked); // for checkboxes
        $("").shouldNotBe(checked); //не отмечен


        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist); //для hidden полей, что они есть в DOM

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled); //если есть атрибут disabled
        $("").shouldBe(enabled);
    }

    //КОЛЛЕКЦИИ
    void collections_examples() {

        $$("div"); // does nothing!

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1)); //проверяем кол-во элтов коллекции
        $$("div").excludeWith(text("123")).shouldHave(size(1)); //ищет все кроме текста 123

        $$("div").first().click();  // находит все div, first() берет первый div
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click(); //последний
        $$("div").get(1).click(); // the second! (start with 0) - конкретный элемент
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first findBy тоже самое что и filterBy только вместе с first

        // assertions  проверки коллкеций
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text //проверка что элемент находится в коллекцим

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));


    }

    //ФАЙЛЫ
    void file_operation_examples() throws FileNotFoundException {
        //загрузка файла откуда то
        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid //когда кнопка сохранится в build/downloads

        //загрузка файла куда то
        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt"); //из ресурса
        // don't forget to submit!
        $("uploadButton").click();
    }

    // ЗАПУСК javascript
    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);

    }
}
