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
        BufferedOutputStream bos = null;

        try
        {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        }

        catch (IOException u) {
             System.out.println(u);
        }

        String line = "";
        String serverLine = "";

            try {

                //Receive Image
                byte[] sizeAr = new byte[4];
                in.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                byte[] imageAr = new byte[size];
                in.read(imageAr);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                File[] serverFiles = new File("src/Client/Images").listFiles();
                System.out.println(serverFiles.length);

                File file = new File("src/Server/Images/test"+serverFiles.length+".jpg");

                ImageIO.write(image,"jpg", file);


                //Send all Server.Images to user
                File[] files = new File("src/Server/Images").listFiles();
                out.writeInt(files.length);


                for(File f: files)
                {
                    String name = file.getName();
                    out.writeUTF(name);

                    BufferedImage imageSend = ImageIO.read(f);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(imageSend, "jpg", byteArrayOutputStream);
                    byte[] sizeSend = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    out.write(sizeSend);
                    out.write(byteArrayOutputStream.toByteArray());
                    out.flush();
                }




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
