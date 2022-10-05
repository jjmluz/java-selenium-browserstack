package com.browserstack.app;

//Sample test in Java to run Automate session.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;

import com.browserstack.local.Local;

public class JavaLocalSample {
    public static final String AUTOMATE_USERNAME = System.getenv("BROWSERSTACK_USERNAME") != null ? System.getenv("BROWSERSTACK_USERNAME") : "BROWSERSTACK_USERNAME";
    public static final String AUTOMATE_ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY") != null ? System.getenv("BROWSERSTACK_ACCESS_KEY") : "BROWSERSTACK_ACCESS_KEY";
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final String LOCAL_FOLDER_PATH = "./assets";
    public static final String LOCAL_FOLDER_URL = "http://" + AUTOMATE_USERNAME + ".browserstack.com/";
    public static final String DOWNLOADS_PATH = "C:\\Users\\hello\\Downloads\\";
    public static final String FILE_FOR_FAKE_VIDEO_CAPTURE = "fake-stream.mjpeg";

    public static void main(String[] args) throws Exception {

        // Configure ChromeOptions to pass fake media stream
        ChromeOptions options = new ChromeOptions();
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");
        options.addArguments("use-file-for-fake-video-capture=" + DOWNLOADS_PATH + FILE_FOR_FAKE_VIDEO_CAPTURE);

        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("local", "true");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "100.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        caps.setCapability("bstack:options", browserstackOptions);
        caps.setCapability("sessionName", "BStack-[Java] Sample Test"); // test name
        caps.setCapability("buildName", "BStack Local Build Number 1"); // CI/CD job or build name

        // Creates an instance of Local
        Local bsLocal = new Local();

        // You can also set an environment variable - "BROWSERSTACK_ACCESS_KEY".
        HashMap<String, String> bsLocalArgs = new HashMap<String, String>();
        bsLocalArgs.put("key", AUTOMATE_ACCESS_KEY);
        bsLocalArgs.put("f", LOCAL_FOLDER_PATH);

        // Starts the Local instance with the required arguments
        bsLocal.start(bsLocalArgs);

        // Check if BrowserStack local instance is running
        System.out.println(bsLocal.isRunning());
                
        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
        
        // WebCam Test
        driver.get(LOCAL_FOLDER_URL + FILE_FOR_FAKE_VIDEO_CAPTURE);
        Thread.sleep(15000);
        driver.get("https://webcamtests.com/check");
        Thread.sleep(5000);
        driver.findElement(By.id("webcam-launcher")).click();
        Thread.sleep(2000);

        bsLocal.stop();
        driver.quit();
    }
}
