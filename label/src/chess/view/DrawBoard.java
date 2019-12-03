package chess.view;

import chess.model.*;
import chess.model.Chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * to paint the components in the frame.
 */

class DrawBoard extends JPanel {

  private static final long serialVersionUID = 1L;
  private JPanel containPanelChess = new JPanel();
  private JPanel panel = new JPanel();
  private JPanel containPanelbutton = new JPanel();
  private JLabel headerLabel = new JLabel("current player:White");
  private JButton reset = new JButton("Reset");
  private int check = 0;
  private Cell from = null;
  private Cell to = null;
  private Pawnposition p = null;

    private int ifmiss = 0;

  /**
   * To order the function to draw the initial chess.
   *
   * @param frame The frame which the chess in.
   * @param mo The chess now.
   */
  DrawBoard(JFrame frame, Model mo) {
    this.paintChess(frame, mo);
  }


  /**
   * Draw pawns in the whole field. panel is the chess in the game, in the middle of
   * containPanelChess. And the containPanelChess is in the middle of the whole frame. And the
   * button "reset" is in the middle of the containPanelbutton. In this function it will print the
   * chess and if any "mouseclick" happens it will refresh the chess the same as the function print.
   *
   * @param frame the frame of the whole game.
   * @param mo The chess.
   *
   */
  protected void paintChess(JFrame frame, Model mo) {
    ImageIcon iconb = new ImageIcon("pawn_black.png");
    iconb.setImage(iconb.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
    ImageIcon iconw = new ImageIcon("pawn_white.png");
    iconw.setImage(iconw.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
    headerLabel.setOpaque(true);
    frame.add(headerLabel, BorderLayout.NORTH);
    headerLabel.setBackground(Color.LIGHT_GRAY);
    headerLabel.setForeground(Color.RED);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    frame.add(containPanelChess, BorderLayout.CENTER);
    this.reset.setSize(20, 10);
    containPanelChess.setLayout(null);
    containPanelbutton.setBackground(Color.LIGHT_GRAY);
    containPanelbutton.add(reset);
    frame.add(containPanelbutton, BorderLayout.SOUTH);
    containPanelChess.setLayout((new BorderLayout()));
    panel.setLayout(new GridLayout(8, 8));
    //JButton[][] bo = new JButton[8][8];
    JLabel[][] bo = new JLabel[8][8];
    JLabel nothing = new JLabel("     ");
    JLabel nothing1 = new JLabel("       ");
    JLabel nothing2 = new JLabel("       ");
    JLabel nothing3 = new JLabel("       ");
    containPanelChess.add(nothing, BorderLayout.SOUTH);
    containPanelChess.add(nothing1, BorderLayout.NORTH);
    containPanelChess.add(nothing2, BorderLayout.EAST);
    containPanelChess.add(nothing3, BorderLayout.WEST);

    int response = JOptionPane.showConfirmDialog(this,"do you want to begin a single game?");
      for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        bo[i][j] = new JLabel();
        panel.add(bo[i][j]);
        if ((i + j) % 2 == 0) {
          bo[i][j].setOpaque(true);
          bo[i][j].setBackground(Color.GRAY);
          bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
        } else {
          bo[i][j].setOpaque(true);
          bo[i][j].setBackground(Color.WHITE);
          bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
        if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
          if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
              .equals(Player.BLACK.toString())) {
            bo[i][j].setIcon(iconb);
            bo[i][j].setHorizontalAlignment(JLabel.CENTER);
          } else {
            bo[i][j].setIcon(iconw);
            bo[i][j].setHorizontalAlignment(JLabel.CENTER);
          }
        }
        Cell temp = new Cell(j, 7 - i);

        if(response==JOptionPane.YES_OPTION){
            bo[i][j].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    //System.out.println("your click has been catched! " + check);
                    int mouse = e.getButton();
                    if (mouse == MouseEvent.BUTTON1) {
                        if (check == 1&&mo.getState().getField().get(from)!=null&&mo.getState().getField().get(from).getPlayer()==Player.WHITE) {
                            to = temp;
                            boolean auto = mo.move(from, to);
                            if (auto == true) {
                                if (!mo.ifmiss() && mo.getState().getWinner() == null) {
                                    ifmiss = 1;
                                    System.out.println(mo.getState().getCurrentPlayer() + " must miss a turn");
                                    mo.getState().setCurrentPlayer(mo.getState().getCurrentPlayer());
                                }
                                check = 0;
                                //System.out.println("move" + " check = " + check);
                                //headerLabel
                                        //.setText("current player:" + mo.getState().getCurrentPlayer().toString());
                                headerLabel
                                        .setText("current player: White");
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
                                            if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
                                                    .equals(Player.BLACK.toString())) {
                                                bo[i][j].setIcon(iconb);
                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                            } else {
                                                bo[i][j].setIcon(iconw);
                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                            }
                                        } else {
                                            bo[i][j].setIcon(null);
                                        }
                                        bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                    }
                                }
                                    if (mo.getState().getCurrentPlayer() == Player.BLACK) {
                                            p = mo.minmax(0,Player.BLACK);
                                            if (p == null) {
                                                System.out.println("Fehler bei p");
                                            } else if (p.getresult() == 0) {
                                                System.out.println("Fehler bei p.r");
                                            } else if (p.getcellt() == null) {
                                                System.out.println("Fehler bei p.getcellt");
                                            } else if (p.getcellf() == null) {
                                                System.out.println("Fehler bei p.getcellf");
                                            } else {
                                                //System.out.println("value: " + p.getresult());
                                                mo.move(p.getcellf(), p.getcellt());
                                                for (int i = 0; i < 8; i++) {
                                                    for (int j = 0; j < 8; j++) {
                                                        if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
                                                            if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
                                                                    .equals(Player.BLACK.toString())) {
                                                                bo[i][j].setIcon(iconb);
                                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                                            } else {
                                                                bo[i][j].setIcon(iconw);
                                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                                            }
                                                        } else {
                                                            bo[i][j].setIcon(null);
                                                        }
                                                        bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                                    }
                                                }
                                            }
                                    }
                            } else {
                                if (mo.getState().getField().get(temp) != null) {
                                    if (mo.getState().getField().get(temp).getPlayer() == mo.getState()
                                            .getCurrentPlayer()) {
                                        check = 1;
                                        //System.out
                                                //.println("not move and the same player to clik" + " check = " + check);
                                        from = temp;
                                        for (int i = 0; i < 8; i++) {
                                            for (int j = 0; j < 8; j++) {
                                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                            }
                                        }
                                        for (Cell cell : mo.getPossibleMovesForPawn(from)) {
                                            //System.out.println("Set");
                                            bo[7 - cell.getRow()][cell.getColumn()]
                                                    .setBorder(BorderFactory.createLineBorder(Color.RED));
                                        }

                                    } else {
                                        check = 0;
                                        //System.out.println("not move and other player to clik" + " check = " + check);
                                        from = null;
                                        for (int i = 0; i < 8; i++) {
                                            for (int j = 0; j < 8; j++) {
                                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                            }
                                        }
                                    }
                                } else {
                                    check = 0;
                                    //System.out.println("not moved and leer click" + " check = " + check);
                                    from = null;
                                    for (int i = 0; i < 8; i++) {
                                        for (int j = 0; j < 8; j++) {
                                            bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                        }
                                    }
                                }

                            }
                            if (mo.getState().getCurrentPhase() == Phase.FINISHED) {
                                JOptionPane.showConfirmDialog(frame,
                                        "The Player " + mo.getState().getWinner().toString() + " has won!",
                                        "Finished!", JOptionPane.DEFAULT_OPTION);
                            }
                        } else if (check == 0) {
                            if (mo.getState().getField().get(temp) != null) {
                                if (mo.getState().getField().get(temp).getPlayer() == mo.getState()
                                        .getCurrentPlayer()) {
                                    from = temp;
                                    check = 1;
                                    //System.out.println("first step" + " check = " + check);
                                    for (Cell cell : mo.getPossibleMovesForPawn(from)) {
                                        bo[7 - cell.getRow()][cell.getColumn()]
                                                .setBorder(BorderFactory.createLineBorder(Color.RED));
                                    }
                                }
                            }
                        }
                    }
                    if (mouse == MouseEvent.BUTTON3 && check == 1) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                            }
                        }
                        check = 0;
                        from = null;
                    }
                    reset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mo.setChess();
                            headerLabel
                                    .setText("current player:" + mo.getState().getCurrentPlayer().toString());
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
                                        if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
                                                .equals(Player.BLACK.toString())) {
                                            bo[i][j].setIcon(iconb);
                                        } else {
                                            bo[i][j].setIcon(iconw);
                                        }
                                    } else {
                                        bo[i][j].setIcon(null);
                                    }
                                    bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                }
                            }

                        }
                    });
                }


            });
        }
        if(response==JOptionPane.NO_OPTION) {
            bo[i][j].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    //System.out.println("your click has been catched! " + check);
                    int mouse = e.getButton();
                    if (mouse == MouseEvent.BUTTON1) {
                        if (check == 1) {
                            to = temp;
                            if (mo.move(from, to)) {
                                if (!mo.ifmiss() && mo.getState().getWinner() == null) {
                                    ifmiss = 1;
                                    System.out.println(mo.getState().getCurrentPlayer() + " must miss a turn");
                                    mo.getState().setCurrentPlayer(mo.getState().getCurrentPlayer());
                                }
                                check = 0;
                                System.out.println("move" + " check = " + check);
                                headerLabel
                                        .setText("current player:" + mo.getState().getCurrentPlayer().toString());
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
                                            if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
                                                    .equals(Player.BLACK.toString())) {
                                                bo[i][j].setIcon(iconb);
                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                            } else {
                                                bo[i][j].setIcon(iconw);
                                                bo[i][j].setHorizontalAlignment(JLabel.CENTER);
                                            }
                                        } else {
                                            bo[i][j].setIcon(null);
                                        }
                                        bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                    }
                                }
                            } else {
                                if (mo.getState().getField().get(temp) != null) {
                                    if (mo.getState().getField().get(temp).getPlayer() == mo.getState()
                                            .getCurrentPlayer()) {
                                        check = 1;
                                        System.out
                                                .println("not move and the same player to clik" + " check = " + check);
                                        from = temp;
                                        for (int i = 0; i < 8; i++) {
                                            for (int j = 0; j < 8; j++) {
                                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                            }
                                        }
                                        for (Cell cell : mo.getPossibleMovesForPawn(from)) {
                                            System.out.println("Set");
                                            bo[7 - cell.getRow()][cell.getColumn()]
                                                    .setBorder(BorderFactory.createLineBorder(Color.RED));
                                        }

                                    } else {
                                        check = 0;
                                        System.out.println("not move and other player to clik" + " check = " + check);
                                        from = null;
                                        for (int i = 0; i < 8; i++) {
                                            for (int j = 0; j < 8; j++) {
                                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                            }
                                        }
                                    }
                                } else {
                                    check = 0;
                                    System.out.println("not moved and leer click" + " check = " + check);
                                    from = null;
                                    for (int i = 0; i < 8; i++) {
                                        for (int j = 0; j < 8; j++) {
                                            bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                        }
                                    }
                                }

                            }
                            if (mo.getState().getCurrentPhase() == Phase.FINISHED) {
                                JOptionPane.showConfirmDialog(frame,
                                        "The Player " + mo.getState().getWinner().toString() + " has won!",
                                        "Finished!", JOptionPane.DEFAULT_OPTION);
                            }
                        } else if (check == 0) {
                            if (mo.getState().getField().get(temp) != null) {
                                if (mo.getState().getField().get(temp).getPlayer() == mo.getState()
                                        .getCurrentPlayer()) {
                                    from = temp;
                                    check = 1;
                                    System.out.println("first step" + " check = " + check);
                                    for (Cell cell : mo.getPossibleMovesForPawn(from)) {
                                        bo[7 - cell.getRow()][cell.getColumn()]
                                                .setBorder(BorderFactory.createLineBorder(Color.RED));
                                    }
                                }
                            }
                        }
                    }
                    if (mouse == MouseEvent.BUTTON3 && check == 1) {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                            }
                        }
                        check = 0;
                        from = null;
                    }
                    reset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mo.setChess();
                            headerLabel
                                    .setText("current player:" + mo.getState().getCurrentPlayer().toString());
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    if (mo.getState().getField().get(new Cell(j, 7 - i)) != null) {
                                        if (mo.getState().getField().get(new Cell(j, 7 - i)).getPlayer().toString()
                                                .equals(Player.BLACK.toString())) {
                                            bo[i][j].setIcon(iconb);
                                        } else {
                                            bo[i][j].setIcon(iconw);
                                        }
                                    } else {
                                        bo[i][j].setIcon(null);
                                    }
                                    bo[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
                                }
                            }

                        }
                    });
                }


            });
      }
      if(response==JOptionPane.CANCEL_OPTION){
            return;
      }
      }
    }
    containPanelChess.add(panel, BorderLayout.CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {

    // example code below
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    drawPawn(Player.WHITE, g, 0, 20, 20, 60, 60);


    // TODO insert code here
  }

  /**
   * Draw a pawn on the current selected cell.
   *
   * @param player The player that owns the cell.
   * @param g The {@link Graphics} object that allows to draw on the board.
   * @param padding Used to determine the gap-size between the cell and its border
   * @param x The coordinate marking the left point of the cell.
   * @param y The coordinate marking the upper point of the cell.
   * @param cellWidth The width of the cell.
   * @param cellHeight The height of the cell.
   */
  private void drawPawn(Player player, Graphics g, int padding, int x, int y, int cellWidth,
      int cellHeight) {
    Optional<Image> imgOpt = Optional.empty();

    switch (player) {
      case WHITE:
        g.setColor(Color.WHITE);
        imgOpt = ResourceLoader.WHITE_PAWN;
        break;
      case BLACK:
        g.setColor(Color.BLACK);
        imgOpt = ResourceLoader.BLACK_PAWN;
        break;
      default:
        throw new RuntimeException("Unhandled player: " + player);
    }

    if (imgOpt.isPresent()) {
      g.drawImage(imgOpt.get(), x + padding, y + padding, cellWidth - 2 * padding,
          cellHeight - 2 * padding, null);
    }

    // TODO insert code here
  }

  public void chesspaintwithGamestate (GameState game) {

  }
}
