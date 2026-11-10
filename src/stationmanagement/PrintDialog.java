///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package stationmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintDialog extends JDialog {

    public PrintDialog(JFrame owner, String title, String text) {
        super(owner, title, true); // `true` لجعل مربع الحوار مشروطًا
        setSize(500, 400);
        setLocationRelativeTo(owner); // توسيط مربع الحوار بالنسبة للإطار الرئيسي

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton closeButton = new JButton("إغلاق");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // إغلاق مربع الحوار
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }
}

