package by.bsuir.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		String url = request.getRequestURL().toString();
		String relativeImagePath = url.replaceFirst(".*/image/", "");
		String appPath = request.getServletContext().getRealPath("WEB-INF/images/");
		String absoluteImagePath = appPath + relativeImagePath;
		
		File f = new File(absoluteImagePath);
		if (f.exists()) {
			FileInputStream fileStream = new FileInputStream(absoluteImagePath);
		    byte[] buffer = new byte[fileStream.available()];
		    
		    fileStream.read(buffer, 0, fileStream.available());
		    
		    response.getOutputStream().write(buffer);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();  
		    
		    fileStream.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
