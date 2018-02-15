package org.mozilla.benchmark.constants;

import org.openqa.selenium.By;

/**
 * Created by Silviu on 14/02/2018.
 */
public final class WebPageConstants {

    public static String HOME_PAGE_URL = "about:home";
    public static By HOME_PAGE_PATTERN = By.xpath("//h3[contains(@class, 'section-title')]");

    //Gmail constants
    public static final String GMAIL_YOUTUBE_LINK = "http://benjamin.smedbergs.us/tests/youtube-notification-mail/youtube-notification-mail-complete.html";
    public static final String GMAIL_URL = "https://mail.google.com/mail/";

    //Facebook constants
    public static final String FACEBOOK_URL = "https://www.facebook.com";
    //credentials
    public static final String FACEBOOK_USER_NAME = "benchmarkautomation_1@yahoo.com";
    public static final String FACEBOOK_PASS = "Softvision10";

    //GoogleSearch constants
    public static final String GSEARCH_URL = "https://google.com";
    public static final String SEARCH_ITEM = "barack obama";

    //Youtube constants
    public static final String YOUTUBE_ITEM = "sarah jeffery recorder";
    public static final String YOUTUBE_URL = "https://www.youtube.com";

    //Amazon constants
    public static final String AMAZON_URL = "https://www.amazon.com";
    public static final String AMAZON_SEARCH_ITEM = "lord of the rings";

    private WebPageConstants() {
    }
}
