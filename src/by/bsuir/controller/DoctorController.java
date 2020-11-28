package by.bsuir.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

@WebServlet("/doc")
public class DoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpSession session;
	private DoctorDAO daoDoctor = new DoctorDAO();
	private AccountDAO daoAccount = new AccountDAO();
	private PrescriptionDAO daoPrescription = new PrescriptionDAO();
	private final Logger logger;
	
    public DoctorController() {
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
		case "/doctor":
			this.showDoctor(request, response);
			break;
		case "/doctor-prescribe":
			this.showCreator(request, response);
			break;
		case "/prescribe":
			this.doPrescription(request, response);
			break;
		case "/execute-prescription":
			this.showExecutor(request, response);
			break;
		case "/execute":
			this.doExecution(request, response);
			break;
		case "/discharge":
			this.dischargePatient(request, response);
			break;
		}
		
	}
	
	protected void showDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount accDoctor = daoAccount.getUserAccount((String)session.getAttribute("hash"));
		Doctor doctor = daoDoctor.getDoctor(accDoctor.id);
		
		request.setAttribute("accountID", accDoctor.id);
		request.setAttribute("status", session.getAttribute("status"));
		request.setAttribute("image", session.getAttribute("image"));
		request.setAttribute("doctorSpecialization", doctor.specialization.toString().toLowerCase().replaceAll("_", " "));
		request.setAttribute("doctorImage", accDoctor.image);
		request.setAttribute("doctorEmail", accDoctor.email);
		request.setAttribute("doctor", doctor);
		
		ArrayList<Patient> patients = null;
		if (doctor.specialization == DoctorSpecialization.NURSE)
			patients = daoDoctor.getAllPatients();
		else
			patients = daoDoctor.getDoctorsPatients(accDoctor.id);
		request.setAttribute("patientList", patients);
		this.getServletContext().getRequestDispatcher("/doctor.jsp").forward(request, response);
	}
	
	protected void showCreator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int patientID = Integer.parseInt(request.getParameter("id"));
			Patient patient = daoDoctor.getPatient(patientID);
			
			if (patient != null) {
				UserAccount accPatient = daoAccount.getUserAccount(patientID);
				Doctor doctor = daoDoctor.getDoctor(daoAccount.getUserAccount((String)session.getAttribute("hash")).id);
				
				request.setAttribute("accountID", doctor.id);
				request.setAttribute("status", session.getAttribute("status"));
				request.setAttribute("image", session.getAttribute("image"));
				request.setAttribute("patient", patient);
				request.setAttribute("patientImage", accPatient.image);
				request.setAttribute("patientEmail", accPatient.email);
				
				ArrayList<String[]> procedures = daoPrescription.getProcedures(doctor.specialization);
				request.setAttribute("procedures", procedures);
				ArrayList<String[]> manipulations = daoPrescription.getManipulations(doctor.specialization);
				request.setAttribute("manipulations", manipulations);
				
				request.setAttribute("canSubmit", daoDoctor.serviceExists(patient.id, doctor.id));
				
				this.getServletContext().getRequestDispatcher("/doctor-prescribe.jsp").forward(request, response);
				return;
			}
		}
		
		logger.warn("doctor-prescribe: invalid patient id");
		response.sendRedirect("main");
	}
	
	protected void doPrescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int patientID = Integer.parseInt(request.getParameter("id"));
			Patient patient = daoDoctor.getPatient(patientID);
			
			if (patient != null) {
				String[] medicines;
				if (request.getParameter("medicines") == null) 
					medicines = new String[0];
				else
					medicines = request.getParameter("medicines").split(";");
				
				String[] procedures = request.getParameterValues("proc-box");
				if (procedures == null) procedures = new String[0];
				String[] operations = request.getParameterValues("manipulation-box");
				if (operations == null) operations = new String[0];
				
				Prescription prscr = new Prescription(  
					request.getParameter("diagnosis"), 
					medicines,
					procedures,
					operations
				);
				
				UserAccount doctor = daoAccount.getUserAccount((String)session.getAttribute("hash"));
				prscr.timestamp = new Date();
				
				int prescriptionID = -1;
				if (doctor.status == UserStatus.DOCTOR)
					 prescriptionID = daoDoctor.doPrescription(prscr, doctor.id, patient.id);
				
				if (prescriptionID != -1)
					response.sendRedirect("prescription?id=" + String.valueOf(prescriptionID));
				else {
					logger.warn("prescription: invalid prescription id");
					response.sendRedirect("main");
				}
				return;
			}
		}
		
		logger.warn("prescribe: invalid patient id");
		response.sendRedirect("main");
	}
	
	protected void showExecutor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				
				Doctor currentDoctor = daoDoctor.getDoctor(accountID);
				if (currentDoctor.specialization != DoctorSpecialization.NURSE) {
					request.setAttribute("procAccessors", filterExecutionOptions(item.procedures, currentDoctor.specialization, "proc"));
					request.setAttribute("operAccessors", filterExecutionOptions(item.manipulations, currentDoctor.specialization, "oper"));
				}
				
				request.setAttribute("prescriptionID", prescriptionID);
				request.setAttribute("diagnosis", item.diagnosis);
				request.setAttribute("medicines", item.medicines);
				request.setAttribute("procedures", item.procedures);
				request.setAttribute("manipulations", item.manipulations);
				
				this.getServletContext().getRequestDispatcher("/execute-prescription.jsp").forward(request, response);
				return;
			}
		}
		
		logger.warn("execute-prescription: invalid prescription id");
		response.sendRedirect("main");
	}
	
	protected void doExecution(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int prescriptionID = Integer.parseInt(request.getParameter("id"));
			Prescription prescription = daoPrescription.getPrescription(prescriptionID);
			
			if (prescription != null) {
				String[] medicines = request.getParameterValues("medicine-box");
				String[] procedures = request.getParameterValues("proc-box");
				String[] manipulations = request.getParameterValues("manipulation-box");
				
				String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());		
				Doctor doctor = daoDoctor.getDoctor(daoAccount.getUserAccount((String)session.getAttribute("hash")).id);
		
				if (procedures != null) prescription.procedures = modifyPrescriptionElement(procedures, prescription.procedures, doctor, timestamp);
				if (medicines != null) prescription.medicines = modifyPrescriptionElement(medicines, prescription.medicines, doctor, timestamp);
				if (manipulations != null) prescription.manipulations = modifyPrescriptionElement(manipulations, prescription.manipulations, doctor, timestamp);
		
				daoDoctor.doExecution(prescription);
				
				if (prescriptionID != -1)
					response.sendRedirect("prescription?id=" + String.valueOf(prescriptionID));
				return;
			}
		}
		
		logger.warn("execute: invalid prescription id");
		response.sendRedirect("main");
	}
	
	protected void dischargePatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int patientID = Integer.parseInt(request.getParameter("id"));
			Patient patient = daoDoctor.getPatient(patientID);
			
			if (patient != null && (String)session.getAttribute("status") == "DOCTOR") {
				patient.pastIllnesses += "; " + request.getParameter("final-diagnosis");
				patient.status = PatientStatus.DISCHARGED;
				daoDoctor.dischargePatient(patient);
				response.sendRedirect("doctor");
				return;
			}
		}
		
		logger.warn("discharge: invalid patient id");
		response.sendRedirect("main");
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
	
	protected Boolean[] filterExecutionOptions(String[] TargetOptions, DoctorSpecialization spec, String type) {
		ArrayList<String[]> DoctorOptions = null;
		if (type == "proc") DoctorOptions = daoPrescription.getProcedures(spec);
		else if (type == "oper") DoctorOptions = daoPrescription.getManipulations(spec);
		
		ArrayList<String> SourceOptions = new ArrayList<String>();
		for (String[] itemList : DoctorOptions)
			for (String item : itemList)
				SourceOptions.add(item);
		
		Boolean[] result = new Boolean[TargetOptions.length];
 		for (int i = 0; i < TargetOptions.length; i++) {
			if (SourceOptions.contains(TargetOptions[i])) result[i] = true;
			else result[i] = false;
		}
 		
 		return result;
	}

}
