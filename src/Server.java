import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Server {
    static final int PORT=8083;
    static final int MAX_CLIENT=10000;
    static int[] a= new int[24];
    static Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) throws Exception {

        try {
        ServerSocket serverSocket = new ServerSocket(PORT);//tao socket va lang nghe
        int i=0;
        while(i<MAX_CLIENT){
        Socket clientSocket = serverSocket.accept();// chap nhan ket noi
        System.out.println("da ket noi");
        // trao doi thong tin

        ClientHandler clientHandler = new ClientHandler(clientSocket,semaphore);
         clientHandler.start();
         i++;
        }
        }
         catch (Exception e) {
           e.printStackTrace();
        }

    

    }
}

