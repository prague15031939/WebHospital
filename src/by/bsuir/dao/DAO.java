package by.bsuir.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
