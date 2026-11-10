

package stationmanagement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Mechanic extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private List<String> transactions;
    private static double basePrice = 5; // السعر الأساسي للخدمات

    public Mechanic(JFrame previousFrame) {
        setTitle("Mechanical services");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        transactions = new ArrayList<>(); // قائمة لحفظ العمليات
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1)); // ترتيب الأزرار في شبكة

        // إنشاء الأزرار
        JButton oilButton = createStyledButton("Oil change");
        JButton electricButton = createStyledButton("electricity");
        JButton sparkPlugButton = createStyledButton("Spark plugs");
        JButton showTransactionsButton = createStyledButton("View operations");
        JButton back = createStyledButton("Go Back");

        // إضافة الأحداث عند النقر على الأزرار
        oilButton.addActionListener(e -> showOilService());
        electricButton.addActionListener(e -> showElectricService());
        sparkPlugButton.addActionListener(e -> showSparkPlugService());
        showTransactionsButton.addActionListener(e -> showTransactions());
        back.addActionListener(e -> {
            dispose(); // إغلاق النافذة الحالية
            previousFrame.setVisible(true); // إظهار النافذة السابقة
        });

        // إضافة الأزرار إلى الواجهة
        mainPanel.add(oilButton);
        mainPanel.add(electricButton);
        mainPanel.add(sparkPlugButton);
        mainPanel.add(showTransactionsButton);
        mainPanel.add(back);
        contentPanel.add(mainPanel, "Main");
        add(contentPanel);
        setVisible(true);
    }

    // دالة لإنشاء زر بتنسيق موحد
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        return button;
    }

    // عرض خدمة تغيير الزيت
    private void showOilService() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField kmField = new JTextField();
        kmField.setFont(new Font("Arial", Font.PLAIN, 18));

        String[] oilTypes = {" oil C ", " oil B", " oil A"};
        JComboBox<String> oilTypeBox = new JComboBox<>(oilTypes);
        oilTypeBox.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton submitButton = createStyledButton("Cost calculation");
        JButton backButton = createStyledButton("Back");

        // إضافة الحقول والعناصر إلى اللوحة
        panel.add(new JLabel("Number of kilometers:")).setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(kmField);
        panel.add(new JLabel("Oil type:")).setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(oilTypeBox);
        panel.add(submitButton);
        panel.add(backButton);

        contentPanel.add(panel, "OilService");
        cardLayout.show(contentPanel, "OilService");

        submitButton.addActionListener(e -> {
            Integer km = Integer.parseInt(kmField.getText()) * 5; // حساب عدد الكيلومترات
            double cost = calculateCost(km);
            String selectedOil = (String) oilTypeBox.getSelectedItem();
            showCostScreen("Oil change - " + selectedOil.trim(), km, cost); // السعر الأساسي = الكيلومترات × 5
        });

        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Main"));
    }

    // عرض خدمة إصلاح الكهرباء
    private void showElectricService() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JButton frontLightButton = createStyledButton("headlight");
        JButton backLightButton = createStyledButton("Tail light");
        JButton backButton = createStyledButton("Back");

        panel.add(frontLightButton);
        panel.add(backLightButton);
        panel.add(backButton);

        contentPanel.add(panel, "ElectricService");
        cardLayout.show(contentPanel, "ElectricService");

        frontLightButton.addActionListener(e -> showCostScreen("headlight", 30, calculateCost(30)));
        backLightButton.addActionListener(e -> showCostScreen("Tail light", 25, calculateCost(25)));
        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Main"));
    }

    // عرض خدمة تغيير شمعات الاحتراق
    private void showSparkPlugService() {
        showCostScreen("Spark plugs", 60, calculateCost(60));
    }

    // حساب تكلفة الخدمة مع الضريبة
    private void showCostScreen(String serviceType, double basePrice, double totalCost) {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        double tax = totalCost - basePrice;
        JLabel basePriceLabel = new JLabel("Basic cost: " + String.format("%.2f", basePrice) + " Dinar");
        JLabel taxLabel = new JLabel("Add tax 16%: " + String.format("%.2f", tax) + " Dinar");
        JLabel costLabel = new JLabel("Total cost: " + String.format("%.2f", totalCost) + " Dinar");
        basePriceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        taxLabel.setFont(new Font("Arial", Font.BOLD, 18));
        costLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JButton confirmButton = createStyledButton("approval");
        JButton backButton = createStyledButton("Back");

        confirmButton.addActionListener(e -> {
            saveTransaction(serviceType, totalCost);
            showTransactions();
        });

        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Main"));

        panel.add(basePriceLabel);
        panel.add(taxLabel);
        panel.add(costLabel);
        panel.add(confirmButton);
        panel.add(backButton);

        contentPanel.add(panel, "CostScreen");
        cardLayout.show(contentPanel, "CostScreen");
    }

    private double calculateCost(double basePrice) {
        return basePrice * 1.16; // إضافة الضريبة بنسبة 16%
    }

    // حفظ المعاملة في القائمة
    private void saveTransaction(String serviceType, double cost) {
        int index = (int) (Math.random() * 1000);
        transactions.add("Service: " + serviceType + " | Cost: " + String.format("%.2f", cost) + " Dinar | Index number: " + index);
    }

    // عرض قائمة العمليات السابقة
    private void showTransactions() {
        JPanel panel = new JPanel(new GridLayout(transactions.size() + 1, 1));
        for (String transaction : transactions) {
            JLabel label = new JLabel(transaction);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            panel.add(label);
        }
        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> cardLayout.show(contentPanel, "Main"));
        panel.add(backButton);

        contentPanel.add(panel, "Transactions");
        cardLayout.show(contentPanel, "Transactions");
    }

    // إرجاع قائمة العمليات كسلسلة نصية
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List of Transactions:\n");
        for (String transaction : transactions) {
            sb.append(transaction).append("\n");
        }
        return sb.toString();
    }

    // طباعة العمليات
    public void printTransactions() {
        StringBuilder transactionsText = new StringBuilder();
        for (String transaction : transactions) {
            transactionsText.append(transaction).append("\n");
        }
        PrintDialog dialog = new PrintDialog(null, "سجل عمليات الصيانة", transactionsText.toString());
        dialog.setVisible(true);
    }
}
