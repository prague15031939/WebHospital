package by.bsuir.model;

enum UserStatus {
	CHIEF, DOCTOR, NURSE, PATIENT
}

public class UserAccount {
	public UserAccount(int id, String username, String hash) {
		this.id = id;
		this.username = username;
		this.hash = hash;
	}
	
	public int id;
	public String username;
	public String hash;
	public String email;
	public String image;
	public UserStatus status;
	
	public String StringForm() {
		return String.valueOf(id) + ": " + username + ": " + hash + "\n";
	}
}
