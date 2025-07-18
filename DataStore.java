import java.sql.*;
import java.util.HashMap;

class DataStore{

	private static String url = "jdbc:mysql://localhost:3306/PASSWD_MGR";
    private static String dbUser = "root";
    private static String dbPassword = "oct 29/10/18";
    
    public static void storeUserCredentials(User usr){
    	String sql = "INSERT INTO USER (USER_ID, USER_NAME, USER_PASSWORD, SALT, IV)" + "VALUES (?, ?, ?, ?, ?)";
    	
    	try(
    	Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
    	PreparedStatement stmt = con.prepareStatement(sql);
    	){
    		stmt.setInt(1, usr.getUserId());
    		stmt.setString(2, usr.getUserName());
    		stmt.setString(3, usr.getUserPassword());
    		stmt.setBytes(4, usr.getSalt());
    		stmt.setBytes(5, usr.getIV());
    		stmt.executeUpdate();
    		
    		con.close();
    	} catch (SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public static User getUserCredentials(String username){
    	String sql = "SELECT * FROM USER WHERE USER_NAME = ?";
    	User us = new User();
    	boolean found = false;
    	
    	try(
    	Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
    	PreparedStatement stmt = con.prepareStatement(sql);
    	){
    		stmt.setString(1, username);
    		ResultSet rs = stmt.executeQuery();
    		
    		while(rs.next()){
    			found = true;
    			us.setUserName(rs.getString("USER_NAME"));
    			us.setPassword(rs.getString("USER_PASSWORD"));
    			us.setSalt(rs.getBytes("SALT"));
    			us.setIV(rs.getBytes("IV"));
    			us.setUserId(rs.getInt("USER_ID"));
    		}
    		
    		con.close();
    		
    	} catch (SQLException e){
    		e.printStackTrace();
    	}
    	if(!found){
    		return null;
    	}
    	else{
    		return us;
    	}
    	
    }
    
    public static int getUsersCount(){
    	String sql = "SELECT COUNT(USER_ID) FROM USER";
    	int count = 0;
    	
    	try(
    	Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
    	PreparedStatement stmt = con.prepareStatement(sql);
    	){
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()){
    			count = rs.getInt(1);
    		}
    		
    		con.close();
    		
    	} catch(SQLException e){
    		e.printStackTrace();
    	}
    	return count;
    }
    
	public static void storeUserData(UserPassword userpassword){
		String sql = "INSERT INTO USER_PASSWORD (USER_ID, WEBSITE, USERNAME, PASSWORD)" + "VALUES (?, ?, ?, ?)";
		
		try(
		Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
		PreparedStatement stmt = con.prepareStatement(sql);
		){
			stmt.setInt(1, userpassword.getUserId());
			stmt.setString(2, userpassword.getWebsite());
			stmt.setString(3, userpassword.getUsername());
			stmt.setString(4, userpassword.getPassword());
			stmt.executeUpdate();
			
			con.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, UserPassword> getUserData(int userId){
		HashMap<String, UserPassword> userDetails = new HashMap<>();
		String sql = "SELECT * FROM USER_PASSWORD WHERE USER_ID = ?";
		
		try(
		Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
		PreparedStatement stmt = con.prepareStatement(sql);
		){
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				UserPassword temp = new UserPassword(
					rs.getInt("USER_ID"),
					rs.getString("WEBSITE"),
					rs.getString("USERNAME"),
					rs.getString("PASSWORD"));
					
				userDetails.put(temp.getWebsite(), temp);	
			}
			
			con.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return userDetails;
	}
	
	public static void deleteData(int userId, String website){
		String sql = "DELETE FROM USER_PASSWORD WHERE USER_ID = ? AND WEBSITE = ?";
		
		try(
		Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
		PreparedStatement stmt = con.prepareStatement(sql);
		){
			stmt.setInt(1, userId);
			stmt.setString(2, website);
			stmt.executeUpdate();
			
			con.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
