package by.bsuir.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.bsuir.model.Doctor;
import by.bsuir.model.DoctorSpecialization;
import by.bsuir.model.Patient;
import by.bsuir.model.Prescription;
import by.bsuir.model.UserAccount;

public class DAO {
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
	
	public UserAccount GetUserAccount(String requestedHash) {		
		UserAccount acc = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_hash` = ?");
			preparedStatement.setString(1, requestedHash);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("user_id"));
				String name = rs.getString("user_name");
				String hash = rs.getString("user_hash");
				acc = new UserAccount(id, name, hash);
			}
		} 
		catch (Exception e) {}
		
		return acc;
	}
	
	public UserAccount GetUserAccount(int id) {		
		UserAccount acc = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_id` = ?");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				id = Integer.parseInt(rs.getString("user_id"));
				String name = rs.getString("user_name");
				String hash = rs.getString("user_hash");
				acc = new UserAccount(id, name, hash);
			}
		} 
		catch (Exception e) {}
		
		return acc;
	}
	
	public ArrayList<Patient> GetDoctorsPatients(int doctorID) {
		ArrayList<Patient> patients = new ArrayList<>(); 
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `service` WHERE `doctor_id` = ?");
			preparedStatement.setInt(1, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int patientID = Integer.parseInt(rs.getString("patient_id"));
				patients.add(GetPatient(patientID));
			}
		} 
		catch (Exception e) {}
		
		return patients;
	}
	
	public Patient GetPatient(int patientID) {
		Patient pat = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `patient` WHERE `id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String livingPlace = rs.getString("living_place");
				pat = new Patient(id, name, birthDate, livingPlace);
			}
		} 
		catch (Exception e) {}
		
		return pat;
	}
	
	public Doctor GetDoctor(int doctorID) {
		Doctor doc = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `doctor` WHERE `id` = ?");
			preparedStatement.setInt(1, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String spec = rs.getString("specialization");
				doc = new Doctor(id, name, birthDate, DoctorSpecialization.valueOf(spec));
			}
		} 
		catch (Exception e) {}
		
		return doc;
	}
	
	public void doPrescription(Prescription prescription, int doctorID, int patientID) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO prescription (patient_id, doctor_id, diagnosis, procedures, medicines, manipulations) VALUES (?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, doctorID);
			preparedStatement.setString(3, prescription.diagnosis);
			preparedStatement.setString(4, String.join("`", prescription.procedures));
			preparedStatement.setString(5, prescription.medicines);
			preparedStatement.setString(6, String.join("`", prescription.manipulations));
			preparedStatement.executeUpdate();
		} 
		catch (Exception e) {}
	}
}
