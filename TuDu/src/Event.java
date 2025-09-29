import java.time.LocalDate;

public class Event {
    // ไม่จำเป็นต้องมี private static final long serialVersionUID = 1L;

    private String title;
    private LocalDate date;
    private String description;

    public Event(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getters
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }

    // เมธอดสำหรับแปลง Event เป็น String ในรูปแบบ CSV
    public String toCSVString() {
        // ใช้ " | " เป็นตัวแบ่งแทน comma เพื่อหลีกเลี่ยงปัญหาในกรณีที่ Title หรือ Description มี comma
        // รูปแบบ: Title|YYYY-MM-DD|Description
        return String.format("%s|%s|%s", title, date.toString(), description);
    }
    
    // (สามารถเพิ่มเมธอดอื่น ๆ ได้)
}