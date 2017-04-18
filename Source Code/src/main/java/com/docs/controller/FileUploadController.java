package com.docs.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.docs.comparison.ComparisonGenerator;
import com.docs.utilities.Utilities;
import com.docs.viewer.ViewGenerator;


@Controller
public class FileUploadController {
	
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public ModelAndView uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) throws Exception {
		
		String fileA = "";
		String fileB = "";
		String fileExt = "";
		String fileAExt = "";
		String fileBExt = "";
		
		String Message = "Failure";
		Map<String,String> result = new HashMap<String,String>();
		
		if (files.length == 2){
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String name = file.getOriginalFilename();
				try {
					byte[] bytes = file.getBytes();
					// Creating the directory to store file
					File dir = new File(Utilities.storagePath.toString());
					if (!dir.exists())
						dir.mkdirs();
					// Create the file on server
					File serverFile = new File(dir.getCanonicalPath()+ File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
					Message = "You successfully uploaded file="+name;					
					String itemName = file.getOriginalFilename();					
					if (itemName.indexOf(".") > 0)
						fileExt = itemName.substring(
								itemName.lastIndexOf(".") + 1,
								itemName.length());
					if (i==0){
						fileA = serverFile.getAbsolutePath();
						fileAExt = fileExt;
					}else{
						fileB = serverFile.getAbsolutePath();
						fileBExt = fileExt;
					}

				} catch (Exception e) {
					Message="You failed to upload " + name + " ,Exception: "+ e.getMessage();
				}
			}
			if(fileAExt!="" && (fileAExt.equalsIgnoreCase(fileBExt) || fileAExt.contains(fileBExt))){			
				InputStream sourceStream = new FileInputStream(fileA);
				InputStream targetStream = new FileInputStream(fileB);
				int NoOfPages = 0;
				String fileName = "ComparedFile"+System.currentTimeMillis();
				Message = ComparisonGenerator.compareFiles(sourceStream,targetStream,fileName,fileExt);				
				if(Message.contains("successfully compared")){
					Message += "in following path="+Utilities.outputPath.toString();
					NoOfPages=ViewGenerator.renderDocumentAsImage(fileName+"." + fileExt);			
					result.put("compFile",fileName+"." + fileExt);
					result.put("fileName",""+fileName+".png");
					result.put("NoOfPages",""+NoOfPages);
				}
			}else
				Message += "Files extention are not match";
		}else{
			Message="File(s) are missing";
		}
		result.put("Message",Message);
		return new ModelAndView("../../index", "Result",result);	
	}
}
