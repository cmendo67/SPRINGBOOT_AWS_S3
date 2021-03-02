package org.jcg.springboot.aws.s3.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utility {
	static String displayTextValue = "";
	public static String displayText(InputStream input) throws IOException{
		// Read one text line at a time and display.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            displayTextValue += line + "\n";
            System.out.println("    " + line);
//            System.out.println(displayTextValue);
        }
        return displayTextValue;
	}
	//getter
	public static String getdisplayTextValue() {
		System.out.println(displayTextValue);
		return displayTextValue;
	}
}
