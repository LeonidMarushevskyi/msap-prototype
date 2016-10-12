package com.engagepoint.msap.cucumber;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.web.rest.UserResource;
import cucumber.api.Transform;
import cucumber.api.Transformer;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;


import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.engagepoint.msap.cucumber.SessionStorage.session;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class UserStepDefs {

    public String homeUrl;
    public String baseUrl;

    @Inject
    private UserResource userResource;

    private MockMvc restUserMockMvc;

	private ResultActions actions;

    @Before
    public void setup() {
        homeUrl = "http://tests.msap.engagepoint.com:3080/#/";
        //Configuration.browser = "firefox";

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

	@When("^I search user '(.*)'$")
	public void i_search_user_admin(String userId) throws Throwable {
        actions = restUserMockMvc.perform(get("/api/users/" + userId)
                .accept(MediaType.APPLICATION_JSON));
    }

	@Then("^the user is found$")
	public void the_user_is_found() throws Throwable {
		actions
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json"));
	}

	@Then("^his last name is '(.*)'$")
	public void his_last_name_is(String lastName) throws Throwable {
		actions.andExpect(jsonPath("$.lastName").value(lastName));
	}

    @When("^open home page$")
    public void open_home_page() throws Throwable {
        open(homeUrl);
        $("[ng-click='toggleLanguagePopup()']").waitUntil(appear, 30000);
    }
    @When("^open home page mobile view$")
    public void open_home_page_mobile_view() throws Throwable {
        open(homeUrl);
        getWebDriver().manage().window().setSize(new Dimension(640,1136));
        $("[ng-click='toggleLanguagePopup()']").waitUntil(appear, 30000);
    }

    @When("^close browser$")
    public void close_browser() throws Throwable {
        close();
    }

    @When("^register new user with email '(.*)', login '(.*)' and password '(.*)'$")
    public void register_new_user(@Transform(VarsConverter.class) String email, @Transform(VarsConverter.class) String login, String password) throws Throwable {
        click_css_and_wait("a[ng-click*='openRegisterModal()']");
        $("#email").setValue(email + "@yopmail.com");
        $("#login").setValue(login);
        $("[ng-model='registerAccount.firstName']").setValue(login);
        $("[ng-model='registerAccount.lastName']").setValue(login);
        $("#password").setValue(password);
        $("#confirmPassword").setValue(password);
        click_css_and_wait("button[ng-click*='register()']");
        $(By.xpath(".//*[contains(text(),'check your email')]")).waitUntil(appear, 10000);
        $(By.xpath(".//*[contains(text(),'check your email')]")).shouldBe(visible).shouldHave(text("Please check your email"));
    }

    @When("^register new foster parent with email '(.*)', login '(.*)' and password '(.*)'$")
    public void register_new_foster_parent(@Transform(VarsConverter.class) String email, @Transform(VarsConverter.class) String login, String password) throws Throwable {
        click_css_and_wait("a[ng-click*='openRegisterModal()']");
        $("#email").setValue(email + "@yopmail.com");
        click_css_and_wait("label[for='isFosterParent']");
        $("#caseNumber").shouldBe(visible);
        $("#caseNumber").setValue("12345");
        $("#login").setValue(login);
        $("[ng-model='registerAccount.firstName']").setValue(login);
        $("[ng-model='registerAccount.lastName']").setValue(login);
        $("#password").setValue(password);
        $("#confirmPassword").setValue(password);
        click_css_and_wait("button[ng-click*='register()']");
        $(By.xpath(".//*[contains(text(),'check your email')]")).waitUntil(appear, 10000);
        $(By.xpath(".//*[contains(text(),'check your email')]")).shouldBe(visible).shouldHave(text("Please check your email"));
    }

    @When("^check confirmation letter for email '(.*)'$")
    public void check_confirmation_letter_for_email(@Transform(VarsConverter.class) String email) throws Throwable {
        close();
        open("http://www.yopmail.com/en/");
        $("#login").shouldBe(visible);
        $("#login").setValue(email);
        sleep(10000);
        $(".sbut").shouldBe(visible);
        $(".sbut").click();
        sleep(2000);
        $("#inboxtit").shouldBe(visible);
        $(By.xpath(".//iframe[@id='ifinbox']")).shouldBe(visible);
        switchTo().innerFrame("ifinbox");
        $(By.xpath(".//a/span[contains(text(),'MSAP Parent Portal activation')]")).shouldBe(visible);
        $(By.xpath(".//a/span[contains(text(),'MSAP Parent Portal activation')]")).click();
        switchTo().defaultContent();
        $(By.xpath(".//iframe[@id='ifmail']")).shouldBe(visible);
        switchTo().innerFrame("ifmail");
        $("p>a").shouldBe(visible);
        $("p>a").hover();
        $("p>a").click();
        sleep(4000);
    }

    @When("^login with login '(.*)' and password '(.*)'$")
         public void login_with_login_and_password(@Transform(VarsConverter.class) String login, String password) throws Throwable {
        $("[ng-click*='openSignInModal()']").shouldBe(visible);
        click_css_and_wait("[ng-click*='openSignInModal()']");
        $(".ch-login__form").shouldBe(visible);
        $("#username").shouldBe(visible);
        $("#username").setValue(login);
        $("#password").setValue(password);
        click_xpath_and_wait(".//button[@type='submit' and text()='Sign in']");
        $(By.xpath(".//button[@type='submit' and text()='Sign in']")).shouldBe(disappear);
    }

    @When("^element '(.*)' has text '(.*)'$")
         public void element_has_text(@Transform(VarsConverter.class) String element, String text) throws Throwable {
        $(element).waitUntil(hasText(text), 10000);
    }

    @When("^change language to English$")
    public void change_language_to_english() throws Throwable {
        $("[ng-click='toggleLanguagePopup()']").shouldBe(visible);
        click_css_and_wait("[ng-click='toggleLanguagePopup()']");
        $(By.xpath(".//p[contains(text(),'English')]")).waitUntil(appear, 4000);
        click_xpath_and_wait(".//p[contains(text(),'English')]");
        $(By.xpath(".//a[text()='en']")).waitUntil(appear, 4000);
    }

    @When("^change language to Spanish$")
    public void change_language_to_spanish() throws Throwable {
        $("[ng-click='toggleLanguagePopup()']").shouldBe(visible);
        click_css_and_wait("[ng-click='toggleLanguagePopup()']");
        $(By.xpath(".//p[contains(text(),'Español')]")).waitUntil(appear, 4000);
        click_xpath_and_wait(".//p[contains(text(),'Español')]");
        $(By.xpath(".//a[text()='es']")).waitUntil(appear, 4000);
    }


    @When("^change language to Vietnamese$")
    public void change_language_to_vietnamese() throws Throwable {
        $("[ng-click='toggleLanguagePopup()']").shouldBe(visible);
        click_css_and_wait("[ng-click='toggleLanguagePopup()']");
        $(By.xpath(".//p[contains(text(),'Tiếng Việt')]")).waitUntil(appear, 4000);
        click_xpath_and_wait(".//p[contains(text(),'Tiếng Việt')]");
        $(By.xpath(".//a[text()='vi']")).waitUntil(appear, 4000);
    }

    @When("^log out$")
    public void log_out() throws Throwable {
        click_css_and_wait(".ch-user-account-entry__dropdown-btn");
        click_css_and_wait("[ng-click='logout()']");
        $(".ch-user-account-entry__dropdown-btn").shouldBe(disappear);
    }

    @Then("^tweet with text '(.*)' from '(.*)' should be presented$")
    public void tweet_with_text_sss_from_mic_should_be_presented(String text, String from) throws Throwable {
        $(By.xpath(".//iframe[contains(@id,'twitter')]")).waitUntil(appear, 10000);
        switchTo().frame(2);
        $(By.xpath(".//*[contains(@class,'timeline-TweetList-tweet')]/*/*[@class='timeline-Tweet-text' and contains(.,'" + text + "')]/../../descendant::div[@class='TweetAuthor']/a/span[text()='" + from + "']")).shouldBe(visible);
        $("[aria-label='Like']").shouldBe(visible);
        $("[aria-label='Share Tweet']").shouldBe(visible);
    }

    @Then("^video can be played$")
    public void video_can_be_played() throws Throwable {
        $(By.xpath(".//iframe[@id='youtube']")).waitUntil(appear, 4000);
        switchTo().innerFrame("youtube");
        $(By.xpath(".//*[contains(@id,'player_uid')]/div/button[contains(@class,'ytp-large-play-button')]")).shouldBe(visible);
        click_xpath_and_wait(".//*[contains(@id,'player_uid')]/div/button[contains(@class,'ytp-large-play-button')]");
        $(By.xpath(".//*[contains(@id,'player_uid')]/div/button[contains(@class,'ytp-large-play-button')]")).waitUntil(disappear, 6000);
    }

    @When("^click element '(.*)' with css and wait$")
    public void click_css_and_wait(String cssElement) throws Throwable {
        $("#loading-bar-spinner").waitUntil(disappear, 11000);
        $(cssElement).shouldBe(visible);
        $(cssElement).shouldBe(enabled);
        $(cssElement).click();
        $("#loading-bar-spinner").waitUntil(disappear, 11000);
    }

    @When("^click element '(.*)' with xpath and wait$")
    public void click_xpath_and_wait(String xpathElement) throws Throwable {
        $("#loading-bar-spinner").waitUntil(disappear, 11000);
        $(By.xpath(xpathElement)).shouldBe(visible);
        $(By.xpath(xpathElement)).shouldBe(enabled);
        $(By.xpath(xpathElement)).click();
        $("#loading-bar-spinner").waitUntil(disappear, 11000);
    }

    public static class VarsConverter extends Transformer<String> {

        public String transform(String value) {
            StrSubstitutor sub = new StrSubstitutor(session);
            return sub.replace(value);
        }
    }

}
