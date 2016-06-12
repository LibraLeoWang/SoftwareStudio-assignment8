package com.example;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Gary on 16/5/28.
 */
public class Server extends JFrame implements Runnable{
    private Thread thread;
    private ServerSocket servSock;
    private JTextArea textArea;
    public Server(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setResizable(false);
        this.setVisible(true);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(500, 300));

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.add(textArea);
        try {
            // Detect server ip
            InetAddress IP = InetAddress.getLocalHost();
            textArea.append("IP of my system is : "+IP.getHostAddress() + "\n");
            textArea.append("Waitting to connect......\n");

            // Create server socket
            servSock = new ServerSocket(2000);

            // Create socket thread
            thread = new Thread(this);
            thread.start();
        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        } finally{

        }
    }

    @Override
    public void run(){
        // Running for waitting multiple client
        BufferedReader reader;
        while(true){
            try{
                // After client connected, create client socket connect with client
                Socket clntSock = servSock.accept();
                InputStream in = clntSock.getInputStream();
                reader = new BufferedReader(new InputStreamReader(clntSock.getInputStream()));
                String line = reader.readLine();
                textArea.append("The result from APP: " + line + "\n");
                System.out.println("Connected!!");

                // Transfer data
                byte[] b = new byte[1024];
                int length;

                length = in.read(b);
                String s = new String(b);
                System.out.println("[Server Said]" + s);

            }
            catch(Exception e){
                System.out.println("Error: "+e.getMessage());
                break;
            }
        }
    }
}
