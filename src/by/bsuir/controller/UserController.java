package by.bsuir.controller;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.bsuir.dao.AccountDAO;
import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.PatientStatus;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebServlet("/usr")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private	String STUFF_REGISTRATION_KEY = "sho";
	private HttpSession session;
	private AccountDAO daoAccount = new AccountDAO();
	private final Logger logger;
       
    public UserController() {
        logger = Logger.getLogger(this.getClass());
        PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("resources/log4j.properties"));
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		String action = request.getServletPath();
		logger.info(action);
		
		switch (action) {
		case "/main":
			this.showMainPage(request, response);
			break;
		case "/login":
			this.showLogin(request, response);
			break;
		case "/sign-in":
			this.loginUser(request, response);
			break;	
		case "/register":
			this.showRegister(request, response);
			break;
		case "/sign-up":
			this.registerUser(request, response);
			break;
		}
	}
	
	protected void showMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("lang") != null && request.getParameter("lang").equals("ru"))
			session.setAttribute("lang", "ru");
		else if (request.getParameter("lang") != null && request.getParameter("lang").equals("en"))
			session.setAttribute("lang", "en");
		
		if (session.getAttribute("hash") != null) {
			request.setAttribute("accountID", daoAccount.getUserAccount((String)session.getAttribute("hash")).id);
			request.setAttribute("status", session.getAttribute("status"));
			request.setAttribute("image", session.getAttribute("image"));
		}
		
		request.setAttribute("lang", session.getAttribute("lang"));
		logger.info("lang: " + session.getAttribute("lang"));
		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
		
	protected void showLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session.removeAttribute("hash");
		session.removeAttribute("status");
		session.removeAttribute("image");
		this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	protected void showRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session.removeAttribute("hash");
		session.removeAttribute("status");
		session.removeAttribute("image");
		this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
	}
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String hash = getHash(request.getParameter("username") + request.getParameter("password"));
			UserAccount acc = daoAccount.getUserAccount(hash);
			if (acc == null) {
				request.setAttribute("error", 1);
				logger.warn("sign-in: invalid login or password");
				this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
				
			UserStatus status = acc.status;
			session.setAttribute("hash", hash);
			session.setAttribute("status", status.toString());
			session.setAttribute("image", acc.image);
			logger.info("sign-in: session filled");
			
			if (status == UserStatus.DOCTOR || status == UserStatus.NURSE)
				response.sendRedirect("doctor");
			else if (status == UserStatus.PATIENT)
				response.sendRedirect("main");
		}
		catch (NoSuchAlgorithmException e) {}
	}
	
	protected void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String hash = getHash(request.getParameter("username") + request.getParameter("password"));
			if (daoAccount.getUserAccount(hash) != null)
			{
				request.setAttribute("error", 1);
				logger.warn("sign-up: user is already registered");
				this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}
			
			String status = request.getParameter("status");
			UserAccount acc = new UserAccount(request.getParameter("username"), hash, request.getParameter("email"), status);
			String ownName = request.getParameter("own-name");
			Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthdate"));
			
			switch (status) {
			
			case "PATIENT":
				String passport = request.getParameter("passport");
				String livingPlace = request.getParameter("living-place");
				String pastIllnesses = request.getParameter("past-illnesses");
				int patientID = daoAccount.registerUser(acc);
				if (patientID != -1) {
					Patient patient = new Patient(patientID, ownName, birthDate, livingPlace);
					patient.passportNumber = passport;
					patient.pastIllnesses = pastIllnesses;
					patient.status = PatientStatus.ON_TREATMENT;
					daoAccount.registerPatient(patient);
					logger.info("sign-up: patient registered");
				}
				break;	
				
			case "NURSE":
			case "DOCTOR":
				String specialization = request.getParameter("specialization");
				String regKey = request.getParameter("regkey");
				if (regKey.equals(STUFF_REGISTRATION_KEY)) {
					int doctorID = daoAccount.registerUser(acc);
					if (doctorID != -1) {
						Doctor doctor = new Doctor(doctorID, ownName, birthDate, DoctorSpecialization.valueOf(specialization));
						daoAccount.registerDoctor(doctor);
						logger.info("sign-up: doctor registered");
					}
				}
				else {
					request.setAttribute("error", 2);
					logger.warn("sign-up: incorrect registration key");
					this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
					return;
				}
				break;
			}
			
			session.setAttribute("hash", hash);
			session.setAttribute("status", status);
			session.setAttribute("image", daoAccount.getUserAccount(hash).image);
			logger.info("sign-up: session filled");
			response.sendRedirect("main");
		}
		catch (NoSuchAlgorithmException e) {} catch (ParseException e) {
			e.printStackTrace();
		}
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
