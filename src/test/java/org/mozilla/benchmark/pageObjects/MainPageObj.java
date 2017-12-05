package org.mozilla.benchmark.pageObjects;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.IOException;

/**
 * Created by andrei.filip on 10/31/2017.
 */
public class MainPageObj {
    WebDriver driver;

    /*    @Before
        public void setUp() throws InterruptedException, AWTException, IOException {


              @After
            public void tearDown() {
                driver.quit();

        }*/
/*
    @Test
    public void testsFacebook() throws InterruptedException {

        FacebookPage facebookPage = new FacebookPage(driver1);
        facebookPage.LoginFacebook();
        facebookPage.accessGroup();
        facebookPage.goHome();
        facebookPage.accessUser();
    }

    @Test
    public void testsGmail() throws InterruptedException, MalformedURLException, AWTException {

        GmailPage gmail = new GmailPage(this.driver1);
        gmail.loginGmail();
        gmail.accessEmail();
        gmail.accessYoutubeLink();
    }



    @Test
    public void testYoutube() throws InterruptedException {
        YoutubePage youtube = new YoutubePage(driver1);
        youtube.runAllScenarios();
    }

    @Test
    public void testAmazon() throws InterruptedException {
        AmazonPage amazon = new AmazonPage(driver1);
        amazon.runAllScenarios();
    }

*/
    @Test()
    public void testGsearch() throws IOException, AWTException, InterruptedException {

/*      VideoCapture ca=new VideoCapture("20","20");
      ca.runVideo("runVideo");*/
        Thread a = new GooglePage(2);
        a.start();


    }


}

