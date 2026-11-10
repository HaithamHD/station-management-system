package stationmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ElectricCharging {

    // مصفوفة لتخزين بيانات عمليات الشحن (تتسع لـ 10 عمليات فقط)
    private static final double[][] chargingData = new double[10][3];
    private int counter = 0; // مؤشر عدد العمليات المُسجّلة
    private final int PricePerKilowatt = 8; // سعر الكيلوواط الواحد
    private Random random = new Random(); // كائن لإنشاء أرقام عشوائية (لرقم العملية)
    private double totalCost; // التكلفة الإجمالية

    private JFrame parentFrame; // تخزين مرجع للنافذة السابقة

    // المُنشئ: يأخذ مرجع النافذة السابقة وينشئ واجهة المستخدم
    public ElectricCharging(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        createGUI();
    }

    // إنشاء واجهة إدخال عدد الكيلوواطات المطلوبة
    private void createGUI() {
        JFrame frame = new JFrame("Kilowatts of Electricity");
        Container container = frame.getContentPane();
        GroupLayout groupLayout = new GroupLayout(container);
        container.setLayout(groupLayout);
        frame.setLocation(750, 400); // تعيين موقع النافذة على الشاشة

        groupLayout.setAutoCreateContainerGaps(true);

        // إنشاء العناصر الرسومية
        JLabel label_1 = new JLabel("Enter the number of kilowatts of electricity:");
        JTextField textField_1 = new JTextField();
        JButton button_1 = new JButton("Go Back");
        JButton button_2 = new JButton("Next");

        // تحديد كيفية توزيع العناصر في النافذة
        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(label_1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, 20)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textField_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, 20)
                                        .addComponent(button_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                )
                        )
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label_1)
                                .addComponent(textField_1)
                        )
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, 20)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button_1)
                                .addComponent(button_2)
                        )
        );

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // زر الرجوع إلى النافذة السابقة
        button_1.addActionListener(e -> {
            frame.dispose(); // إغلاق النافذة الحالية
            if (parentFrame != null) {
                parentFrame.setVisible(true); // إعادة إظهار النافذة السابقة
            }
        });

        // زر "التالي" لمعالجة الإدخال والانتقال لحساب التكلفة
        button_2.addActionListener(e -> {
            String text = textField_1.getText().trim(); // إزالة المسافات الزائدة

            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "⚠️ Please enter a valid number!");
                return;
            }

            try {
                int textFieldValue = Integer.parseInt(text); // تحويل النص إلى عدد

                if (textFieldValue <= 0) {
                    JOptionPane.showMessageDialog(frame, "❌ The value must be greater than zero!");
                } else {
                    calculateCost(textField_1); // استدعاء حساب التكلفة
                    frame.setVisible(false); // إخفاء النافذة الحالية
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "🚫 Please enter a valid numeric value!");
            }
        });
    }

    // دالة لحساب التكلفة الإجمالية وتسجيل البيانات
    private void calculateCost(JTextField textField_1) {
        double kilowatts = Double.parseDouble(textField_1.getText());
        double discountPerKilo = (kilowatts >= 60) ? 2 : (kilowatts >= 50) ? 1 : 0; // خصم على حسب الاستهلاك
        double totalDiscount = kilowatts * discountPerKilo;
        totalCost = (kilowatts * PricePerKilowatt) - totalDiscount;

        // عرض رسالة الخصم للمستخدم
        JOptionPane.showMessageDialog(null, "You got a discount of " + totalDiscount
                + " pennies", "Discount", JOptionPane.INFORMATION_MESSAGE);

        // تخزين بيانات الشحن في المصفوفة
        chargingData[counter][0] = random.nextInt(1000); // رقم عشوائي للعملية
        chargingData[counter][1] = kilowatts; // عدد الكيلوواطات
        chargingData[counter][2] = totalCost; // التكلفة النهائية بعد الخصم
        counter++;

        displayChargingDetails(); // عرض تفاصيل التكلفة
    }

    // دالة لعرض تفاصيل حساب التكلفة في نافذة جديدة
    private void displayChargingDetails() {
        JFrame frame = new JFrame("Charging Cost Calculation System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(750, 400); // تعيين موقع النافذة على الشاشة

        // لوحة تحتوي على المعلومات بشكل جدولي
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // إضافة هوامش داخلية
        panel.setBackground(new Color(230, 240, 250)); // تعيين لون الخلفية

        // تخصيص الخطوط لجعلها واضحة وكبيرة
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        JLabel label1 = new JLabel("Number of Kilowatts:");
        JLabel value1 = new JLabel(String.valueOf(chargingData[counter - 1][1]));
        JLabel label2 = new JLabel("Cost:");
        JLabel value2 = new JLabel(String.format("%.2f", chargingData[counter - 1][2]));
        JLabel label3 = new JLabel("Index Number:");
        JLabel value3 = new JLabel(String.valueOf(chargingData[counter - 1][0]));

        // تطبيق الخط على كل العناصر
        label1.setFont(labelFont);
        value1.setFont(labelFont);
        label2.setFont(labelFont);
        value2.setFont(labelFont);
        label3.setFont(labelFont);
        value3.setFont(labelFont);

        // توسيط النصوص
        label1.setHorizontalAlignment(JLabel.CENTER);
        value1.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        value2.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        value3.setHorizontalAlignment(JLabel.CENTER);

        // إضافة العناصر إلى اللوحة
        panel.add(label1);
        panel.add(value1);
        panel.add(label2);
        panel.add(value2);
        panel.add(label3);
        panel.add(value3);

        // زر إغلاق النافذة
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(100, 150, 200)); // لون جميل
        closeButton.setForeground(Color.WHITE);

        closeButton.addActionListener(e -> frame.dispose());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // دالة لطباعة سجل عمليات الشحن
    @Override
    public String toString() {
        StringBuilder history = new StringBuilder("🔋 سجل عمليات الشحن الكهربائي:\n");
        for (int i = 0; i < counter; i++) {
            history.append((i + 1)).append(". ")
                    .append("رقم العملية: ").append((int) chargingData[i][0]).append(" - ")
                    .append("كيلوواط: ").append(chargingData[i][1]).append(" - ")
                    .append("التكلفة: ").append(chargingData[i][2]).append(" دينار\n");
        }
        return history.toString();
    }


    public void printChargingHistory() {
    PrintDialog dialog = new PrintDialog(null, "سجل عمليات الشحن الكهربائي", this.toString());
    dialog.setVisible(true);
}
}
