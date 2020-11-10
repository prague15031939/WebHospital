package by.bsuir.controller;

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

@WebServlet("/usr")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private	String STUFF_REGISTRATION_KEY = "sho";
	private HttpSession session;
	private AccountDAO daoAccount = new AccountDAO();
       
    public UserController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		String action = request.getServletPath();
		
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
		if (session.getAttribute("hash") != null && session.getAttribute("status") != null) {
			request.setAttribute("accountID", daoAccount.GetUserAccount((String)session.getAttribute("hash")).id);
			request.setAttribute("status", session.getAttribute("status"));
			request.setAttribute("image", session.getAttribute("image"));
		}
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
			UserAccount acc = daoAccount.GetUserAccount(hash);
			if (acc == null) {
				request.setAttribute("error", "incorrect username or password");
				this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
				
			UserStatus status = acc.status;
			session.setAttribute("hash", hash);
			session.setAttribute("status", status.toString());
			session.setAttribute("image", acc.image);
			
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
			if (daoAccount.GetUserAccount(hash) != null)
			{
				request.setAttribute("error", "user is already registered");
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
					}
				}
				else {
					request.setAttribute("error", "invalid registration key");
					this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
					return;
				}
				break;
			}
			
			session.setAttribute("hash", hash);
			session.setAttribute("status", status);
			session.setAttribute("image", daoAccount.GetUserAccount(hash).image);
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
