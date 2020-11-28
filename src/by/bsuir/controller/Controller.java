package by.bsuir.controller;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.bsuir.dao.AccountDAO;
import by.bsuir.dao.DoctorDAO;
import by.bsuir.dao.PrescriptionDAO;
import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.PatientStatus;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

@WebServlet("/")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpSession session;
	private AccountDAO daoAccount = new AccountDAO();
	private DoctorDAO daoDoctor = new DoctorDAO();
	private PrescriptionDAO daoPrescription = new PrescriptionDAO();
	private final Logger logger;

    public Controller() {
        logger = Logger.getLogger(this.getClass());
        PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("resources/log4j.properties"));
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true); 
		String action = request.getServletPath();
		logger.info(action);
		
		switch (action) {
		case "/patient":
			this.showPatient(request, response);
			break;
		case "/prescription":
			this.showPrescription(request, response);
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void showPatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int patientID = Integer.parseInt(request.getParameter("id"));
			UserAccount accPatient = daoAccount.getUserAccount(patientID);
		
			if (accPatient != null) {
				request.setAttribute("status", session.getAttribute("status"));
				request.setAttribute("image", session.getAttribute("image"));
				request.setAttribute("accountID", daoAccount.getUserAccount((String)session.getAttribute("hash")).id);
				request.setAttribute("patientImage", accPatient.image);
				request.setAttribute("patientEmail", accPatient.email);
		
				Patient patient = daoDoctor.getPatient(patientID);
				request.setAttribute("patient", patient);
		
				ArrayList<Prescription> prescriptions = daoPrescription.getPatientsPrescriptions(patientID);
				request.setAttribute("prescriptionsList", prescriptions);
		
				this.getServletContext().getRequestDispatcher("/patient.jsp").forward(request, response);
				return;
			}
		}
		
		logger.warn("patient: invalid patient id");
		response.sendRedirect("main");
	}
	
	protected void showPrescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int prescriptionID = Integer.parseInt(request.getParameter("id"));
			Prescription item = daoPrescription.getPrescription(prescriptionID);
			
			if (item != null) {
				int accountID = daoAccount.getUserAccount((String)session.getAttribute("hash")).id;
				request.setAttribute("accountID", accountID);
				request.setAttribute("status", session.getAttribute("status"));
				request.setAttribute("image", session.getAttribute("image"));
				
				UserAccount accPatient = daoAccount.getUserAccount(item.patientID);
				Patient patient = daoDoctor.getPatient(item.patientID);
				UserAccount accDoctor = daoAccount.getUserAccount(item.doctorID);
				Doctor doctor = daoDoctor.getDoctor(item.doctorID);
				
				request.setAttribute("patientInfo", patient.name + " " + patient.passportNumber + " " + patient.birthDate + " " + accPatient.email);
				request.setAttribute("doctorInfo", doctor.getQuickInfo() + " " + accDoctor.email);
				request.setAttribute("timestamp", item.getStringTime());
				
				request.setAttribute("prescriptionID", prescriptionID);
				request.setAttribute("diagnosis", item.diagnosis);
				request.setAttribute("medicines", item.medicines);
				request.setAttribute("procedures", item.procedures);
				request.setAttribute("manipulations", item.manipulations);
				
				this.getServletContext().getRequestDispatcher("/prescription.jsp").forward(request, response);
				return;
			}
		}
		
		logger.warn("prescription: invalid prescription id");
		response.sendRedirect("main");
	}

}
