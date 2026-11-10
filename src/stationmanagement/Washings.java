
package stationmanagement;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Washings {

    // مصفوفة لتخزين سجل عمليات الغسيل (100 عملية كحد أقصى)
    private static String[][] washHistory = new String[100][3]; 
    private static int washIndex = 0; // مؤشر لعدد العمليات
    private static String tax = "16"; // نسبة الضريبة (السعر × 1.16)
    private double totalPrice; // إجمالي السعر
    private String[] carTypes = { "1- small car", "2- saloon cars", "3- small transport bus", 
                                  "4- large transport bus", "5- vehicles" }; // أنواع السيارات المتاحة

    // المُنشئ الافتراضي
    public Washings() {
    }

    // مُنشئ يفتح نافذة اختيار نوع السيارة لغسلها
    public Washings(JFrame previousFrame) {
        JFrame frame = new JFrame("Wash service");
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(7, 1)); // ترتيب العناصر في شبكة من 7 صفوف وعمود واحد
        frame.setLocation(750, 400); // تعيين موقع النافذة على الشاشة

        // قائمة أسعار الغسيل لكل نوع من أنواع السيارات
        double[] prices = { 3, 4, 5, 6, 7 };

        // إنشاء أزرار لكل نوع من السيارات
        for (int i = 0; i < carTypes.length; i++) {
            JButton button = new JButton(carTypes[i]);
            double price = prices[i];
            int carTypeIndex = i;

            // عند الضغط على الزر، يتم حساب التكلفة الإجمالية وفتح نافذة التكلفة
            button.addActionListener(e -> {
                totalPrice = price * 1.16; // إضافة الضريبة
                CostWashing(frame, totalPrice, tax, price, carTypeIndex);
                frame.dispose(); // إغلاق النافذة الحالية
            });

            frame.add(button);
        }

        // زر الرجوع إلى النافذة السابقة
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            previousFrame.setVisible(true);
        });
        frame.add(backButton);

        frame.setVisible(true);
    }

    // نافذة لحساب تكلفة الغسيل وعرضها
    public void CostWashing(JFrame previousFrame, double totalPrices, String tax, double price, int carindex) {
        JFrame frame = new JFrame("washing cost");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(700, 350);
        frame.setLayout(new GridLayout(5, 1)); // تقسيم النافذة إلى 5 أقسام

        // عرض السعر الأساسي
        JLabel label1 = new JLabel("Price : " + price);
        label1.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
        label1.setFont(new Font("Arial", Font.BOLD, 20));

        // عرض نسبة الضريبة
        JLabel label2 = new JLabel(" tax : " + tax + " %");
        label2.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
        label2.setFont(new Font("Arial", Font.BOLD, 20));

        // عرض السعر الإجمالي بعد الضريبة
        JLabel label3 = new JLabel("total Price : " + String.format("%.2f", totalPrices));
        label3.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
        label3.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout()); // إنشاء لوحة للأزرار

        // زر تأكيد العملية
        JButton confirmButton = new JButton("Confirm the transaction");
        confirmButton.setPreferredSize(new Dimension(300, 60));

        // زر الرجوع
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(300, 60));

        // عند الضغط على زر التأكيد، يتم حفظ العملية وعرض رسالة تأكيد
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "تم تأكيد العملية بنجاح!");
            washHistory[washIndex][0] = carTypes[carindex]; // تخزين نوع السيارة
            washHistory[washIndex][1] = String.format("%.2f", totalPrice) + " دينار"; // تخزين التكلفة
            washHistory[washIndex][2] = String.valueOf(washIndex); // تخزين رقم العملية
            washIndex++;
            frame.dispose(); // إغلاق النافذة بعد الحفظ
        });

        // عند الضغط على زر الرجوع، يتم الرجوع إلى النافذة السابقة
        backButton.addActionListener(e -> {
            frame.dispose();
            previousFrame.setVisible(true);
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        frame.add(label1);
        frame.add(label2);
        frame.add(label3);
        frame.add(buttonPanel); // إضافة الأزرار داخل اللوحة

        frame.setVisible(true);
    }

    // دالة لتحويل سجل عمليات الغسيل إلى نص منسق للطباعة أو العرض
    @Override
    public String toString() {
        StringBuilder history = new StringBuilder("سجل عمليات الغسيل:\n");
        for (int i = 0; i < washIndex; i++) {
            history.append((i + 1)).append(". ")
                   .append(washHistory[i][0]).append(" - ") // نوع السيارة
                   .append(washHistory[i][1]).append(" - ") // التكلفة
                   .append("رقم العملية: ").append(washHistory[i][2]).append("\n"); // رقم العملية
        }
        return history.toString();
    }

    // دالة لطباعة سجل عمليات الغسيل باستخدام نافذة الطباعة
    public void printWashHistory() {
        PrintDialog dialog = new PrintDialog(null, "سجل عمليات الغسيل", this.toString());
        dialog.setVisible(true);
    }
}
