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

public class DoctorDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/hospital?serverTimezone=Europe/Minsk&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "MySqlPassword09052020";
	
	private ConnectionPool pool = ConnectionPool.getInstance();
	
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
	
	public ArrayList<Patient> getDoctorsPatients(int doctorID) {
		ArrayList<Patient> patients = new ArrayList<>();
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `service` WHERE `doctor_id` = ?");
			preparedStatement.setInt(1, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Patient patient = getPatient(Integer.parseInt(rs.getString("patient_id")));
				if (patient.status == PatientStatus.ON_TREATMENT)
					patients.add(patient);
			}
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return patients;
	}
	
	public ArrayList<Patient> getAllPatients() {
		ArrayList<Patient> patients = new ArrayList<>(); 
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `patient`");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String livingPlace = rs.getString("living_place");
				Patient human = new Patient(id, name, birthDate, livingPlace);
				human.passportNumber = rs.getString("passport");
				human.pastIllnesses = rs.getString("past_ill");
				human.status = PatientStatus.valueOf(rs.getString("status"));
				if (human.status == PatientStatus.ON_TREATMENT)
					patients.add(human);
			}
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return patients;
	}
	
	public Patient getPatient(int patientID) {
		Patient pat = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `patient` WHERE `id` = ?");
			preparedStatement.setInt(1, patientID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String name = rs.getString("name");
				Date birthDate = rs.getDate("birth_date");
				String livingPlace = rs.getString("living_place");
				pat = new Patient(id, name, birthDate, livingPlace);
				pat.passportNumber = rs.getString("passport");
				pat.pastIllnesses = rs.getString("past_ill");
				pat.status = PatientStatus.valueOf(rs.getString("status"));
			}
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return pat;
	}
	
	public Doctor getDoctor(int doctorID) {
		Doctor doc = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
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
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return doc;
	}
	
	public int doPrescription(Prescription prescription, int doctorID, int patientID) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO prescription (patient_id, doctor_id, timestamp, diagnosis, procedures, medicines, manipulations) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, doctorID);
			preparedStatement.setObject(3, new java.sql.Timestamp(prescription.timestamp.getTime())); 
			preparedStatement.setString(4, prescription.diagnosis);
			preparedStatement.setString(5, String.join("`", prescription.procedures));
			preparedStatement.setString(6, String.join(";", prescription.medicines));
			preparedStatement.setString(7, String.join("`", prescription.manipulations));
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
			    return rs.getInt(1);
			} 
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return -1;
	}
	
	public void doExecution(Prescription prescription) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `prescription` SET `procedures` = ?, `medicines` = ?, `manipulations` = ? WHERE `id` = ?");
			preparedStatement.setString(1, String.join("`", prescription.procedures));
			preparedStatement.setString(2, String.join(";", prescription.medicines));
			preparedStatement.setString(3, String.join("`", prescription.manipulations));
			preparedStatement.setInt(4, prescription.prescriptionID);
			preparedStatement.executeUpdate();
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
	}
	
	public void dischargePatient(Patient patient) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `patient` SET `past_ill` = ?, `status` = ? WHERE `id` = ?");
			preparedStatement.setString(1, patient.pastIllnesses);
			preparedStatement.setString(2, String.valueOf(patient.status));
			preparedStatement.setInt(3, patient.id);
			preparedStatement.executeUpdate();
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
	}
	
	public Boolean serviceExists(int patientID, int doctorID) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `service` WHERE `patient_id` = ? AND `doctor_id` = ?");
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, doctorID);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) 
				return true;
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }
		
		return false;
	}
}