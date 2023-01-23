/**
 * Main Program where screenshots are taken.
 */

package screencast;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
	public static final long serialVersionUID = 1L;
	
	//counter to keep the count of the screenshots
	private static int counter = 0;
	
	private void incrementCounter() {
		counter++;
	}
	
	public void takeScreenshot(String path)
	{
		try {
			Thread.sleep(20);
			Robot r = new Robot();
			String format = "jpg";
			incrementCounter();
			
			// specifying the absolute path for the screenshots
			String filePath = path + "/shot" + counter + "." + format;

			// Used to get ScreenSize and capture image
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage Image = r.createScreenCapture(capture);
			
			//Saving the image in JPEG format in the specified Path.
			ImageIO.write(Image, "jpg", new File(filePath));
			
			System.out.println("Screenshot shot" + counter + " saved");
		}
		catch (AWTException | IOException | InterruptedException ex) {
			System.out.println(ex);
		}
	}
}

