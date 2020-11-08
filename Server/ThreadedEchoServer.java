

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ThreadedEchoServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serversocket = null;
        Socket socket = null;
        Scanner s = null;
        File file = null;
        int totalImages = 0;

        s = new Scanner(System.in);
        System.out.print("Enter In a port for the server: ");
        int port = Integer.parseInt(s.nextLine());

        try
        {

            serversocket = new ServerSocket(port);
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
}
