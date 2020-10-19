package by.bsuir.controller;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.bsuir.dao.DAO;
import by.bsuir.model.Doctor;
import by.bsuir.model.Patient;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

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
			this.showLogin(request, response);
			break;
		case "/sign-in":
			this.loginUser(request, response);
			break;	
		case "/main":
			this.showMainPage(request, response);
			break;
		case "/patients":
			this.showPatients(request, response);
			break;
		case "/doctor-prescribe":
			this.showCreator(request, response);
			break;
		case "/prescribe":
			this.doPrescription(request, response);
			break;
		case "/prescriptions":
			this.showPrescriptions(request, response);
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void showLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session.removeAttribute("hash");
		this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	protected void showMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("status", session.getAttribute("status"));
		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	protected void showPatients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount accDoctor = dao.GetUserAccount((String)session.getAttribute("hash"));
		Doctor doctor = dao.GetDoctor(accDoctor.id);
		
		request.setAttribute("status", session.getAttribute("status"));
		request.setAttribute("doctorSpecialization", doctor.specialization.toString().toLowerCase().replaceAll("_", " "));
		request.setAttribute("doctorImage", accDoctor.image);
		request.setAttribute("doctorEmail", accDoctor.email);
		request.setAttribute("doctor", doctor);
		
		ArrayList<Patient> patients = dao.GetDoctorsPatients(accDoctor.id);
		request.setAttribute("patientList", patients);
		this.getServletContext().getRequestDispatcher("/patients.jsp").forward(request, response);
	}
	
	protected void showCreator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int patientID = Integer.parseInt(request.getParameter("id"));
		Patient patient = dao.GetPatient(patientID);
		UserAccount accPatient = dao.GetUserAccount(patientID);
		
		request.setAttribute("status", session.getAttribute("status"));
		request.setAttribute("patient", patient);
		request.setAttribute("patientImage", accPatient.image);
		request.setAttribute("patientEmail", accPatient.email);
		
		String[][] procedures = {{"first", "second", "third"}, {"fourth", "fifth", "sixth"}, {"seventh", "eighths", "ninth"}};
		String[][] manipulations = {{"foo", "bar", "nop"}, {"gac", "rze", "pax"}};
		request.setAttribute("procedures", procedures);
		request.setAttribute("manipulations", manipulations);
		
		this.getServletContext().getRequestDispatcher("/doctor-prescribe.jsp").forward(request, response);
	}
	
	protected void showPrescriptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("status", session.getAttribute("status"));
		if (request.getParameter("id") == null) {
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
	
		int patientID = Integer.parseInt(request.getParameter("id"));
		UserAccount accPatient = dao.GetUserAccount(patientID);
		request.setAttribute("patientImage", accPatient.image);
		request.setAttribute("patientEmail", accPatient.email);
		
		Patient patient = dao.GetPatient(patientID);
		request.setAttribute("patient", patient);
		
		ArrayList<Prescription> prescriptions = dao.GetPatientsPrescriptions(patientID);
		request.setAttribute("prescriptionsList", prescriptions);
		
		this.getServletContext().getRequestDispatcher("/prescriptions.jsp").forward(request, response);
	}
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String hash = getHash(request.getParameter("username") + request.getParameter("password"));
			session.setAttribute("hash", hash);
			UserStatus status = dao.GetUserAccount(hash).status;
			session.setAttribute("status", status.toString());
			
			if (status == UserStatus.DOCTOR)
				response.sendRedirect("patients");
			else if (status == UserStatus.PATIENT)
				response.sendRedirect("prescriptions");
		}
		catch (NoSuchAlgorithmException e) {}
	}
	
	protected void doPrescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Prescription prscr = new Prescription(  
			request.getParameter("diagnosis"), 
			request.getParameter("medicines"), 
			request.getParameterValues("proc-box"),
			request.getParameterValues("manipulation-box")
		);
		
		int patientID = Integer.parseInt(request.getParameter("id"));
		Patient patient = dao.GetPatient(patientID);
		UserAccount doctor = dao.GetUserAccount((String)session.getAttribute("hash"));
		dao.doPrescription(prscr, doctor.id, patient.id);
		
		response.sendRedirect("patients");
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
