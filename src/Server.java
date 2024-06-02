import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT=8083;
    static final int MAX_CLIENT=10000;
    static int[] a= new int[24];
    public static void main(String[] args) throws Exception {

        try {
        ServerSocket serverSocket = new ServerSocket(PORT);//tao socket va lang nghe
        while(true){
        Socket clientSocket = serverSocket.accept();// chap nhan ket noi
        System.out.println("da ket noi");
        // trao doi thong tin
        ClientHandler clientHandler = new ClientHandler(clientSocket);
         clientHandler.start();

        }
        }
         catch (Exception e) {
           e.printStackTrace();
        }

    

    }
}

