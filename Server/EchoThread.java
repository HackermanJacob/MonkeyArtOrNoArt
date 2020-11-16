

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
        BufferedReader is = null;
        Scanner s = null;

        try
        {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        catch (IOException u) {
             System.out.println(u);
        }

        String line = "";
        String serverLine = "";

        while(!line.equals("q")) {
            try {
                line = is.readLine();
                System.out.println(line);


                if (line.equals("s")) {
                    //Receive Image
                    byte[] sizeAr = new byte[4];
                    in.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    in.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    File[] serverFiles = new File("./Images").listFiles();
                    //System.out.println(serverFiles.length)
                    if(image != null) {
                        ImageIO.write(image, "jpg", new File("./Images/test" + serverFiles.length + ".jpg"));
                    }
                    System.out.println("Received Image");
                }

                if (line.equals("r")) {
                    //Send all Server.Images to user
                    File[] files = new File("./Images").listFiles();
                    out.writeInt(files.length);


                    for (File f : files) {
                        String name = f.getName();
                        out.writeUTF(name);

                        BufferedImage imageSend = ImageIO.read(f);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(imageSend, "jpg", byteArrayOutputStream);
                        byte[] sizeSend = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                        out.write(sizeSend);
                        out.write(byteArrayOutputStream.toByteArray());
                        out.flush();
                    }

                    System.out.println("Sending Images");
                }
            }

         catch (IOException i) {
        System.out.println(i);
         }


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
