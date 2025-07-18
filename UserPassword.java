public class UserPassword{
	private int userId;
	private String website;
	private String username;
	private String password;
	
	public UserPassword(int userId, String website, String username, String password){
		this.userId = userId;
		this.website = website;
		this.username = username;
		this.password = password;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public String getWebsite(){
		return website;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
}
