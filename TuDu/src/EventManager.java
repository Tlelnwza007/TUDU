import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager {
    private List<Event> events;
    private final String DATA_FILE = "calendar_events.csv";
    private final String CSV_DELIMITER = "\\|"; // ใช้เครื่องหมาย "|" เป็นตัวแบ่งข้อมูล

    public EventManager() {
        this.events = new ArrayList<>();
        loadEvents(); // โหลดข้อมูลเมื่อสร้าง Object
    }

    // --- Business Logic Methods ---
    
    public void addEvent(Event event) {
        events.add(event);
        saveEvents(); // บันทึกข้อมูลทุกครั้งที่มีการเพิ่ม
    }

    public List<Event> getEventsForDate(LocalDate date) {
        return events.stream()
                     .filter(e -> e.getDate().equals(date))
                     .collect(Collectors.toList());
    }

    // --- File Handling Methods (CSV) ---

    private void loadEvents() {
        // อ่านข้อมูลจากไฟล์ CSV
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // แยก String ออกเป็นส่วนๆ ตามตัวแบ่งที่เรากำหนด ("|")
                String[] parts = line.split(CSV_DELIMITER, 3); // จำกัดให้แยกได้ไม่เกิน 3 ส่วน

                if (parts.length == 3) {
                    try {
                        String title = parts[0];
                        LocalDate date = LocalDate.parse(parts[1]); // แปลง String เป็น LocalDate
                        String description = parts[2];
                        
                        events.add(new Event(title, date, description));
                    } catch (Exception e) {
                        System.err.println("Skipping malformed CSV line: " + line);
                    }
                }
            }
            System.out.println("Events loaded successfully from CSV.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing data file found. Starting with empty calendar.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void saveEvents() {
        // เขียนข้อมูลทั้งหมดลงในไฟล์ CSV
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Event event : events) {
                // เขียนแต่ละ Event ในรูปแบบ CSV String ที่เรากำหนด
                pw.println(event.toCSVString()); 
            }
            System.out.println("Events saved successfully to CSV.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
