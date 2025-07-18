import java.util.HashMap;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class User{
	private static int userCount = 0;
	private int userId;
	private byte[] salt;
	private byte[] iv;
	private String userName;
	private String userPassword;
	
	private HashMap<String, UserPassword> UserPasswordList = new HashMap<>();
	
	public User(){
	}
	
	public User(String userName, String userPassword){
		userId = ++userCount;
		setUserName(userName);
		hashPassword(userPassword);
		salt = AESCipher.generateRandomBytes(16);
		iv = AESCipher.generateRandomBytes(16);
	}
	
	public static void setUserCount(){
		userCount = DataStore.getUsersCount();
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public void setPassword(String userPassword){
		this.userPassword = userPassword;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public void setSalt(byte[] salt){
		this.salt = salt;
	}
	
	public void setIV(byte[] iv){
		this.iv = iv;
	}
	
	public int getUserCount(){
		return userCount;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getUserPassword(){
		return userPassword;
	}
	
	public byte[] getSalt(){
		return salt;
	}
	
	public byte[] getIV(){
		return iv;
	}
	
	public void enterCredentials(String website, String username, String password, String masterPassword){
		try {
    		username = AESCipher.encrypt(username, masterPassword, this.salt, this.iv);
    		password = AESCipher.encrypt(password, masterPassword, this.salt, this.iv);
		} catch (Exception e) {
    		System.err.println("Encryption failed: " + e.getMessage());
    		e.printStackTrace();
		}
		
		UserPassword userpassword = new UserPassword(this.userId, website, username, password);
		DataStore.storeUserData(userpassword);
		System.out.println("Data entered successfully!");
		System.out.println();
	}
	
	public void showCredentials(String masterPassword){
		UserPasswordList = DataStore.getUserData(getUserId());
	
		String username = "";
		String password = "";
		if(UserPasswordList.isEmpty()){
			System.out.println("There is no data in the password manager!");
			System.out.println();
		}
		else{
			for(UserPassword up : UserPasswordList.values()){
				try {
					username = AESCipher.decrypt(up.getUsername(), masterPassword, this.salt, this.iv);
					password = AESCipher.decrypt(up.getPassword(), masterPassword, this.salt, this.iv);
				} catch (Exception e){
					System.err.println("decryption failed : " + e.getMessage());
					e.printStackTrace();
				}
				System.out.println("Website : " + up.getWebsite());
				System.out.println("Username : " + username);
				System.out.println("Password : " + password);
				System.out.println();
			}
		}
	}
	
	public void updateCredentials(String website, String username, String password, String masterPassword){
		UserPasswordList.remove(website);
		DataStore.deleteData(this.userId, website);
		enterCredentials(website, username, password, masterPassword);
		System.out.println("Data updated successfully!");
		System.out.println();
	}
	
	public void deleteCredentials(String website){
		DataStore.deleteData(this.userId, website);
		System.out.println("Password entry deleted successfully!");
		System.out.println();
	}
	
	public void hashPassword(String password){
		String pw = BCrypt.hashpw(password, BCrypt.gensalt(12));
		setPassword(pw);
	}
	
	public static User validate(String username, String password){
		User us = DataStore.getUserCredentials(username);
		if(us == null){
			System.out.println("Invalid Username!");
			System.out.println();
			return null;
		}
		else{
			boolean matched = BCrypt.checkpw(password, us.getUserPassword());
			if(matched){
				return us;
			}
			else{
				System.out.println("Invalid Password!");
				System.out.println();
				return null;
			}
		}
		
	}
}
