package com.engagepoint.msap.cucumber;

import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfileSteps {

    private final UserStepDefs userStepDefs = new UserStepDefs();

    @When("^open my profile$")
    public void open_my_profile() throws Throwable {
        userStepDefs.click_css_and_wait(".ch-user-account-entry__dropdown-btn");
        userStepDefs.click_xpath_and_wait(".//button/span[text()='My Profile']");
        $(By.xpath(".//h1[text()='My Profile']")).shouldBe(visible);
    }

    @When("^fill gender '(.*)', DOB mm-dd-yyyy '(.*)'-'(.*)'-'(.*)', license number '(.*)' in General Information$")
    public void fill_gender_DOB_license_number_in_General_Information(String gender, String month, String day, String year, String licenseNum) throws Throwable {
        userStepDefs.click_xpath_and_wait(".//span[text()='" + gender + "']");
        $("[placeholder='Month']").shouldBe(visible);
        userStepDefs.click_css_and_wait("[placeholder='Month']");
        $("[placeholder='Month']").setValue(month);
        userStepDefs.click_css_and_wait("[placeholder='Day']");
        $("[placeholder='Day']").setValue(day);
        userStepDefs.click_css_and_wait("[placeholder='Year']");
        $("[placeholder='Year']").setValue(year);
        $("#license").setValue(licenseNum);
    }

    @When("^fill address '(.*)', email '(.*)', telephone '(.*)' in Contact Information$")
    public void fill_address_email_telephone_in_Contact_Information(String address, String email, String telephone ) throws Throwable {
        userStepDefs.click_xpath_and_wait(".//span[text()='Contact information']");
        if (!address.isEmpty() & !address.equals(" ")) {
            $("[title='Search']").setValue(address);
            $(By.xpath(".//li/*[contains(text(),'" + address + "')]")).click();
            $("[title='Close']").click();
        }
        if (!email.isEmpty() & !email.equals(" ")) {
            userStepDefs.click_css_and_wait("#email");
            $("#email").setValue(email);
        }
        if (!telephone.isEmpty() & !telephone.equals(" ")) {
            userStepDefs.click_css_and_wait("#telephone");
            userStepDefs.click_css_and_wait("#telephone");
            $("#telephone").setValue(email);
        }
        userStepDefs.click_xpath_and_wait(".//span[text()='Contact information']");
    }

    @When("^change old password to the new one '(.*)'$")
    public void change_old_password_to_the_new_one(String newPass) throws Throwable {
        userStepDefs.click_xpath_and_wait(".//span[text()='Change password']");
        $("#newPassword").setValue(newPass);
        $("#confirmPassword").setValue(newPass);
        userStepDefs.click_css_and_wait("[ng-click='changePassword()']");
        $(By.xpath(".//strong[text()='Password changed!']")).waitUntil(appear, 4000);
        userStepDefs.click_xpath_and_wait(".//span[text()='Change password']");
    }

    @When("^save changes in profile$")
    public void save_changes_in_profile() throws Throwable {
        userStepDefs.click_css_and_wait("[value='Save Changes']");
        $(By.xpath(".//strong[text()='Settings saved!']")).waitUntil(appear, 4000);
    }

}
