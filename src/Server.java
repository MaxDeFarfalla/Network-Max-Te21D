import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void main(String[] args) {
        ServerSocket server = null;
        Socket client;

        //Default port number we are going to use
        int portNumber = 50000;
        if (args.length >= 0) {
            portNumber = Integer.parseInt(args[0]);
        }
    // Create Server side socket
    try {
            server = new ServerSocket(portNumber);
    }catch (IOException ie) {
        System.out.println("Cannot open port: " + ie);
        System.exit(1);
    }
        System.out.println("ServerSocket is created" + server);

    //Wait for data from client to reply
        while (true) {
            try {
                //Listens for a connection to be made to this socket
                // and accepts it. The method blocks until a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + "Client port" + clientPort);

                // Read data from client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message received from client = " + msgFromClient);

                //Send response to Client
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")){
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello " + msgFromClient;
                    pw.println(ansMsg);
                }

                //Close sockets
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")){
                    server.close();
                    client.close();

                    break;
                }

            } catch (IOException ie){
                //Error msg
                System.out.println("Cannot accept connection: " + ie);
            }
        }

    }
}
