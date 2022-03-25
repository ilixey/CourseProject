package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket {
    private int currentUserId;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientSocket() {
        try {
            socket = new Socket("localhost", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (socket != null){
            try {
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public void setRequest(String request){
        try {
                objectOutputStream.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRequest(){
        String answerRequest = "";
        try {
            answerRequest = (String)objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return answerRequest;
    }

    public ArrayList<String> getRequestList(){
        ArrayList<String> answerRequest = new ArrayList<>();
        try {
            answerRequest = (ArrayList<String>)objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return answerRequest;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }


    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void close() throws IOException {
        this.socket.close();
    }
}
