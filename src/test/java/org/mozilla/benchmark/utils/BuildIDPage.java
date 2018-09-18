package org.mozilla.benchmark.utils;

import org.mozilla.benchmark.constants.WebPageConstants;
import org.mozilla.benchmark.objects.PageNavigationTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BuildIDPage extends BasePage {

    public BuildIDPage(int runs, String testName, PageNavigationTypes navigationType) {
        super(runs, testName, navigationType);
    }

    public String getBuildID(){
        navigateToAbout();
        WebElement locatorElement = getElement(By.id("buildid-box"));
        return locatorElement.getText();
    }

    public void runAllScenarios() {
        getBuildID();
    }
}
