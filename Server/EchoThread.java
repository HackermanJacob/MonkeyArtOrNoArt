package Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class EchoThread extends Thread
{
    protected Socket socket;
    protected int totalImage;

    public EchoThread(Socket clientSocket, int totalImage)
    {
        this.socket = clientSocket;
        this.totalImage = totalImage;
    }

    public void run()
    {
        DataInputStream in = null;
        DataOutputStream out = null;
        Scanner s = null;

        try
        {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        }

        catch (IOException u) {
             System.out.println(u);
        }

        String line = "";
        String serverLine = "";

            try {
                byte[] sizeAr = new byte[4];
                in.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                byte[] imageAr = new byte[size];
                in.read(imageAr);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                File file = new File("src/Server/Images/test"+totalImage+".jpg");

                ImageIO.write(image,"jpg", file);

            } catch (IOException i) {
                System.out.println(i);
            }


        System.out.println("Closing connection");

        try {
            assert socket != null;
            socket.close();
            in.close();
            out.close();

        }

        catch (IOException i) {
            System.out.println(i);
        }
    }

}
