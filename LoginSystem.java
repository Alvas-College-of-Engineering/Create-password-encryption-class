import java.util.HashMap;
import java.util.Scanner;

// User class
class User {
    String username;
    String passwordHash;
    String salt;

    public User(String username, String passwordHash, String salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
}

public class LoginSystem {

    private static HashMap<String, User> database = new HashMap<>();

    // Register user
    public static void register(String username, String password) throws Exception {

        if (database.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        String salt = PasswordEncryption.generateSalt();
        String hash = PasswordEncryption.hashPassword(password, salt);

        database.put(username, new User(username, hash, salt));
        System.out.println("User registered successfully!");
    }

    // Login user (secure message)
    public static void login(String username, String password) throws Exception {

        User user = database.get(username);

        if (user != null &&
            PasswordEncryption.verifyPassword(password, user.passwordHash, user.salt)) {

            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Preloaded users
        register("bindu", "secure123");
        register("admin", "admin123");
        register("user1", "pass1");
        register("user2", "pass2");

        while (true) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (choice == 1) {
                System.out.print("Enter new username: ");
                String username = scanner.nextLine();

                System.out.print("Enter new password: ");
                String password = scanner.nextLine();

                register(username, password);

            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();

                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                login(username, password);

            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}