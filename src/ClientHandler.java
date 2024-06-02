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

public class ClientHandler extends Thread {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    static File id = new File("src/id");
    static int[] a;
    public ClientHandler(Socket socket){
        this.socket=socket;
        try {
            inputStream=socket.getInputStream();
            outputStream=socket.getOutputStream();
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
        synchronized(this){
        int i=inputStream.read();
        if(i>0&&i<25){
        if(a[i-1]==0) outputStream.write(0);
        else {outputStream.write(1);
            writeId(i);
            
        }
        }
        }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
    
 }
}
