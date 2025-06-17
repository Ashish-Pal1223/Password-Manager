import java.util.Scanner;
import java.util.HashMap;

class PasswordManagerDriver{
	static User newAccount(String username, String password){
		User newUser = new User();
		newUser.setUserName(username);
		newUser.setPassword(password);
		return newUser;
	}
	
	static void userMenu(User crntUser){
		boolean running = true;
		String website = "";
		String username = "";
		String password = "";
		
		while(running){
			System.out.println("----------Welcome " + crntUser.getUserName() + "----------");
			System.out.println("1.Enter new password");
			System.out.println("2.Show all password");
			System.out.println("3.log out");
			System.out.println();
			System.out.print("Enter you choice : ");
			
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			sc.nextLine();
			System.out.println();
			
			switch(choice){
				case 1:
					System.out.print("Website name : ");
					website = sc.nextLine();
					System.out.print("username : ");
					username = sc.nextLine();
					System.out.print("password : ");
					password = sc.nextLine();
					crntUser.enterCredentials(website, username, password);
					break;
				
				case 2:
					crntUser.showCredentials();
					break;
					
				case 3:
					running = false;
					break;
					
				default:
					System.out.println("Invalid choice!");
					System.out.println();
			}
		}
	}

	public static void main(String[] args){
		HashMap<String, User> userList = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		String username = "";
		String password = "";
		
		boolean running = true;
		while(running){
		
			System.out.println("----------Welcome----------");
			System.out.println("1.Create New Account");
			System.out.println("2.Log In");
			System.out.println("3.Exit");
			System.out.println();
			System.out.print("Enter you choice : ");
			
			int choice = sc.nextInt();
			sc.nextLine();
			System.out.println();
			
			switch(choice){
				case 1:
					User newUser;
					System.out.print("Enter Username : ");
					username = sc.nextLine();
					System.out.print("Enter Password : ");
					password = sc.nextLine();
					newUser = newAccount(username, password);
					userList.put(username, newUser);
					System.out.println("Account Created!");
					System.out.println();
					break;
				
				case 2:
					User currentUser;
					System.out.print("Enter username : ");
					username = sc.nextLine();
					System.out.print("Enter password : ");
					password = sc.nextLine();
					System.out.println();
					
					if(userList.get(username) == null){
						System.out.println("Invalid Username!");
						System.out.println();
					}
					else{
						currentUser = userList.get(username);
						if(!password.equals(currentUser.getUserPassword())){
							System.out.println("Incorrect Password!");
							System.out.println();
						}
						else{
							userMenu(currentUser);
						}
					}
					
					break;
					
				case 3: 
					running = false;
					break;
					
				default:
					System.out.println("Enter the correct choice!");
			}
		}
	}
}
