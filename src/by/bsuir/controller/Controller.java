package by.bsuir.controller;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	enum OpenPrescriptionMode {
		VIEW, EXECUTE
	}
	
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
		case "/doctor":
			this.showDoctor(request, response);
			break;
		case "/doctor-prescribe":
			this.showCreator(request, response);
			break;
		case "/prescribe":
			this.doPrescription(request, response);
			break;
		case "/patient":
			this.showPatient(request, response);
			break;
		case "/prescription":
			this.showPrescription(request, response, OpenPrescriptionMode.VIEW);
			break;
		case "/execute-prescription":
			this.showPrescription(request, response, OpenPrescriptionMode.EXECUTE);
			break;
		case "/execute":
			this.doExecution(request, response);
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
		if (session.getAttribute("hash") != null) {
			request.setAttribute("accountID", dao.GetUserAccount((String)session.getAttribute("hash")).id);
			request.setAttribute("status", session.getAttribute("status"));
		}
		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	protected void showDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount accDoctor = dao.GetUserAccount((String)session.getAttribute("hash"));
		Doctor doctor = dao.GetDoctor(accDoctor.id);
		
		request.setAttribute("accountID", accDoctor.id);
		request.setAttribute("status", session.getAttribute("status"));
		request.setAttribute("doctorSpecialization", doctor.specialization.toString().toLowerCase().replaceAll("_", " "));
		request.setAttribute("doctorImage", accDoctor.image);
		request.setAttribute("doctorEmail", accDoctor.email);
		request.setAttribute("doctor", doctor);
		
		ArrayList<Patient> patients = dao.GetDoctorsPatients(accDoctor.id);
		request.setAttribute("patientList", patients);
		this.getServletContext().getRequestDispatcher("/doctor.jsp").forward(request, response);
	}
	
	protected void showCreator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int patientID = Integer.parseInt(request.getParameter("id"));
		Patient patient = dao.GetPatient(patientID);
		UserAccount accPatient = dao.GetUserAccount(patientID);
		Doctor doctor = dao.GetDoctor(dao.GetUserAccount((String)session.getAttribute("hash")).id);
		
		request.setAttribute("accountID", doctor.id);
		request.setAttribute("status", session.getAttribute("status"));
		request.setAttribute("patient", patient);
		request.setAttribute("patientImage", accPatient.image);
		request.setAttribute("patientEmail", accPatient.email);
		
		ArrayList<String[]> procedures = dao.GetProcedures(doctor.specialization);
		request.setAttribute("procedures", procedures);
		ArrayList<String[]> manipulations = dao.GetManipulations(doctor.specialization);
		request.setAttribute("manipulations", manipulations);
		
		this.getServletContext().getRequestDispatcher("/doctor-prescribe.jsp").forward(request, response);
	}
	
	protected void showPatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("status", session.getAttribute("status"));
		if (request.getParameter("id") == null) {
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		int patientID = Integer.parseInt(request.getParameter("id"));
		UserAccount accPatient = dao.GetUserAccount(patientID);
		request.setAttribute("accountID", dao.GetUserAccount((String)session.getAttribute("hash")).id);
		request.setAttribute("patientImage", accPatient.image);
		request.setAttribute("patientEmail", accPatient.email);
		
		Patient patient = dao.GetPatient(patientID);
		request.setAttribute("patient", patient);
		
		ArrayList<Prescription> prescriptions = dao.GetPatientsPrescriptions(patientID);
		request.setAttribute("prescriptionsList", prescriptions);
		
		this.getServletContext().getRequestDispatcher("/patient.jsp").forward(request, response);
	}
	
	protected void showPrescription(HttpServletRequest request, HttpServletResponse response, OpenPrescriptionMode mode) throws ServletException, IOException {
		int prescriptionID = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("accountID", dao.GetUserAccount((String)session.getAttribute("hash")).id);
		request.setAttribute("status", session.getAttribute("status"));
		
		Prescription item = dao.GetPrescription(prescriptionID);
		
		UserAccount accPatient = dao.GetUserAccount(item.patientID);
		Patient patient = dao.GetPatient(item.patientID);
		UserAccount accDoctor = dao.GetUserAccount(item.doctorID);
		Doctor doctor = dao.GetDoctor(item.doctorID);
		
		request.setAttribute("patientInfo", patient.name + " " + patient.passportNumber + " " + patient.birthDate + " " + accPatient.email);
		request.setAttribute("doctorInfo", doctor.getQuickInfo() + " " + accDoctor.email);
		request.setAttribute("timestamp", item.getStringTime());
		
		request.setAttribute("prescriptionID", prescriptionID);
		request.setAttribute("diagnosis", item.diagnosis);
		request.setAttribute("medicines", item.medicines);
		request.setAttribute("procedures", item.procedures);
		request.setAttribute("manipulations", item.manipulations);
		
		if (mode == OpenPrescriptionMode.VIEW)
			this.getServletContext().getRequestDispatcher("/prescription.jsp").forward(request, response);
		else if (mode == OpenPrescriptionMode.EXECUTE)
			this.getServletContext().getRequestDispatcher("/execute-prescription.jsp").forward(request, response);
	}
	
	protected void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String hash = getHash(request.getParameter("username") + request.getParameter("password"));
			session.setAttribute("hash", hash);
			UserStatus status = dao.GetUserAccount(hash).status;
			session.setAttribute("status", status.toString());
			
			if (status == UserStatus.DOCTOR)
				response.sendRedirect("doctor");
			else if (status == UserStatus.PATIENT)
				response.sendRedirect("main");
		}
		catch (NoSuchAlgorithmException e) {}
	}
	
	protected void doPrescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Prescription prscr = new Prescription(  
			request.getParameter("diagnosis"), 
			request.getParameter("medicines").split(";"), 
			request.getParameterValues("proc-box"),
			request.getParameterValues("manipulation-box")
		);
		
		int patientID = Integer.parseInt(request.getParameter("id"));
		Patient patient = dao.GetPatient(patientID);
		UserAccount doctor = dao.GetUserAccount((String)session.getAttribute("hash"));
		prscr.timestamp = new Date();
		int prescriptionID = dao.doPrescription(prscr, doctor.id, patient.id);
		
		if (prescriptionID != -1)
			response.sendRedirect("prescription?id=" + String.valueOf(prescriptionID));
	}
	
	protected void doExecution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int prescriptionID = Integer.parseInt(request.getParameter("id"));
		String[] medicines = request.getParameterValues("medicine-box");
		String[] procedures = request.getParameterValues("proc-box");
		String[] manipulations = request.getParameterValues("manipulation-box");
		
		String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());		
		Doctor doctor = dao.GetDoctor(dao.GetUserAccount((String)session.getAttribute("hash")).id);
		Prescription prescription = dao.GetPrescription(prescriptionID);

		if (procedures != null) prescription.procedures = modifyPrescriptionElement(procedures, prescription.procedures, doctor, timestamp);
		if (medicines != null) prescription.medicines = modifyPrescriptionElement(medicines, prescription.medicines, doctor, timestamp);
		if (manipulations != null) prescription.manipulations = modifyPrescriptionElement(manipulations, prescription.manipulations, doctor, timestamp);

		dao.doExecution(prescription);
		
		if (prescriptionID != -1)
			response.sendRedirect("prescription?id=" + String.valueOf(prescriptionID));
	}
	
	protected String[] modifyPrescriptionElement(String[] source, String[] target, Doctor doctor, String timestamp) {
		for (int i = 0; i < source.length; i++) {
			for (int j = 0; j < target.length; j++) {
				if (!target[j].trim().startsWith("@") && target[j].equals(source[i])) {
					target[j] = String.format("@%s!?%s  %s", source[i], doctor.name, timestamp);
					break;
				}
			}
		}
		
		return target;
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
