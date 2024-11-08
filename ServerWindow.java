package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerWindow extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 450;
    private static final int WIDTH = 350;
    private static final int HEIGHT = 100;

    private final JTextArea log = new JTextArea();
    private static boolean isServerWorking;

    public static void main(String[] args){
        new ServerWindow();

        if (!Files.exists(Path.of("c:\\logFile.txt"))){
        try{
            File logFile = new File("logFile.txt");
            if (logFile.createNewFile()){
                System.out.println("Файл создан");
            }else{
                System.out.println("Файл уже существует");
            }
        }catch(IOException exception){
            System.out.println("Ошибка при создании");
            exception.printStackTrace();
        }
        }
    }

    ServerWindow(){
        isServerWorking = false;
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = false;
                System.out.println("Server stopped\n");
                try {
                    FileWriter fileWriter = new FileWriter("logFile.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.append("Server stopped");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    log.setText("Server stopped");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                System.out.println("Server started\n");
                try {
                    FileWriter fileWriter = new FileWriter("logFile.txt", true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.append("Server started");
                    log.setText("Server started");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 3));
        setLocationRelativeTo(null);
        add(btnStart);
        add(log);
        add(btnStop);
        setVisible(true);

    }

    public static boolean getWorkingState(){
        return isServerWorking;
    }

}


