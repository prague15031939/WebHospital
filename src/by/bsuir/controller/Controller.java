package by.bsuir.controller;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.bsuir.dao.DAO;
import by.bsuir.model.UserAccount;

@WebServlet("/")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession session;
	private DAO dao;

    public Controller() {
    	dao = new DAO();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		String action = request.getServletPath();
		
		switch (action) {
		case "/login":
			this.loginUser(request, response);
			break;	
		case "/main":
			this.showMain(request, response);
			break;
		case "/another":
			this.showAnother(request, response);
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String hash = getHash(request.getParameter("username") + request.getParameter("password"));
			session.setAttribute("hash", hash);
			
			response.sendRedirect("main");
		}
		catch (NoSuchAlgorithmException e) {}
	}
	
	protected void showMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount acc = dao.GetUserAccount((String)session.getAttribute("hash"));
		request.setAttribute("item", acc.StringForm());
		this.getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
	}
	
	protected void showAnother(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount acc = dao.GetUserAccount((String)session.getAttribute("hash"));
		request.setAttribute("acc", acc.StringForm());
		this.getServletContext().getRequestDispatcher("/another.jsp").forward(request, response);
	}
	
	protected String getHash(String text) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < encodedhash.length; i++) {
	        String hex = Integer.toHexString(0xff & encodedhash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

}
