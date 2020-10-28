package Server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ThreadedEchoServer {
    private ServerSocket serversocket = null;
    private Socket socket = null;
    private Scanner s = null;
    private File file = null;
    public int totalImages = 0;

    public ThreadedEchoServer(int port){
        try
        {
            //System.out.println("Enter the path to create a directory: ");
            //Scanner sc = new Scanner(System.in);
            //String path = sc.nextLine();
            //path = path+"\\";
            //Creating a File object
            file = new File("src/Server/Images");
            //Creating the directory
            boolean bool = file.mkdir();
            if(bool){
                System.out.println("Directory created successfully");
            }else{
                System.out.println("Sorry couldn’t create specified directory");
            }

            serversocket = new ServerSocket(port);
            Socket socket = null;
        }

        catch (IOException u) {
            System.out.println(u);
        }

        while(true)
        {
            try
            {
                socket = serversocket.accept();
                System.out.println("Client joined");
                totalImages ++;
            }

            catch(NullPointerException n)
            {
                return;
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            new EchoThread(socket, totalImages).start();
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadedEchoServer server = new ThreadedEchoServer(1978);
    }
}
