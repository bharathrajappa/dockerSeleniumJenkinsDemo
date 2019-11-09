import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class StartDocker {

    public void executeDockerCommand() throws IOException, InterruptedException {

        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c start dockerUp.bat");

        String fileUrl = "dockerOutputLog.txt";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 45);
        long stopNow = calendar.getTimeInMillis();
        Thread.sleep(3000);

        boolean flag = false;
        while (System.currentTimeMillis() < stopNow) {
            if (flag) {
                break;
            }

            BufferedReader reader = new BufferedReader(new FileReader(fileUrl));
            String currentLine = reader.readLine();

            while (currentLine != null && !flag) {
                if (currentLine.contains("The node is registered to the hub and ready to use")) {
                    System.out.println("Found Text");
                    flag = true;
                    break;
                }
                currentLine = reader.readLine();
            }
            reader.close();
        }

        Assert.assertTrue(flag, "!!! Text not found. Test Failed !!!");
        runtime.exec("cmd /c start scaleChrome.bat");
        Thread.sleep(15000);

    }
}
