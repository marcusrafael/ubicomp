package org.ubicomp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CSVFile {
	
	public static int contador = 0;
	
	public static void escrevaTemperature(String line) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream( new File("public/temperature.csv"), true )); 
        pw.write(line + " , " + contador++ + "\n");
        pw.close();
    }
	
	public static void escrevaHumidity(String line) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream( new File("public/humidity.csv"), true )); 
        pw.write(line + " , " + contador++ + "\n");
        pw.close();
    }
	
	public static void escrevaLuminosity(String line) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream( new File("public/luminosity.csv"), true )); 
        pw.write(line + " , " + contador++ + "\n");
        pw.close();
    }
	
	
	
	
}
