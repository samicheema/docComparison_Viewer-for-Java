package com.docs.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;


public class Utilities {

	public static final Path storagePath = getProjectBaseDir().resolve("Result/Files/");
	public static final Path tempPath = FileSystems.getDefault().getPath(System.getProperty("java.io.tmpdir"));
	public static final Path licensePath = getProjectBaseDir().resolve("E:/GroupDocs.Total.Java.lic");
	public static final Path outputHtmlPath = getProjectBaseDir().resolve("Result/ResultFiles/ComparisonDoc/");
	//Generated image files will be saved in Images folder with name starting with output_
	public static final Path outputImagePath = getProjectBaseDir().resolve("Result/ResultFiles/ComparedDocImages/");
	//Generated files will be saved in Output folder with name starting with output_
	public static final Path outputPath = getProjectBaseDir().resolve("Result/ResultFiles/ComparisonDoc/");


	public static Path getProjectBaseDir() {
		Properties props = new Properties();
		try {
			InputStream i = Utilities.class.getResourceAsStream("/project.properties");
			props.load(i);
		} catch (IOException x) {
			throw new RuntimeException(x);
		}
		return FileSystems.getDefault().getPath(props.getProperty("project.basedir"));
	}

	public static void saveAsImage(String fileName, String imageFormat, InputStream inputStream) {

		try {
			ImageIO.write(ImageIO.read(inputStream), imageFormat,
					new File(outputImagePath + "/" + getFileNameWithoutExtension(fileName) + "." + imageFormat));
		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
		}

	}

	public static void saveAsHtml(String outputFileName, String fileContent) {
		try {
			PrintWriter out = new PrintWriter(outputHtmlPath.toString() + getFileNameWithoutExtension(outputFileName) + ".html",
					"UTF-8");
			out.println(fileContent);
			out.flush();
			out.close();
		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
		}

	}

	public static void saveAsFile(String fileName, InputStream inputStream) {

		try {
			OutputStream outputStream = new FileOutputStream(outputPath + fileName);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			outputStream.flush();
		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
		}
	}

	public static void saveAsFile(String fileName, InputStream inputStream, String fileExtension) {

		try {
			OutputStream outputStream = new FileOutputStream(
					outputPath + getFileNameWithoutExtension(fileName) + fileExtension);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			outputStream.flush();
		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
		}
	}
	
	public static String getFileExtension(String fileName) {
		try {
			String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
			return tokens[tokens.length - 1];
		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
			return null;
		}
	}
	
	public static String getFileNameWithoutExtension(String fileName) {
		try {

			return fileName.replaceFirst("[.][^.]+$", "");

		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
			return null;
		}

	}

	public static ViewerConfig getConfiguration() {
		try {

			ViewerConfig config = new ViewerConfig();
			// Set storage path
			config.setStoragePath(outputPath.toString());
			config.setTempPath(tempPath.toString());
			config.setCachePath(tempPath.toString());
			config.setUseCache(true);
			config.setDefaultFontName("Calibri");
			return config;

		} catch (Exception exp) {
			System.out.println("Exception: " + exp.getMessage());
			return null;
		}
	}

}
