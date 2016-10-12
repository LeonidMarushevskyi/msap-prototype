package com.engagepoint.msap.cucumber;

import cucumber.api.Transform;
import cucumber.api.Transformer;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.engagepoint.msap.cucumber.SessionStorage.session;

public class InboxSteps {

    private final UserStepDefs userStepDefs = new UserStepDefs();

    @When("^open inbox page$")
    public void open_inbox_page() throws Throwable {
        userStepDefs.click_xpath_and_wait(".//a[@href='#/mail/inbox']");
        sleep(2000);
        $("#loading-bar-spinner").waitUntil(disappear, 15000);
        $("[href='#/mail/sent']").shouldBe(visible);
        $("[href='#/mail/drafts']").shouldBe(visible);
        $("[ui-sref='ch-inbox.new-mail']").shouldBe(visible);
    }

    @When("^compose and send new email to '(.*)' with subject '(.*)' and text '(.*)', attach file '(.*)'$")
    public void compose_and_send_new_email_with_text_attach_file(@Transform(VarsConverter.class) String recipient, @Transform(VarsConverter.class) String subject, @Transform(VarsConverter.class) String messageText, String attachFile) throws Throwable {
        userStepDefs.click_css_and_wait("[ui-sref='ch-inbox.new-mail']");
        sleep(2000);
        userStepDefs.click_css_and_wait(".ch-new-mail-field__input");
        sleep(2000);
        userStepDefs.click_xpath_and_wait(".//div[contains(@ng-click,'selectContact')]/span[text()='" + recipient + "']/..");
        $(By.xpath(".//div[contains(@ng-click,'selectContact')]/span[text()='" + recipient + "']/..")).shouldBe(disappear);
        userStepDefs.click_css_and_wait("textarea");
        $("textarea").setValue(messageText);
        userStepDefs.click_css_and_wait("[ng-model='mail.subject']");
        $("[ng-model='mail.subject']").setValue(subject);
        if (!attachFile.isEmpty() & !attachFile.equals(" ")) {
            userStepDefs.click_xpath_and_wait(".//a[contains(text(),'Attach files')]");
//todo upload file
        }
        userStepDefs.click_css_and_wait("[ng-click='sendMail()']");
        $("[ng-click='sendMail()']").shouldBe(disappear);
        userStepDefs.click_xpath_and_wait(".//*[contains(@class,'ch-alert-msg')]/*[text()='Message has been sent!']");
        $(By.xpath(".//*[contains(@class,'ch-alert-msg')]/*[text()='Message has been sent!']")).waitUntil(disappear, 4000);
    }

    @Then("^verify letter to '(.*)' with subject '(.*)' and text '(.*)' is sent$")
    public void verify_letter_is_sent(@Transform(VarsConverter.class) String recipient, @Transform(VarsConverter.class) String subject, @Transform(VarsConverter.class) String messageText) throws Throwable {
        userStepDefs.click_css_and_wait("[href='#/mail/sent']");
        $(By.xpath(".//div[contains(@ng-click,'openMail')]")).waitUntil(appear, 4000);
        userStepDefs.click_xpath_and_wait(".//div[contains(@ng-click,'openMail')]/*/span[text()='" + recipient + "']/../../*/span[text()='" + subject + "']");
        $(By.xpath(".//div[contains(@ng-click,'openMail')]/*/span[text()='" + recipient + "']/../../*/span[text()='" + subject + "']")).shouldBe(disappear);
        $(By.xpath(".//textarea[contains(text(),'" + messageText + "')]")).shouldBe(visible);
    }

    @Then("^verify unread letter from '(.*)' has subject '(.*)' and text '(.*)'$")
    public void verify_unread_letter_from_has_text(@Transform(VarsConverter.class) String sender, @Transform(VarsConverter.class) String subject, @Transform(VarsConverter.class) String messageText) throws Throwable {
        userStepDefs.click_xpath_and_wait(".//div[contains(@ng-click,'openMail')]/*/span[text()='" + sender + "']/../../*/span[text()='" + subject + "']");
        $(By.xpath(".//div[contains(@ng-click,'openMail')]/*/span[text()='" + sender + "']/../../*/span[text()='" + subject + "']")).shouldBe(disappear);
        $(By.xpath(".//textarea[contains(text(),'" + messageText + "')]")).shouldBe(visible);
    }

    @When("^reply to '(.*)' with text '(.*)'$")
    public void reply_to_with_subject_and_text(@Transform(VarsConverter.class) String recipient, String messageText) throws Throwable {
        userStepDefs.click_xpath_and_wait(".//*[@class='ch-mail-thread-list__item ng-scope']/descendant::span[contains(text(),'" + recipient + "')]/ancestor::*[@class='ch-mail-thread-list__item ng-scope']/descendant::span[text()='Reply']");
        userStepDefs.click_xpath_and_wait(".//textarea");
        $(By.xpath(".//textarea")).setValue(messageText);
        userStepDefs.click_xpath_and_wait(".//button[@ng-click='sendMail()']");
        $(By.xpath(".//button[@ng-click='sendMail()']")).waitUntil(disappear, 4000);
        userStepDefs.click_xpath_and_wait(".//*[contains(@class,'ch-alert-msg')]/*[text()='Message has been sent!']");
        $(By.xpath(".//*[contains(@class,'ch-alert-msg')]/*[text()='Message has been sent!']")).waitUntil(disappear, 4000);
    }

    @Then("^verify letter in thread from '(.*)' has text '(.*)'$")
    public void verify_letter_in_thread_from_has_text(@Transform(VarsConverter.class) String sender, String messageText) throws Throwable {
        $(By.xpath(".//textarea[contains(text(),'" + messageText + "')]")).shouldBe(visible);
        $(By.xpath(".//*[@class='ch-mail-thread-list__item ng-scope']/descendant::span[contains(text(),'" + sender + "')]/ancestor::*[@class='ch-mail-thread-list__item ng-scope']/descendant::textarea[contains(text(),'" + messageText + "')]")).shouldBe(visible);
    }

    public static class VarsConverter extends Transformer<String> {

        public String transform(String value) {
            StrSubstitutor sub = new StrSubstitutor(session);
            return sub.replace(value);
        }
    }
}
