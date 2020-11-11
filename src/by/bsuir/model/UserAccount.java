package by.bsuir.model;

public class UserAccount {
	public UserAccount(int id, String username, String hash) {
		this.id = id;
		this.username = username;
		this.hash = hash;
	}
	
	public UserAccount(String username, String hash, String email, String status) {
		this.username = username;
		this.hash = hash;
		this.email = email;
		this.image = "/WebHospital/image/default-avatar.jpg";
		this.status = UserStatus.valueOf(status);
	}
	
	public int id;
	public String username;
	public String hash;
	public String email;
	public String image;
	public UserStatus status;
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getImage() {
		return image;
	}
	
	public UserStatus getStatus() {
		return status;
	}
}
