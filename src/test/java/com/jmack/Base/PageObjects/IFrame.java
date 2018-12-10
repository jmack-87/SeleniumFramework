package com.jmack.Base.PageObjects;

import com.jmack.Base.Generic;
import com.jmack.Base.ScreenShot;
import com.jmack.Base.TestBase;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class IFrame extends TestBase {

    private Generic generic;
    private ScreenShot ss;
    private String id = "unknown";
    private String testName = "unknown";

    /**
     * Minimum constructor
     * @param generic
     * @param ss
     */
    public IFrame (Generic generic, ScreenShot ss) {

        this.generic = generic;
        this.ss = ss;

    }

    /**
     * Constructor provisioned for instance logging
     * @param generic
     * @param ss
     * @param id
     * @param testName
     */
    public IFrame (Generic generic, ScreenShot ss, String id, String testName) {

        this.generic = generic;
        this.ss = ss;
        this.id = id;
        this.testName = testName;

    }

    /**
     * Switch to an iFrame from a default context
     *
     * @param wel (WebElement)
     */
    @Step("Switch to an iFrame in the current webpage")
    public void switchToIframe(WebElement wel) {
        generic.returnDriver().switchTo().frame(wel);
    }

    /**
     * Switch to an iFrame from a default context
     *
     * @param propertyKey (String) locator properties key
     */
    @Step("Switch to an iFrame in the current webpage after ensuring it exists")
    public void switchToIframe(String propertyKey) {
        WebElement identifier = generic.confirmElementExistence(propertyKey);
        generic.returnDriver().switchTo().frame(identifier);
    }
    /**
     * Switch to the parent of the current iFrame
     */
    @Step("Switch to the parent of the iFrame.")
    public void switchToParentIframe() {
        generic.returnDriver().switchTo().parentFrame();
    }

    /**
     * Switch to the main webpage from any level within a set of iFrames
     */

    @Step("Switch back to the main webpage.")
    public void switchToDefault() {
        generic.returnDriver().switchTo().defaultContent();
    }
}

