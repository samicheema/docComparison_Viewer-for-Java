package com.docs.comparison;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

import com.docs.utilities.Utilities;
import com.groupdocs.comparison.Comparison;
import com.groupdocs.comparison.common.ComparisonType;
import com.groupdocs.comparison.common.comparisonsettings.PdfComparisonSettings;
import com.groupdocs.comparison.common.comparisonsettings.TextComparisonSettings;
import com.groupdocs.comparison.common.comparisonsettings.WordsComparisonSettings;

public class ComparisonGenerator {
	
	public static String compareFiles(InputStream sourceStream,InputStream targetStream,String fileName,String fileExt) {
		Comparison comparison = new Comparison();
		String Message="Failure";
		try {
			if (fileExt.equalsIgnoreCase("docx") || fileExt.contains("doc"))			
				comparison.compare(sourceStream,targetStream, outputFileName(fileName+"." + fileExt),ComparisonType.Words,new WordsComparisonSettings());			
			else if (fileExt.equalsIgnoreCase("pdf"))
				comparison.compare(sourceStream,targetStream, outputFileName(fileName+"." + fileExt),ComparisonType.Pdf, new PdfComparisonSettings());
			else if (fileExt.equalsIgnoreCase("txt") || fileExt.contains("txt"))
				comparison.compare(sourceStream,targetStream, outputFileName(fileName+"." + fileExt),ComparisonType.Text, new TextComparisonSettings());
			else if (fileExt.equalsIgnoreCase("XLS") || fileExt.contains("XLS")
							|| fileExt.equalsIgnoreCase("XLSX") || fileExt.contains("XLSX"))
				comparison.compare(sourceStream,targetStream, outputFileName(fileName+"." + fileExt),ComparisonType.Cells);
			else if (fileExt.equalsIgnoreCase("pptx") || fileExt.contains("ppt"))
				comparison.compare(sourceStream,targetStream, outputFileName(fileName+"." + fileExt),ComparisonType.Slides);
			Message = "Your file successfully compared";
		} catch (Exception e) {
			Message = e.getMessage();
		}
		return Message;
	}
	
	private static String outputFileName(String fileNameWithExt) {
		Path storagePath = Utilities.outputPath;
		String fileWithPath = storagePath+"\\"+fileNameWithExt;
		File savedFile = new File(fileWithPath);
		if (!savedFile.exists()) {
			try {
				savedFile.mkdir();
			}catch (SecurityException se) {
				
			}
		}		
		return fileWithPath;
	}	
 }