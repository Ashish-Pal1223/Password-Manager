import java.util.Scanner;
import java.util.HashMap;

class PasswordManagerDriver{
	
	static void userMenu(User crntUser, String masterPassword){
		boolean running = true;
		String website = "";
		String username = "";
		String password = "";
		
		while(running){
			System.out.println("----------Welcome " + crntUser.getUserName() + "----------");
			System.out.println("1.Enter new password");
			System.out.println("2.Show all password");
			System.out.println("3.Update password");
			System.out.println("4.Delete Password");
			System.out.println("5.log out");
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
					crntUser.enterCredentials(website, username, password, masterPassword);
					break;
				
				case 2:
					crntUser.showCredentials(masterPassword);
					break;
					
				case 3:
					System.out.print("Website name : ");
					website = sc.nextLine();
					System.out.print("username : ");
					username = sc.nextLine();
					System.out.print("password : ");
					password = sc.nextLine();
					crntUser.updateCredentials(website, username, password, masterPassword);
					break;
					
				case 4:
					System.out.print("Webiste name : ");
					website = sc.nextLine();
					crntUser.deleteCredentials(website);
					break;
					
				case 5:
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
		User.setUserCount();
		Scanner sc = new Scanner(System.in);
		String username = "";
		String masterPassword = "";
		
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
					masterPassword = sc.nextLine();
					newUser = new User(username, masterPassword);
					DataStore.storeUserCredentials(newUser);
					System.out.println("Account Created!");
					System.out.println();
					break;
				
				case 2:
					User currentUser;
					System.out.print("Enter username : ");
					username = sc.nextLine();
					System.out.print("Enter password : ");
					masterPassword = sc.nextLine();
					System.out.println();
					
					currentUser = User.validate(username, masterPassword);
					
					if(currentUser == null){
						System.out.println("Invalid Username or password!");
					}
					else{
						userMenu(currentUser, masterPassword);
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
