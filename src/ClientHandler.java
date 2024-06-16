import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ClientHandler extends Thread {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    static File id = new File("src/id");
    static int[] a;
    Semaphore semaphore;

    public ClientHandler(Socket socket,Semaphore semaphore){
        this.socket=socket;
        this.semaphore=semaphore;
        try {
            inputStream=socket.getInputStream();
            outputStream=socket.getOutputStream();
            a=check();
            for(int i=0;i<24;i++){
                socket.getOutputStream().write(a[i]);
                //System.out.println(a[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    
        public static void writeId(int i) throws IOException{
              FileWriter fw = new FileWriter(id, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(i));
            bw.newLine();
            bw.close();
            fw.close();
        }


        public static int[] check(){
            String l=null;
            int[] a=new int[24];
            for(int i=0;i<24;i++){
                a[i]=1;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(id))) {
                while((l=reader.readLine())!=null&&!l.equals("")){
                int n= Integer.parseInt(l);
                a[n-1]=0;
                }
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return a;
    }

    @Override
    public void run(){
        try {
            while(true){
            a=check();
            for(int i=0;i<24;i++){
            socket.getOutputStream().write(a[i]);
            //System.out.println(a[i]);
        }

        int i=inputStream.read();
        if(i>0&&i<25){

            semaphore.acquire();
        a=check();
        if(a[i-1]==0) outputStream.write(0);
        else {outputStream.write(1);
            writeId(i);
            
        }
        semaphore.release();
        }


        }
        } catch (Exception e) {
            // TODO: handle exception
        }
    
 }
}
