package com.engagepoint.msap.cucumber;

import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import javax.inject.Inject;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.engagepoint.msap.cucumber.SessionStorage.session;

public class AdminPortalSteps {

    private final UserStepDefs userStepDefs = new UserStepDefs();

    @When("^open and verify Metrics page$")
    public void open_and_verify_Metrics_page() throws Throwable {
        $("a[href='#/metrics']").hover();
        userStepDefs.click_css_and_wait("a[href='#/metrics']");
        userStepDefs.click_xpath_and_wait(".//h2[text()='Application Metrics']");
        $(By.xpath(".//*[text()='JVM Metrics']")).shouldBe(visible);
        $(By.xpath(".//*[text()='Garbage collections']")).shouldBe(visible);
        $(By.xpath(".//*[text()='HTTP requests (events per second)']")).shouldBe(visible);
        $(By.xpath(".//*[text()='Active requests:']")).shouldBe(visible);
        $(By.xpath(".//th[text()='Code']/../th[text()='Count']/../th[text()='Mean']/../th[contains(.,'Average')]/..")).shouldBe(visible);
    }

    @When("^open and verify Users page$")
    public void open_and_verify_Users_page() throws Throwable {
        $("a[href='#/user-management']").hover();
        userStepDefs.click_css_and_wait("a[href='#/user-management']");
        $(By.xpath(".//*[text()='Create a new user']")).shouldBe(visible);
        $(By.xpath(".//*[contains(@ui-sref,'user-management.delete')]")).shouldBe(visible);
        $(By.xpath(".//*[contains(@ui-sref,'user-management.edit')]")).shouldBe(visible);
        $(By.xpath(".//th[text()='ID']/../th[text()='Login']/../th[text()='Language']/../th[text()='Profiles']/..")).shouldBe(visible);
    }

    @When("^open and verify Tracker page$")
    public void open_and_verify_Tracker_page() throws Throwable {
        $("a[href='#/tracker']").hover();
        userStepDefs.click_css_and_wait("a[href='#/tracker']");
        userStepDefs.click_xpath_and_wait(".//h2[text()='Real-time user activities']");
        $(By.xpath(".//th[text()='User']/../th[text()='IP Address']/../th[text()='Current page']/../th[text()='Time']/..")).shouldBe(visible);
    }

    @When("^open and verify Health page$")
    public void open_and_verify_Health_page() throws Throwable {
        $("a[href='#/health']").hover();
        userStepDefs.click_css_and_wait("a[href='#/health']");
        $(By.xpath(".//h2[text()='Health checks']")).shouldBe(visible);
        userStepDefs.click_xpath_and_wait(".//h2[text()='Health checks']");
        $(By.xpath(".//th[text()='Service name']/../th[text()='Status']/../th[text()='Details']/..")).shouldBe(visible);
    }

    @When("^open and verify Configuration page$")
    public void open_and_verify_Configuration_page() throws Throwable {
        $("a[href='#/configuration']").hover();
        userStepDefs.click_css_and_wait("a[href='#/configuration']");
        userStepDefs.click_xpath_and_wait(".//h2[text()='Configuration']");
        $(By.xpath(".//th[contains(.,'Prefix')]/../th[contains(.,'Properties')]/..")).shouldBe(visible);
    }

    @When("^open and verify Audit page$")
    public void open_and_verify_Audit_page() throws Throwable {
        $("a[href='#/audits']").hover();
        userStepDefs.click_css_and_wait("a[href='#/audits']");
        userStepDefs.click_xpath_and_wait(".//h2[text()='Audits']");
        $(By.xpath(".//th[contains(.,'Date')]/../th[contains(.,'User')]/../th[contains(.,'State')]/../th[contains(.,'Extra data')]/..")).shouldBe(visible);
    }

    @When("^open and verify Logs page$")
    public void open_and_verify_Logs_page() throws Throwable {
        $("a[href='#/logs']").hover();
        userStepDefs.click_css_and_wait("a[href='#/logs']");
        userStepDefs.click_xpath_and_wait(".//h2[text()='Logs']");
        $(By.xpath(".//th[contains(.,'Name')]/../th[contains(.,'Level')]/..")).shouldBe(visible);
    }

}
