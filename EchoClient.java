/*
* EchoClient.java
*
* The client class allows the user to interact with server by sending and viewing images
* sent by the user and other users.The class is also used to interact with the gui and
* generate images. The class has no predefined order and depends on sending commands between
* the server and the client such as a (s)ending or (r)ecieving.
 */

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class EchoClient {
        Socket socket = null;
        DataInputStream input = null;
        DataInputStream serverInput = null;
        DataOutputStream out = null;

        PrintWriter ow = null;

        public void createSocket()
        {
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

                    ow = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                } catch (ConnectException c) {
                    System.out.println("Invalid Socket");
                } catch (IOException u) {
                    System.out.println(u);
                } catch (NumberFormatException n) {
                    System.out.println("Must be a number");
                }
            }
        }

        public void send(String fileName) {
            try {

                System.out.println(fileName);

                ow.println("s");
                ow.flush();

                //Send Image
                assert out != null;

                BufferedImage image = ImageIO.read(getClass().getResource("ImagesClient/"+fileName));


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                out.write(size);
                out.write(byteArrayOutputStream.toByteArray());
                out.flush();
                System.out.println("Sending Image");

            }

            catch(IOException i)
            {
                System.out.println(i);
            }

            catch (NullPointerException n) {
                return;
            }
        }

        public void receive()
        {
            try {
                //Receive all images from server

                ow.println("r");
                ow.flush();


                int fileCount = serverInput.readInt();
                File[] files = new File[fileCount];

                for (int i = 0; i < fileCount; i++) {
                    String fileName = serverInput.readUTF();

                    byte[] sizeAr = new byte[4];
                    serverInput.read(sizeAr);
                    int sizeReceive = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[sizeReceive];
                    serverInput.read(imageAr);

                    BufferedImage imageReceive = ImageIO.read(new ByteArrayInputStream(imageAr));

                    ImageIO.write(imageReceive, "jpg", new File("src/ReceivedImages/" + fileName));
                    System.out.println("Receiving Image");
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
