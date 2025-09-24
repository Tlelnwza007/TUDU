import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class csv {
    private static final String CSV_FILE = "users.csv";
    private static final String CSV_HEADER = "username,password";

    public csv() {
        initializeCsvFile();
    }

    private void initializeCsvFile() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try (FileWriter fw = new FileWriter(CSV_FILE);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(CSV_HEADER);
                bw.newLine();
            } catch (IOException e) {
                System.err.println("Error initializing CSV file: " + e.getMessage());
            }
        }
    }

    public boolean registerUser(Users users) {
        // ตรวจสอบว่ามีผู้ใช้คนนี้อยู่แล้วหรือไม่
        if (getUserByUsername(users.getUsername()) != null) {
            return false; // ผู้ใช้มีอยู่แล้ว
        }

        try (FileWriter fw = new FileWriter(CSV_FILE, true); // true for append mode
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(users.toString());
            return true;
        } catch (IOException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public Users authenticateUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return new Users(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null; // ไม่พบผู้ใช้หรือรหัสผ่านไม่ถูกต้อง
    }

    public Users getUserByUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return new Users(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        }
        return null; // ไม่พบผู้ใช้
    }
}