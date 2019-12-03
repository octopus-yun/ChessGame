package chess;

import chess.model.GameState;
import chess.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    int test = 0;
    static List<Socket> list = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        try {
            ServerSocket serverSocket = new ServerSocket(43200);
            while(true) {
                System.out.println("Server: waiting for client");
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    System.out.println("accept");
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    if(list.size()==0){
                        out.writeObject(Player.WHITE);
                    }
                    if(list.size()==1){
                        out.writeObject(Player.BLACK);
                    }
                    list.add(socket);
                    System.out.println(list.size());
                    Thread receivingThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                    handleClient(socket);
                                } catch (IOException e) {
                                    e.getStackTrace();
                                }
                        }
                    });
                    receivingThread.start();
                }
            }
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }

    private static void handleClient(Socket socket) throws IOException {
        //list.add(socket);
        //System.out.println(list.size());

        GameState temp = new GameState();

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            GameState receiveObject = (GameState) in.readObject();
            temp = receiveObject;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

       if(list.size()==2) {
            for (Socket s : list) {
                System.out.println("huhu");
                System.out.println("mumu");
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                System.out.println("jiji");
                //try {
                    //GameState receiveObject = (GameState) in.readObject();
                    //GameState g = new GameState();
                    System.out.println("lili");
                    out.writeObject(temp);
                    System.out.println("dada");
                //} catch (ClassNotFoundException e) {
                    //e.printStackTrace();
                //}
            }
        }
    }
}
