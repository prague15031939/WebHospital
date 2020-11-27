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

public class AccountDAO {
	private ConnectionPool pool = ConnectionPool.getInstance();
	
	public int registerUser(UserAccount acc) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `user_account` (`user_name`, `user_hash`, `email`, `image`, `status`) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, acc.username);
			preparedStatement.setString(2, acc.hash);
			preparedStatement.setString(3, acc.email);
			preparedStatement.setString(4, acc.image);
			preparedStatement.setString(5, acc.status.toString());
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
	
	public void registerPatient(Patient patient) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `patient` (`id`, `name`, `passport`, `birth_date`, `living_place`, `past_ill`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, patient.id);
			preparedStatement.setString(2, patient.name);
			preparedStatement.setString(3, patient.passportNumber);
			preparedStatement.setObject(4, new java.sql.Date(patient.birthDate.getTime()));
			preparedStatement.setString(5, patient.livingPlace);
			preparedStatement.setString(6, patient.pastIllnesses);
			preparedStatement.setString(7, patient.status.toString());
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
	
	public void registerDoctor(Doctor doctor) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `doctor` (`id`, `name`, `birth_date`, `specialization`) VALUES (?, ?, ?, ?)");
			preparedStatement.setInt(1, doctor.id);
			preparedStatement.setString(2, doctor.name);
			preparedStatement.setObject(3, new java.sql.Date(doctor.birthDate.getTime()));
			preparedStatement.setString(4, doctor.specialization.toString());
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
	
	protected UserAccount fillUserAccount(PreparedStatement preparedStatement) {
		UserAccount acc = null;
		try {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("user_id"));
				String name = rs.getString("user_name");
				String hash = rs.getString("user_hash");
				acc = new UserAccount(id, name, hash);
				acc.email = rs.getString("email");
				acc.image = rs.getString("image");
				acc.status = UserStatus.valueOf(rs.getString("status"));
			} 
		}
		catch (Exception e) {}
		return acc;
	}
	
	public UserAccount getUserAccount(String requestedHash) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_hash` = ?");
			preparedStatement.setString(1, requestedHash);
			return fillUserAccount(preparedStatement);
		} 
		catch (Exception e) {}
		finally {
	        try {
	            connection.close();
	        } catch (SQLException sqlee) {
	            sqlee.printStackTrace();
	        }
	    }

		return null;
	}
	
	public UserAccount getUserAccount(int id) {		
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `user_account` WHERE `user_id` = ?");
			preparedStatement.setInt(1, id);
			return fillUserAccount(preparedStatement);
		} 
		catch (Exception e) {}
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
