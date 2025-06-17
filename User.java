import java.util.ArrayList;

class User{
	private static int userCount = 0;
	private int userId;
	private String userName;
	private String userPassword;
	
	private ArrayList<UserPassword> UserPasswordList = new ArrayList<>();
	
	public User(){
		userId = ++userCount;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public void setPassword(String userPassword){
		this.userPassword = userPassword;
	}
	
	public int getUserCount(){
		return userCount;
	}
	
	public int userId(){
		return userId;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getUserPassword(){
		return userPassword;
	}
	
	public void enterCredentials(String website, String username, String password){
		UserPasswordList.add(new UserPassword(this.userId, website, username, password));
		System.out.println("Data entered successfully!");
		System.out.println();
	}
	
	public void showCredentials(){
		if(UserPasswordList.isEmpty()){
			System.out.println("There is no data in the password manager!");
			System.out.println();
		}
		else{
			for(UserPassword up : UserPasswordList){
				System.out.println("Website : " + up.getWebsite());
				System.out.println("Username : " + up.getUsername());
				System.out.println("Password : " + up.getPassword());
				System.out.println();
			}
		}
	}
}
