package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeEach
    void beforeEachTest() {
        open("http://localhost:9999");
    }
    @Test   // №1
    public void testCard() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now() .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(verificationDate);
        $("[data-test-id=name] input").setValue("Жан-Поль Иванов");
        $("[data-test-id=phone] input").setValue("+12345678901");
        $("[data-test-id=agreement]").click();
        $x("//*[text()=\"Забронировать\"]").click();
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate),
                        Duration.ofSeconds(15));
    }
    @Test   //№2
    public void testCards() {
        $("[data-test-id=city] input").setValue("Ан");
        $(Selectors.byText("Москва")).click();
        $("[data-test-id=date] input").click();
        int days = 4;
        for ( int cycle = 0; cycle < days; cycle++) {
            $(".calendar").sendKeys(Keys.ARROW_RIGHT);
        }
        $(".calendar").sendKeys(Keys.ENTER);
        $("[data-test-id=name] input").setValue("Жан-Поль Иванов");
        $("[data-test-id=phone] input").setValue("+12345678901");
        $("[data-test-id=agreement]").click();
        $(".button").shouldHave(Condition.text("Забронировать")).click();
        String verificationDate = LocalDate.now().plusDays(7)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate),
                        Duration.ofSeconds(15));
    }
}