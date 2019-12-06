
import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    private static final int PORT_NUMBER=1034;
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server started. Listens on port "+PORT_NUMBER);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Successfully connected");
            String req, resp;
            while((req = in.readLine())!=null){

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        finally{
            System.out.println("Shutting down server");
        }
    }
}
