package chess;

import chess.model.Chess;
import chess.model.GameState;
import chess.model.Model;
import chess.model.Player;
import chess.view.ChessController;
import chess.view.ChessView;
import chess.view.Controller;
import chess.view.View;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class Client {

    private static final int PORT = 43200;
    private static final String HOST = "localhost";

    public static void main (String [] args) throws IOException {
        int test = 0;
        Socket socket = new Socket (HOST,PORT);
        System.out.println("Client: connected to server.");
        Model model = new Chess();
        Model mo = new Chess();
        AtomicReference<Controller> controller = new AtomicReference<>(new ChessController(model));

        View view = new ChessView(model, controller.get());
        controller.get().setView(view);
        model.addPropertyChangeListener(view);
        controller.get().start();

        ObjectInputStream initial = new ObjectInputStream(socket.getInputStream());

        try{
            while (true) {
                System.out.println("hh");
                boolean check = model.checkifsame(model.getState(),mo.getState());
                System.out.println("check: " + check);

                //mo.print();
                //model.print();
                if(check && ((Player) initial.readObject() == Player.WHITE)){
                    System.out.println("haha");
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    //mo.print();
                    //model.print();
                    do {

                    }while(model.checkifsame(mo.getState(),model.getState()));
                    System.out.println("1. fertig");
                    out.writeObject(model.getState());
                }
                else {
                    System.out.println("lala");
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    System.out.println("here");
                    GameState receiveObject = (GameState) in.readObject();
                    //model.getState().setgameState(receiveObject);
                    model.print();
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.flush();
                    System.out.println("end1");
                    System.out.println(model.checkifsame(model.getState(),receiveObject));
                    if(!model.checkifsame(model.getState(),receiveObject)){
                        model.getState().setgameState(receiveObject);
                        do{

                        }while(model.checkifsame(model.getState(),receiveObject));
                        System.out.println("end2");
                        out.writeObject(model.getState());
                        //model.print();
                        System.out.println("end");
                    }
                }
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }


}
