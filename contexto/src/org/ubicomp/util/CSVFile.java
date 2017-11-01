package org.ubicomp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CSVFile {
	
	public static void escreva(String line) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new FileOutputStream( new File("test.csv"), true )); 
        pw.write(line);
        pw.close();
    }
	
	public static void criarArquivo(String line) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        pw.write(line);
        pw.close();
    }
	
}
