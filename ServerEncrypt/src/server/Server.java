package server;

import generators.RSAGenerator;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.ArrayList;


public class Server {
    private static final int PORT_NUMBER = 1044;

    private static ArrayList<String> docs = new ArrayList<>();

    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started. Listens on port " + PORT_NUMBER);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Successfully connected");
            String req, resp;
            while(true){
                req = in.readLine();
                if(req.equalsIgnoreCase(">exit")){
                    System.out.println("Shutting down");
                    serverSocket.close();
                    break;
                }
                if(req.substring(0,5).equals(">get ")){
                    int index = Integer.parseInt(req.substring(5));
                    if(docs.size()>index){
                        out.println(docs.get(index));
                    }else{
                        out.println("No file with such index found on server");
                    }
                }
                if(req.substring(0, 8).equals(">create ")){
                    docs.add(req.substring(8));
                    out.println("Created successfully with index " + (docs.size() - 1));
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            System.out.println("Shutting down server");
        }
    }
}
