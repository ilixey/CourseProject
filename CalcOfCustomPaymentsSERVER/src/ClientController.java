import service.AdminService;
import service.AuthorizationService;
import service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

public class ClientController implements Runnable {
    private Socket clientSocket;
    private Connection connection;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private AuthorizationService authorizationService;
    private AdminService adminService;
    private UserService userService;


    public ClientController (Socket clientSocket) {
        this.clientSocket = clientSocket;
        authorizationService = new AuthorizationService();
        adminService = new AdminService();
        userService = new UserService();
        try {
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = "";
                boolean isUserDisconnected = false;

                request = (String) objectInputStream.readObject();
                String[] creds = request.split(" ");
                String response = "";
                ArrayList<String> responseS = null;
                System.out.println(request);
                switch (creds[0]) {
                    case "AUTHORISATION":
                        response = authorizationService.authorization(creds[1], creds[2]);
                        break;
                    case "DISCONNECT":
                        authorizationService.disconnect(Integer.parseInt(creds[1]));
                        isUserDisconnected = true;
                        break;
                    case "GET_USERS":
                        responseS = adminService.getUsers();
                        break;
                    case "GET_CLIENTS":
                        responseS = adminService.getClients();
                        break;
                    case "EDIT_CLIENT":
                        response = adminService.editClient(Integer.parseInt(creds[1]), creds[2], creds[3]);
                        break;
                    case "ADD_CLIENT":
                        response = userService.addClient(creds[1], creds[2], creds[3]);
                        break;
                    case "NEW_BV":
                        response = adminService.editBV(Integer.parseInt(creds[1]));
                        break;
                    case "NEW_RC":
                        response = adminService.editRC(creds[1], Integer.parseInt(creds[2]));
                        break;
                    case "DELETE_USER":
                        response= adminService.deleteUser(Integer.parseInt(creds[1]));
                        break;
                    case "DELETE_CLIENT":
                        response= adminService.deleteClient(Integer.parseInt(creds[1]));
                        break;
                    case "ADD_USER":
                        response = adminService.addUser(creds[1], creds[2], Boolean.parseBoolean(creds[3]), creds[4], creds[5], creds[6], creds[7]);
                        break;
                    case "EDIT_USER":
                        response = adminService.editUser(Integer.parseInt(creds[1]), creds[2], creds[3]);
                        break;
                    case "CHECK_CLIENT_PASSPORT":
                        response = userService.checkClient(creds[1]);
                        break;
                    case "SHOW_PROFILE":
                        response = userService.profileInfo(creds[1]);
                        break;
                    case "CHANGE_STATUS_PSYCHO":
                        response = userService.updateVacation(creds[1], Integer.parseInt(creds[2]));
                        break;
                    case "SHOW_BV":
                        response = userService.showBV();
                        break;
                    case "SHOW_RATES":
                        response = userService.showRates();
                        break;
                    case "GET_CHECKS_UNPAID_UNCONF":
                        responseS = userService.showChecksUnpaidUnconf();
                        break;
                    case "EDIT_CHECK_STATUSES":
                        response = userService.editCheckUser(Integer.parseInt(creds[1]), creds[2], creds[3]);
                        break;
                    case "EDIT_CHECKS":
                        responseS = adminService.showChecksEdit();
                        break;
                    case "DELETE_CHECK":
                        response = adminService.deleteCheck(Integer.parseInt(creds[1]));
                        break;
                    case "ADD_CHECK":
                        response = userService.addCheck(creds[1], creds[2]);
                        break;
                    case "ADD_ORDER":
                        response = userService.addOrder(creds[1], Integer.parseInt(creds[2]), Integer.parseInt(creds[3]));
                        break;
                    case "BAN_USER":
                        response = adminService.ban(Integer.parseInt(creds[1]));
                        break;
                    default:
                        response = "ERROR NOT_SUCH_METHOD";
                        break;
                }

                if (isUserDisconnected) {
                    System.out.println("DISCONNECTED ID: " + creds[1]);
                    break;
                }

                if (responseS == null) {
                    objectOutputStream.writeObject(response);
                } else {
                    objectOutputStream.writeObject(responseS);
                }


//                System.out.println(request);
            }
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
