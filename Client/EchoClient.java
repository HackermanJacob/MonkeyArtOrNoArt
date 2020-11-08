

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args)
    {
        Socket socket = null;
        DataInputStream input = null;
        DataInputStream serverInput = null;
        DataOutputStream out = null;
        BufferedInputStream bis = null;

        boolean valid = false;
        while (!valid) {
            try {
                Scanner s = new Scanner(System.in);
                System.out.print("Enter In a port to join: ");
                int port = Integer.parseInt(s.nextLine());

                socket = new Socket("localhost", port);
                valid = true;

                System.out.println("Connected");

                input = new DataInputStream(System.in);

                serverInput = new DataInputStream(socket.getInputStream());

                out = new DataOutputStream(socket.getOutputStream());

                bis = new BufferedInputStream(socket.getInputStream());
            } catch (ConnectException c) {
                System.out.println("Invalid Socket");
            } catch (IOException u) {
                System.out.println(u);
            }
        }


        String line = "";
        String serverLine = "";

        while(!line.equals("Over"))
        {
            try
            {
                assert input != null;
                System.out.print("Enter picture name: ");
                line = input.readLine();
                assert out != null;


                //Send Image
                BufferedImage image = ImageIO.read(new File("./Images/demo/" + line));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                out.write(size);
                out.write(byteArrayOutputStream.toByteArray());
                out.flush();

                line = "Over";

                //Receive all images from server

                int fileCount = serverInput.readInt();
                File[] files = new File[fileCount];

                for(int i = 0; i<fileCount;i++)
                {
                    String fileName = serverInput.readUTF();

                    byte[] sizeAr = new byte[4];
                    serverInput.read(sizeAr);
                    int sizeReceive = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[sizeReceive];
                    serverInput.read(imageAr);

                    BufferedImage imageReceive = ImageIO.read(new ByteArrayInputStream(imageAr));

                    ImageIO.write(imageReceive,"jpg", new File("./ReceivedImages/"+fileName));
                }


            }

            catch(IOException i)
            {
                System.out.println(i);
            }

            catch (NullPointerException n) {
                return;
            }
        }
    }

}
