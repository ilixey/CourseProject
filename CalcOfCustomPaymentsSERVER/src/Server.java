import db.DBConnector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(6666);
            DBConnector.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (serverSocket != null) {
            while (true){
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null){
                    ClientController client = new ClientController(socket);
                    Thread thread = new Thread(client);
                    thread.start();
                }
            }
        }
    }
}
