import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CalendarApp {
    private JFrame mainFrame;
    private EventManager eventManager;
    private LocalDate currentSelectedDate; // วันที่ที่ถูกเลือกในปัจจุบัน
    private YearMonth currentDisplayedMonth; // เดือนที่กำลังแสดงผล

    private JLabel monthLabel;
    private JPanel calendarPanel;
    private JTextArea eventDisplayArea;

    public CalendarApp() {
        eventManager = new EventManager();
        currentDisplayedMonth = YearMonth.now();
        currentSelectedDate = LocalDate.now();
        initializeGUI();
    }

    private void initializeGUI() {
        mainFrame = new JFrame("Simple Java Calendar");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // --- 1. Header (Month Navigation) ---
        monthLabel = new JLabel("", SwingConstants.CENTER);
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JButton prevButton = new JButton("<< Prev");
        prevButton.addActionListener(e -> changeMonth(-1));
        
        JButton nextButton = new JButton("Next >>");
        nextButton.addActionListener(e -> changeMonth(1));

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);
        mainFrame.add(headerPanel, BorderLayout.NORTH);

        // --- 2. Calendar Grid ---
        calendarPanel = new JPanel(new GridLayout(0, 7)); // 0 แถว, 7 คอลัมน์
        mainFrame.add(calendarPanel, BorderLayout.CENTER);

        // --- 3. Event Details and Add Button ---
        JPanel southPanel = new JPanel(new BorderLayout());
        eventDisplayArea = new JTextArea(10, 30);
        eventDisplayArea.setEditable(false);
        southPanel.add(new JScrollPane(eventDisplayArea), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Event to Selected Date");
        addButton.addActionListener(e -> showAddEventDialog());
        southPanel.add(addButton, BorderLayout.SOUTH);
        mainFrame.add(southPanel, BorderLayout.SOUTH);

        // แสดงผลเริ่มต้น
        updateCalendarView();
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    // *** ส่วนสำคัญ: สร้างตารางปฏิทิน ***
    private void updateCalendarView() {
        calendarPanel.removeAll();
        monthLabel.setText(currentDisplayedMonth.getMonth().toString() + " " + currentDisplayedMonth.getYear());

        // 1. เพิ่มชื่อวัน (Sun, Mon, Tue, ...)
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            calendarPanel.add(label);
        }

        LocalDate firstDayOfMonth = currentDisplayedMonth.atDay(1);
        int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue() % 7; // 0=Sunday, 1=Monday...

        // 2. เว้นช่องว่างก่อนวันแรกของเดือน
        for (int i = 0; i < dayOfWeekValue; i++) {
            calendarPanel.add(new JLabel("")); 
        }

        // 3. ใส่ตัวเลขวันที่
        int daysInMonth = currentDisplayedMonth.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            LocalDate date = currentDisplayedMonth.atDay(i);
            JButton dateButton = new JButton(String.valueOf(i));
            
            // Mark วันที่ที่มี Event
            if (!eventManager.getEventsForDate(date).isEmpty()) {
                dateButton.setBackground(Color.YELLOW); // มี Event เป็นสีเหลือง
            } else {
                dateButton.setBackground(null); // ไม่มี Event
            }
            
            // Mark วันที่ปัจจุบัน
            if (date.equals(currentSelectedDate)) {
                 dateButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // วันที่เลือกมีขอบแดง
            } else {
                 dateButton.setBorder(null);
            }

            // เพิ่ม Action Listener เมื่อคลิกปุ่มวันที่
            dateButton.addActionListener(createDateButtonListener(date));
            calendarPanel.add(dateButton);
        }

        // อัปเดตรายละเอียด Event สำหรับวันที่ที่เลือกปัจจุบัน
        displayEventsForDate(currentSelectedDate);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private ActionListener createDateButtonListener(LocalDate date) {
        return e -> {
            currentSelectedDate = date;
            updateCalendarView(); // อัปเดต GUI เพื่อเน้นวันที่ที่เลือกใหม่
        };
    }
    
    // *** Method การจัดการ Logic ***
    private void changeMonth(int change) {
        currentDisplayedMonth = currentDisplayedMonth.plusMonths(change);
        updateCalendarView();
    }

    private void displayEventsForDate(LocalDate date) {
        List<Event> events = eventManager.getEventsForDate(date);
        String text = "Events on " + date.toString() + ":\n\n";
        if (events.isEmpty()) {
            text += "No events planned.";
        } else {
            for (Event e : events) {
                text += " - " + e.getTitle() + "\n   (" + e.getDescription() + ")\n";
            }
        }
        eventDisplayArea.setText(text);
    }
    
    // *** Method สำหรับ Pop-up เพิ่ม Event ***
    private void showAddEventDialog() {
        String title = JOptionPane.showInputDialog(mainFrame, 
            "Event Title for " + currentSelectedDate.toString() + ":");
        if (title == null || title.trim().isEmpty()) return; // ผู้ใช้ยกเลิก/ไม่ป้อน

        String description = JOptionPane.showInputDialog(mainFrame, "Event Description:");
        
        Event newEvent = new Event(title, currentSelectedDate, description);
        eventManager.addEvent(newEvent);
        
        updateCalendarView(); // อัปเดตปฏิทินเพื่อแสดงสีเหลืองบนวันที่
    }

    // --- Main Method ---
    public static void main(String[] args) {
        // ให้ GUI Run บน Event Dispatch Thread
        SwingUtilities.invokeLater(CalendarApp::new);
    }
}