package hw4_20001983_NgoPhuongTrang.bai7;

import hw4_20001983_NgoPhuongTrang.bai3.LinkedListQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class BankView extends JFrame {
    final Color[] COLORS = {Color.PINK, Color.ORANGE, Color.RED, Color.CYAN, Color.GREEN};
    LinkedListQueue<Customer> waitLine = new LinkedListQueue<>();
    ServiceDesk[] serviceDesks = new ServiceDesk[5];
    int waitNum = 0;

    public BankView() {
        setTitle("Phuong Trang's BANK");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < serviceDesks.length; i++)
            serviceDesks[i] = new ServiceDesk();

        TextField tf = new TextField("Available seats: 40", 20);
        tf.setBounds(270, 500, 220, 30);
        add(tf);

        Button b1 = new Button("NEW CUSTOMER");
        b1.setBounds(530, 500, 120, 30);
        add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (waitLine.size() < 40) {
                    waitLine.enqueue(new Customer(++waitNum));
                    tf.setText("Available seats: " + (40 - waitLine.size()));
                    repaint();
                } else
                    tf.setText("Sorry, currently full. Plz wait!");
            }
        });

        Button b2 = new Button("START WORKING");
        b2.setBounds(100, 500, 120, 30);
        add(b2);

        Timer t = new Timer(1700, new ActionListener() {
            final double[] press = {0, 0, 0, 0, 0};

            @Override
            public void actionPerformed(ActionEvent e) {
                for (ServiceDesk serviceDesk : serviceDesks) {
                    if (waitLine.first() == null)
                        break;
                    if (!serviceDesk.inService) {
                        serviceDesk.startServe(waitLine.dequeue());
                        if (b1.isShowing())
                            tf.setText("Available seats: " + (40 - waitLine.size()));
                    }
                }
                repaint();
                for (int i = 0; i < serviceDesks.length; i++) {
                    if (serviceDesks[i].inService) {
                        if (serviceDesks[i].finishServe(press[i])) {
                            press[i] = 0;
                            repaint();
                        } else
                            press[i] += 0.25;
                    }
                }
            }
        });
        t.setInitialDelay(0);
        t.setRepeats(true);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.start();
                remove(b2);
                Button b3 = new Button("NO NEW CUSTOMER");
                b3.setBounds(100, 500, 130, 30);
                add(b3);
                b3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(b1);
                        b3.setLabel("CLOSE BANK");
                        boolean finish = true;
                        for (ServiceDesk serviceDesk : serviceDesks) {
                            if (serviceDesk.currentCustomer != null) {
                                finish = false;
                                break;
                            }
                        }
                        if (waitLine.size() == 0 && finish) {
                            t.stop();
                            tf.setText("CLOSED. Total customers served: " + waitNum);
                            remove(b3);
                            Button b4 = new Button("ANOTHER DAY");
                            b4.setBounds(530, 500, 120, 30);
                            add(b4);
                            b4.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    remove(b4);
                                    add(b1);
                                    add(b2);
                                    tf.setText("ANOTHER DAY IN PT BANK!");
                                    waitNum = 0;
                                    for (ServiceDesk serviceDesk : serviceDesks)
                                        serviceDesk.reset();
                                    repaint();
                                }
                            });
                        } else {
                            tf.setText("Still serving remaining customers.");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawSeats(g);
        drawWaitingCustomers(g, waitLine);
        drawDesks(g, serviceDesks);
        drawInServiceCustomers(g, serviceDesks);
    }

    public void drawSeats(Graphics g) {
        for (int i = 0; i < 40; i++) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(60 * (i % 10) + 90, 80 * (i / 10) + 200, 50, 50);
        }
    }

    public void drawWaitingCustomers(Graphics g, LinkedListQueue<Customer> waitLine) {
        Iterator<Customer> iterator = waitLine.iterator();
        while (iterator.hasNext()) {
            Customer currentCus = iterator.next();
            int order = (currentCus.num - 1) % 40;
            g.setColor(COLORS[order % 5]);
            g.fillRect(60 * (order % 10) + 90, 80 * (order / 10) + 200, 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("Cus " + currentCus.num, 60 * (order % 10) + 95, 80 * (order / 10) + 225);
        }
    }

    public void drawDesks(Graphics g, ServiceDesk[] serviceDesks) {
        for (int i = 0; i < serviceDesks.length; i++) {
            g.setColor(Color.YELLOW);
            int xPos = 120 * (i + 1) + 5;
            g.fillRect(xPos - 5, 40, 60, 60);
            g.setColor(Color.BLACK);
            g.drawString("DESK " + (i + 1), xPos, 55);
            g.drawString("Cus served:  " + serviceDesks[i].numCusServed, xPos, 75);
            g.drawString("Hours served:  " + serviceDesks[i].timeServed, xPos, 90);
        }
    }

    public void drawInServiceCustomers(Graphics g, ServiceDesk[] serviceDesks) {
        for (int i = 0; i < serviceDesks.length; i++) {
            if (serviceDesks[i].inService) {
                Customer customer = serviceDesks[i].currentCustomer;
                g.setColor(COLORS[(customer.num - 1) % 40 % 5]);
                int xPos = 120 * (i + 1) + 5;
                g.fillRect(xPos - 5, 125, 50, 50);
                g.setColor(Color.BLACK);
                g.drawString("Cus " + customer.num, xPos, 140);
                g.drawString(customer.request, xPos, 155);
                g.drawString(customer.timeToServe + " h", xPos, 170);
            }
        }
    }
}
