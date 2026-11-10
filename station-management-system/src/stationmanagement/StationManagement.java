package stationmanagement;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class StationManagement extends JFrame implements ActionListener {

    private int attempts = 3; // عدد المحاولات المتاحة
    private JLabel label1, label2; // تسميات الحقول
    private JTextField userNameField; // حقل اسم المستخدم
    private JPasswordField passwordField; // حقل كلمة المرور
    public JButton loginButton; // زر تسجيل الدخول
    public String username;
    public String password;
    private ArrayList<Object> serviceInstances = new ArrayList<>(); // قائمة لحفظ جميع العمليات

    public static void main(String[] args) {
        try {
            new StationManagement(); // تشغيل الواجهة
        } catch (Exception e) {
            System.out.println("Error!!:(");
        }
    }

    public StationManagement() {
        // إعداد النافذة الرئيسية
        JFrame frame = new JFrame("Station Management");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout());

        // إنشاء لوحة تسجيل الدخول
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createTitledBorder("Log In"));
        panel.setBounds(500, 500, 500, 500);
        panel.setBackground(new Color(125, 180, 197));

        // تسميات الحقول
        label1 = new JLabel("User Name:");
        label2 = new JLabel("Password:");
        label1.setBounds(300, 340, 1000, 50);
        label2.setBounds(300, 380, 1000, 50);
        label1.setFont(new Font("Arial", Font.PLAIN, 20));
        label2.setFont(new Font("Arial", Font.PLAIN, 20));

        // حقول الإدخال
        userNameField = new JTextField();
        passwordField = new JPasswordField();
        userNameField.setBounds(500, 350, 300, 30);
        passwordField.setBounds(500, 390, 300, 30);

        // زر تسجيل الدخول
        loginButton = new JButton("Log In");
        loginButton.setBounds(500, 450, 200, 30);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.addActionListener(this);

        // إضافة العناصر للوحة
        panel.add(label1);
        panel.add(label2);
        panel.add(userNameField);
        panel.add(passwordField);
        panel.add(loginButton);

        // تحميل الصورة الخلفية
        // الصورة تُحمّل من داخل المشروع (src/stationmanagement/background.jpg) حتى يعمل البرنامج على أي جهاز
        JLabel label;
        java.net.URL imageUrl = StationManagement.class.getResource("background.jpg");
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(1000, 1000, Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(scaledImage));
        } else {
            // في حال عدم وجود الصورة: يُعرض اسم البرنامج بدلًا منها
            label = new JLabel("Station Management", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 56));
            label.setOpaque(true);
            label.setBackground(new Color(38, 70, 83));
            label.setForeground(Color.WHITE);
        }

        // إضافة العناصر للنافذة
        frame.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        username = userNameField.getText();
        password = new String(passwordField.getPassword());

        // التحقق من بيانات تسجيل الدخول
        if (username.equals("admin") && password.equals("1234")) {
            openServiceSelection(this);
        } else {
            attempts--;
            if (attempts > 0) {
                JOptionPane.showMessageDialog(this, "Wrong! You have " + attempts + " remaining attempts.", "Wrong", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "⛔ Account has been disabled!", "Disabled", JOptionPane.ERROR_MESSAGE);
                System.exit(0); // الخروج بعد استنفاد المحاولات
            }
        }
    }

//    Object serviceInstance = null; // لتخزين الخدمة المختارة
    public void openServiceSelection(JFrame previousFrame) {
        JFrame frame = new JFrame("Select a service");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(750, 400);

        // لوحة تحتوي على أزرار الخدمات
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton btn1 = new JButton("Electric Charging");
        JButton btn2 = new JButton("Washing");
        JButton btn3 = new JButton("Mechanics");
        JButton btn4 = new JButton("Review Services");
        JButton btn5 = new JButton("Go Back");

        // تنسيق الأزرار
        Font btnFont = new Font("Arial", Font.BOLD, 18);
        btn1.setFont(btnFont);
        btn2.setFont(btnFont);
        btn3.setFont(btnFont);
        btn4.setFont(btnFont);
        btn5.setFont(btnFont);

        // إضافة الأزرار للوحة
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        frame.add(panel);
        frame.setVisible(true);

        // أزرار الخدمات
        btn1.addActionListener(e -> {
            ElectricCharging chargingService = new ElectricCharging(frame);
            serviceInstances.add(chargingService); // حفظ العملية في القائمة
            frame.setVisible(false);

        });

        btn2.addActionListener(e -> {
            Washings washingService = new Washings(frame);
            serviceInstances.add(washingService); // حفظ العملية في القائمة
            frame.setVisible(false);

        });

        btn3.addActionListener(e -> {
            Mechanic mechanicService = new Mechanic(frame);
            serviceInstances.add(mechanicService); // حفظ العملية في القائمة
            frame.setVisible(false);

        });

        // زر مراجعة السجل
        btn4.addActionListener(e -> {

            StringBuilder history = new StringBuilder("📜 سجل جميع العمليات:\n\n");

            for (Object service : serviceInstances) {
                if (service instanceof ElectricCharging) {
                    history.append(((ElectricCharging) service).toString()).append("\n");
                } else if (service instanceof Mechanic) {
                    history.append(((Mechanic) service).toString()).append("\n");
                } else if (service instanceof Washings) {
                    history.append(((Washings) service).toString()).append("\n");
                }
            }

            if (history.length() > 20) { // تحقق من أن هناك عمليات مسجلة
                PrintDialog dialog = new PrintDialog(null, "سجل العمليات", history.toString());
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ لا توجد عمليات مسجلة حتى الآن!", "تنبيه", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // زر الرجوع
        btn5.addActionListener(e -> {
            frame.dispose(); // إغلاق النافذة
        });
    }
}
