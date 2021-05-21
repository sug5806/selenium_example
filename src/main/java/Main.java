import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.List;


public class Main {
    private static WebDriver webDriver;
    private static JavascriptExecutor js;
    private static String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private static String WEB_DRIVER_PATH = "chromedriver";
    private static String URL = "https://www.youtube.com/channel/UCewitUbsXnyjvJjGgxa0IYw/videos";

    public static void setUp() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("disable-gpu");
        chromeOptions.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        chromeOptions.addArguments("lang=ko_KR");


        webDriver = new ChromeDriver(chromeOptions);

        webDriver.manage().window().maximize();

        js = (JavascriptExecutor) webDriver;

    }

    public static void setDown() {
        webDriver.quit();
    }

    public static void main(String[] args) throws IOException {
        setUp();

        js.executeScript("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},})");
        js.executeScript("Object.defineProperty(navigator, 'languages', {get: function() {return ['ko-KR', 'ko']}})");
        js.executeScript("const getParameter = WebGLRenderingContext.getParameter;WebGLRenderingContext.prototype.getParameter = function(parameter) {if (parameter === 37445) {return 'NVIDIA Corporation'} if (parameter === 37446) {return 'NVIDIA GeForce GTX 980 Ti OpenGL Engine';}return getParameter(parameter);};");

        webDriver.get(URL);

        try {
            long lastHeight = (long) js.executeScript("return document.documentElement.scrollHeight");

            while (true) {
                js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
                Thread.sleep(1000);

                long newHeight = (long) js.executeScript("return document.documentElement.scrollHeight");

                System.out.println(lastHeight);
                System.out.println(newHeight);

                if (newHeight == lastHeight) {
                    break;
                }

                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> elements = webDriver.findElements(By.cssSelector("#contents #items .ytd-grid-renderer #dismissible #meta h3 #video-title"));

        for (WebElement element : elements) {
            String text = element.getText();
            String href = element.getAttribute("href");
            System.out.println(text + "   " + href);
        }

        setDown();
    }
}

