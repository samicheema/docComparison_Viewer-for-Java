package com.docs.viewer;

import java.util.ArrayList;
import java.util.List;

import com.docs.utilities.Utilities;
import com.groupdocs.viewer.config.ViewerConfig;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;

public class ViewGenerator {
	
	public static int renderDocumentAsImage(String fileName) {
		List<PageImage> pages = new ArrayList<PageImage>();
		try {
			ViewerConfig config = Utilities.getConfiguration();
			ViewerImageHandler imageHandler = new ViewerImageHandler(config);
			pages = imageHandler.getPages(fileName);
			System.out.println("No. Of Pages:"+(pages.size()+1));
			for (PageImage page : pages) {
				Utilities.saveAsImage(page.getPageNumber() + "_" + fileName, "png", page.getStream());
			}
		} catch (Exception exp) {
			System.out.println("Exception in ViewGenerator: " + exp.getMessage());
			exp.printStackTrace();
		}
		return pages.size();
	}
 }