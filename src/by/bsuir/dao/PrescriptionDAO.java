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

import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.PatientStatus;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;
import by.bsuir.model.UserStatus;

public class PrescriptionDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/hospital?serverTimezone=Europe/Minsk&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "MySqlPassword09052020";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	public ArrayList<Prescription> getPatientsPrescriptions(int patientID) {
		ArrayList<Prescription> list = new ArrayList<>(); 
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `prescription` WHERE `patient_id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {	
				int prescriptionID = Integer.parseInt(rs.getString("id"));
				list.add(getPrescription(prescriptionID));
			}
		} 
		catch (Exception e) {}
		
		return list;
	}
	
	public Prescription getPrescription(int prescriptionID) {
		try {
			Connection connection = getConnection();
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
		catch (Exception e) {}
		
		return null;
	}
	
	public ArrayList<String[]> getManipulations(DoctorSpecialization spec) {
		try {
			Connection connection = getConnection();
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
		catch (Exception e) {}
		
		return null;
	}
	
	public ArrayList<String[]> getProcedures(DoctorSpecialization spec) {
		try {
			Connection connection = getConnection();
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
		catch (Exception e) {}
		
		return null;
	}
	
}