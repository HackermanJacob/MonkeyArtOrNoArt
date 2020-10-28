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

    public EchoClient(int port)
    {
        try
        {

            socket = new Socket("localhost", port);
            System.out.println("Connected");

            input = new DataInputStream(System.in);

            serverInput = new DataInputStream(socket.getInputStream());

            out = new DataOutputStream(socket.getOutputStream());
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

                BufferedImage image = ImageIO.read(new File("src/" + line));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                out.write(size);
                out.write(byteArrayOutputStream.toByteArray());
                out.flush();

                line = "Over";

            }

            catch(IOException i)
            {
                System.out.println(i);
            }
        }

    }

    public static void main(String[] args) throws SocketException
    {
        EchoClient client = new EchoClient(1978);
    }

}
