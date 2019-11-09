import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class StopDocker {

    public void executeDockerCommand() throws IOException, InterruptedException {

        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd /c start dockerDown.bat");

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
                if (currentLine.contains("selenium-hub exited")) {
                    System.out.println("Found Text");
                    flag = true;
                    break;
                }
                currentLine = reader.readLine();
            }
            reader.close();
        }

        Assert.assertTrue(flag, "!!! Text not found. Test Failed !!!");
        File f = new File("dockerOutputLog.txt");
        if (f.delete()) {
            System.out.println("Docker output file is deleted successfully.");
        }
    }
}
