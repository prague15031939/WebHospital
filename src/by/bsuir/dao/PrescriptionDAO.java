package by.bsuir.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.PatientStatus;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

public class PrescriptionDAO {	
	private final Logger logger;
	private ConnectionPool pool = ConnectionPool.getInstance();
	
	public PrescriptionDAO() {
        logger = Logger.getLogger(this.getClass());
        PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("resources/log4j.properties"));
	}

	public ArrayList<Prescription> getPatientsPrescriptions(int patientID) {
		Connection connection = null;
		ArrayList<Prescription> list = new ArrayList<>(); 
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `prescription` WHERE `patient_id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {	
				int prescriptionID = Integer.parseInt(rs.getString("id"));
				list.add(getPrescription(prescriptionID));
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return list;
	}
	
	public Prescription getPrescription(int prescriptionID) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `prescription` WHERE `id` = ?");
			preparedStatement.setInt(1, prescriptionID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				String diagnosis = rs.getString("diagnosis");
				String[] medicines = rs.getString("medicines").split(";");
				String[] procedures = rs.getString("procedures").split("`");
				String[] manipulations = rs.getString("manipulations").split("`");
				Prescription item = new Prescription(diagnosis, medicines, procedures, manipulations);
				item.timestamp = rs.getTimestamp("timestamp");
				item.prescriptionID = Integer.parseInt(rs.getString("id"));
				item.patientID = Integer.parseInt(rs.getString("patient_id"));
				item.doctorID = Integer.parseInt(rs.getString("doctor_id"));
				
				return item;
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return null;
	}
	
	public ArrayList<String[]> getManipulations(DoctorSpecialization spec) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `manipulations` WHERE `specialization` = ?");
			preparedStatement.setString(1, String.valueOf(spec));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ArrayList<String[]> manipulations = new ArrayList<String[]>();
				for (String item : rs.getString("manipulations_string").split("~"))
					manipulations.add(item.split("`"));
				
				return manipulations;
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return null;
	}
	
	public ArrayList<String[]> getProcedures(DoctorSpecialization spec) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `procedures` WHERE `specialization` = ?");
			preparedStatement.setString(1, String.valueOf(spec));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ArrayList<String[]> procedures = new ArrayList<String[]>();
				for (String item : rs.getString("procedures_string").split("~"))
					procedures.add(item.split("`"));
				
				return procedures;
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return null;
	}
	
}