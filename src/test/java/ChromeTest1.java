import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChromeTest1 {
    @BeforeTest
    public void scaleDocker() throws IOException, InterruptedException {

        File f = new File("dockerOutputLog.txt");
        if (f.delete()) {
            System.out.println("Docker output file is deleted successfully.");
        }
        StartDocker s = new StartDocker();
        s.executeDockerCommand();
    }

    @Test
    public void test1() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        URL url = new URL("http://localhost:4444/wd/hub");
        RemoteWebDriver driver = new RemoteWebDriver(url, cap);
        driver.get("http://www.google.com");
        System.out.println("Title of the page: "+driver.getTitle());
    }

    @AfterTest
    public void stopDockerDeleteFile() throws IOException, InterruptedException {
        StopDocker sd = new StopDocker();
        sd.executeDockerCommand();
    }

}
