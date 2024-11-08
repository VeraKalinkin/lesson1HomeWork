package server;

public class Main{
    public static void main(String[] args){
        ServerWindow serverWindow = new ServerWindow();
        ClientGUI clientGUI = new ClientGUI(serverWindow, "Max");
        ClientGUI clientGUI1 = new ClientGUI(serverWindow, "Jack");

        clientGUI.setLocation(serverWindow.getX() - clientGUI1.getWidth(), serverWindow.getY());
        clientGUI1.setLocation(serverWindow.getX() + clientGUI1.getWidth(), serverWindow.getY());

    }
    }
