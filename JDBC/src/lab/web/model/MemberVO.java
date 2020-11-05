package lab.web.model;

public class MemberVO {

	public String userId;
	public String name;
	public String password;
	public String email;
	public String address;
	
	@Override
	public String toString() {
		return "MemberVO [userId=" + userId + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", address=" + address + "]";
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
