package Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class EchoClient {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataInputStream serverInput = null;
    private DataOutputStream out = null;
    private BufferedInputStream bis = null;

    public EchoClient(int port)
    {
        try
        {

            socket = new Socket("localhost", port);
            System.out.println("Connected");

            input = new DataInputStream(System.in);

            serverInput = new DataInputStream(socket.getInputStream());

            out = new DataOutputStream(socket.getOutputStream());

            bis = new BufferedInputStream(socket.getInputStream());
        }

        catch(IOException u)
        {
            System.out.println(u);
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
                BufferedImage image = ImageIO.read(new File("./" + line));
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

                    ImageIO.write(imageReceive,"jpg", new File("./RecievedImages/"+fileName));
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

    public static void main(String[] args) throws SocketException
    {
        EchoClient client = new EchoClient(1978);
    }

}
