package hw4_20001983_NgoPhuongTrang.bai7;

import javax.swing.*;

public class Run {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankView postOfficeView = new BankView();
            postOfficeView.setLayout(null);
            postOfficeView.setVisible(true);
        });
    }
}
