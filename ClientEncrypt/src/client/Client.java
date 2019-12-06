package client;

import generators.RSAGenerator;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.ArrayList;

public class Client {

    private final static int DEFAULT_PORT_NUMBER = 1044;
    private final static String DEFAULT_HOST_NAME = "127.0.0.1";
    private static int portNumber = DEFAULT_PORT_NUMBER;
    private static String hostName = DEFAULT_HOST_NAME;

    private static ArrayList<KeyPair> keyPairs;

    public static void main(String[] args) {
        try{
            Socket echoSocket = new Socket(hostName, portNumber);

            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected!");
            keyPairs = new ArrayList<>();
            String req, resp;

            while(true){
                req = stdin.readLine();
                if(req.length() > 4){
                    if(req.equalsIgnoreCase(">exit")){
                        System.out.println("Shutting down.");
                        echoSocket.close();
                        break;
                    }
                    if(req.substring(0,5).equals(">get ")){
                        out.println(req);
                        int index = Integer.parseInt(req.substring(5));
                        resp = RSAGenerator.decrypt(in.readLine(), keyPairs.get(index).getPrivate());
                        System.out.println(resp);
                    }
                    if(req.length()>8 && req.substring(0, 8).equals(">create ")){
                        KeyPair kp = RSAGenerator.generateKeyPair();
                        keyPairs.add(kp);
                        String cipherText = RSAGenerator.encrypt(req.substring(8), kp.getPublic());
                        out.println(">create " + cipherText);
                        resp = in.readLine();
                        System.out.println(resp);
                    }
                }
            }
        }catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Wrong host " + hostName);
            System.exit(1);
        }catch (IOException e) {
            System.out.println("Couldn't get I/O for connection " + hostName);
            System.exit(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
