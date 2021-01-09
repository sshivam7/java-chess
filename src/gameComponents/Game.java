package gameComponents;
import gameEntities.*;

import javax.swing.*;

/**
 * @author Shivam Sood
 * Date: 2020-04-03
 * Description: Chess game class controls turns and other interactions between the users, board, score panel, and
 * other entities.
 *
 * Method List:
 * public Alliance testCheck() - Method to check if the king is under check
 * public void movePiece() - Method to move piece and handle changes to piece once moved
 * public void pawnPromotion(Board gb) - Method to prompt user for pawn promotion and change piece type
 * public static void main(String[] args) - Method to run game window
 */

public class Game {
    //Global Variables
    private final Board gb;
    private final ScorePanel playerOnePanel, playerTwoPanel;
    private final Player playerOne, playerTwo;
    boolean gameOver;

    /**
     * Default Game constructor to handle running the chess game
     */
    public Game () {
        //creates new frame and sets box layout along the y-axis
        JFrame gameFrame = new JFrame("Chess Game");
        gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.Y_AXIS));

        //initializes data and sets defaults
        gameOver = false;
        gb = new Board();
        playerOne = new Player(Alliance.BLACK, false);
        playerTwo = new Player (Alliance.WHITE, true);
        playerOnePanel = new ScorePanel(playerOne);
        playerTwoPanel = new ScorePanel(playerTwo);
        gameFrame.add(playerOnePanel);
        gameFrame.add(gb);
        gameFrame.add(playerTwoPanel);

        //Edits window preferences
        gameFrame.setSize(600,800);
        gameFrame.setLocation(400, 10);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);

        while(!gameOver) { //runs until the game is over
            gb.repaint(); //refreshes the board

            //sets turn colours in score panel to show whose turn it is
            if (testCheck() == null) {
                playerOnePanel.setTurn();
                playerTwoPanel.setTurn();
            }
            else { //removes colours if a player is in check
                if (testCheck() == Alliance.WHITE)
                    playerOnePanel.clearText();
                else
                    playerTwoPanel.clearText();
            }
            //if valid tiles are selected and the correct alliance has been selected than moves piece
            if (gb.getStartTile() != null && gb.getEndTile() != null && gb.getStartTile().getPiece() != null) {
                if(playerTwo.isTurn() && gb.getStartTile().getPiece().getPieceAlliance() == Alliance.WHITE ||
                    playerOne.isTurn() && gb.getStartTile().getPiece().getPieceAlliance() == Alliance.BLACK) {
                    if (gb.getFirstClick()) {
                        movePiece();   //calls method to move pieces

                        gb.clearSelection();   //clears selection for next itteration of the loop

                        //Tests to see if any of the player are in check or checkmate
                        if (testCheck() == Alliance.WHITE) {
                            playerTwoPanel.setCheck();
                        } else if (testCheck() == Alliance.BLACK) {
                            playerOnePanel.setCheck();
                        } else {
                            playerOnePanel.clearText();
                            playerTwoPanel.clearText();
                        }
                    }
                } //end of player turn check statement
                else {
                    //if player has not selected valid tiles than selection is cleared forcing the player to choose again
                    gb.clearSelection();
                }
            }
        } //end of while loop
        if (testCheck() == Alliance.WHITE) //displays winning message depending on the king in check
            JOptionPane.showMessageDialog(null, "Game Over, Black Wins!");
        else
            JOptionPane.showMessageDialog(null, "Game Over, White Wins!");
        System.exit(0); //closes the window
    }

    /**
     * Method to test if the king is under check
     * @return Alliance value for the king who is in check or null if neither king is in check
     */
    public Alliance testCheck() {
        King whiteKing = null, blackKing = null; //variables to hold white and black kings

        //finds the locations of the two kings
        for (int i = 0; i < gb.getTileMap().length; i++) {
            for (int j = 0; j < gb.getTileMap().length; j++) {
                if(gb.getTile(i, j).isOccupied() && gb.getTile(i, j).getPiece().getPieceType() == PieceType.KING) {
                    if (gb.getTile(i, j).getPiece().getPieceAlliance() == Alliance.WHITE)
                        whiteKing = (King) gb.getTile(i, j).getPiece();
                    else
                        blackKing = (King) gb.getTile(i, j).getPiece();
                }
            }
        }

        assert whiteKing != null; //makes sure both the white and black kings are present on the board
        assert blackKing != null;

        //returns the appropriate value depending on if and which kings are under check
        if (whiteKing.inCheck(gb.getTileMap(), whiteKing.findKing(gb.getTileMap()))) {
            if(whiteKing.checkMate(gb.getTileMap())) {    //tests for white checkmate
                gameOver = true;
            }
            return Alliance.WHITE;
        }
        else if (blackKing.inCheck(gb.getTileMap(), blackKing.findKing(gb.getTileMap()))) {
            if(blackKing.checkMate(gb.getTileMap())) {      //tests for black checkmate
                gameOver = true;
            }
            return Alliance.BLACK;
        }
        else {
            return null;
        }
    }

    /**
     * Method to move piece and handle changes to the piece once moved
     */
    public void movePiece() {
        //checks if the move is valid
        if (gb.getStartTile().isOccupied() && gb.getStartTile().getPiece().isValidMove(gb.getStartTile(), gb.getEndTile())) {
            if (gb.getStartTile().getPiece().isValidPath(gb.getStartTile(), gb.getEndTile(), gb.getTileMap())) {
                if(gb.getEndTile().isOccupied()) {
                    //updates score for black player if white piece is captured
                    if(gb.getEndTile().getPiece().getPieceAlliance() == Alliance.WHITE) {
                        playerOne.updateScore(gb.getEndTile().getPiece());
                        playerOnePanel.setScore();
                    }
                    //updates score for white player if black piece is captured
                    else {
                        playerTwo.updateScore((gb.getEndTile().getPiece()));
                        playerTwoPanel.setScore();
                    }
                }
                gb.move();      //moves piece

                //checks if moved piece is pawn
                if (gb.getEndTile().getPiece().getPieceType() == PieceType.PAWN) {
                    gb.getEndTile().getPawn().setFirstMove(false); //stops pawn from moving 2 spaces after initial move
                    //handles pawn promotion if pawn has made it to the appropriate square
                    if ((gb.getEndTile().getPawn().getPieceAlliance() == Alliance.WHITE && gb.getEndTile().getRow() == 0) ||
                            (gb.getEndTile().getPawn().getPieceAlliance() == Alliance.BLACK && gb.getEndTile().getRow() == 7)) {
                        pawnPromotion(gb);
                    }
                }
                //if moving a piece results in check than undoes move
                if (testCheck() != null && gb.getEndTile().getPiece().getPieceAlliance() == testCheck()) {
                    if (gb.getEndTile().getPiece().getPieceType() == PieceType.PAWN) {
                        gb.getEndTile().getPawn().setFirstMove(true);
                    }
                    gb.undoMove();
                    if (gb.getStartTile().getPiece().getPieceAlliance() == Alliance.WHITE) {
                        playerTwo.fixScore(gb.getEndTile().getPiece());
                    }
                    else {
                        playerOne.fixScore(gb.getEndTile().getPiece());
                    }
                    playerOnePanel.setScore();
                    playerTwoPanel.setScore();
                    gb.clearSelection();
                }
                else { //if move is successful moves onto next turn
                    playerOne.setTurn(!playerOne.isTurn());
                    playerTwo.setTurn(!playerTwo.isTurn());
                }
            }
        }
    } //end of method

    /**
     * Method to prompt user for pawn promotion and change piece type
     * @param gb is the game board
     */
    public void pawnPromotion(Board gb) {
        //gets letter from user
        char letter = JOptionPane.showInputDialog(null, "Pawn promotion: enter the corresponding character: \n" +
                "q - Queen \n r - Rook \n k - Knight \n b - Bishop").charAt(0);

        //depending on the letter entered promotes pawn
        switch (letter) {
            case 'r':
                gb.getEndTile().setPiece(new Rook(gb.getEndTile().getPiece().getPieceAlliance()));
                break;
            case 'k':
                gb.getEndTile().setPiece(new Knight(gb.getEndTile().getPiece().getPieceAlliance()));
                break;
            case 'b':
                gb.getEndTile().setPiece(new Bishop(gb.getEndTile().getPiece().getPieceAlliance()));
                break;
            default:
                gb.getEndTile().setPiece(new Queen(gb.getEndTile().getPiece().getPieceAlliance()));
                break;
        }
        gb.refreshBoard();
    }

    /**
     * Main method to run game window
     * @param args
     */
    public static void main(String[] args) {
        new Game();
    }
}
