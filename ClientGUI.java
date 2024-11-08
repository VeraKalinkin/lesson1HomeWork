package server;

import javax.management.timer.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Time;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;


    ClientGUI(ServerWindow serverWindow, String login){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat client");

        JPanel panelButton = new JPanel(new BorderLayout());
        JTextField tfMessage = new JTextField();
        panelButton.add(tfMessage, BorderLayout.CENTER);
        JButton btnSend = new JButton("Send");
        panelButton.add(btnSend, BorderLayout.EAST);
        add(panelButton, BorderLayout.SOUTH);

        JTextArea log = new JTextArea();
        log.setEditable(false);
        JScrollPane scrolling = new JScrollPane(log);
        add(scrolling);

        JPanel panelTop = getTopjPanel(login, log);
        add(panelTop, BorderLayout.NORTH);



        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tfMessage.getText();

                if (!message.isEmpty()){
                try {
                    FileWriter fileWriter = new FileWriter("logFile.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.append(login).append(" : ").append(message);
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                log.setText("");
                    try {
                        getUpdate(log);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    tfMessage.setText("");
                }
            }
        });


        setVisible(true);
    }

    private static JPanel getTopjPanel(String login, JTextArea log) {
        JPanel panelTop = new JPanel(new GridLayout(2, 3));
        JTextField tfIPAddress = new JTextField("127.0.0.1");
        panelTop.add(tfIPAddress);
        JTextField tfPort = new JTextField("8189");
        panelTop.add(tfPort);
        JTextField tfLogin = new JTextField(login);
        panelTop.add(tfLogin);
        JPasswordField tfPassword = new JPasswordField("123456");
        panelTop.add(tfPassword);
        JButton btnLogin = new JButton("Login");
        panelTop.add(btnLogin);
        JButton btnUpdate = new JButton("Update");
        panelTop.add(btnUpdate);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ServerWindow.getWorkingState()){
                    try {
                        log.setText("");
                        getUpdate(log);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ServerWindow.getWorkingState()){
                    try {
                        getUpdate(log);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        return panelTop;
    }

    static void getUpdate(JTextArea log) throws IOException {
        if (ServerWindow.getWorkingState()){
        BufferedReader reader = new BufferedReader(new FileReader("logFile.txt"));
        String line;
        while ((line = reader.readLine()) != null){
            if (!line.contains("Server")){
                log.append(line + "\n");
            }
        }
    }
    }
}

