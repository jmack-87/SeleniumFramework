package com.ibm.ciclan.Base.PageObjects;

import com.ibm.ciclan.Base.Generic;
import com.ibm.ciclan.Base.ScreenShot;
import com.ibm.ciclan.Base.TestBase;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class IFramePO extends TestBase {

    private Generic generic;
    private ScreenShot ss;
    private String id = "unknown";
    private String testName = "unknown";

    /**
     * Minimum constructor
     * @param generic
     * @param ss
     */
    public IFramePO(Generic generic, ScreenShot ss) {

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
    public IFramePO(Generic generic, ScreenShot ss, String id, String testName) {

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

