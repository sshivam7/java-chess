package gameComponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameEntities.*;

/**
 * @author Shivam Sood
 * Date: 2020-04-03
 * Description: Board class allows for user interaction with the different pieces. Pieces are assigned to varios
 * tiles on the board.
 *
 * Method List:
 * public void refreshBoard() - Method that updates button icons to show changes to piece positions
 * public void resetColors() - Method rests the board colours to the original green and light green layout
 * public void resetBoard() - Method places all of the pieces onto the game board
 * public void actionPerformed(ActionEvent evt) - Loops through and sets start tile and end tile locations depending on
 *                                                the button that has been selected
 * public void clearSelection () - Method to clear user's piece selection
 * public void moveOptions() - Method responsible for showing all of the possible moves when a given piece is selected
 * public void move() - Method to move Pieces around the board
 * public void undoMove() - Method to undo the last move made by the user
 * public Tile getTile(int row, int column) - Method to return a tile based on the given row and column values
 * public void setTilePiece(Tile tile, Piece newPiece) - Method to assign a piece to a given tile
 * public boolean getFirstClick() - Method to check if the user has only clicked once
 * public Tile getStartTile() - Method to get starting tile selected by user
 * public Tile getEndTile() - Method to get ending tile selected by the user
 * public Tile[][] getTileMap() - Method to return the entire tileMap (provides board information)
 * public static void main(String[] args) - Self-testing main method
 */

public class Board extends JPanel implements ActionListener {
    //Global variables
    private static Tile[][] tileMap;          //array to hold the tiles that make up the game board
    private boolean firstClick;
    private Piece tempPiece;
    private Tile startTile;
    private Tile endTile;
    private final Color SQUARE_COLOUR_ONE = new Color(186, 202, 68);   //Dark green colour
    private final Color SQUARE_COLOUR_TWO = new Color(238, 238, 210);  //light green colour

    /**
     * Default board constructor to set up game baord
     */
    public Board() {
        super();                    //sets default values for the global variables
        startTile = null;
        endTile = null;
        tempPiece = null;
        firstClick = true;
        //creates and 8 by 8 grid and a 2 dimensional array (8 by 8) of tiles
        this.setLayout(new GridLayout(8, 8));
        tileMap = new Tile[8][8];

        //places the tiles in an alternating pattern
        for (int i = 0; i < tileMap.length; i++) {                        //row # (0, 7)
            for (int j = 0; j < tileMap.length; j++) {                    //Column # (0, 7)

                tileMap[i][j] = new Tile(i, j, null);

                //creates the checkered colour pattern
                if ((i + j) % 2 == 0)
                    tileMap[i][j].setBackground(SQUARE_COLOUR_ONE);
                else
                    tileMap[i][j].setBackground(SQUARE_COLOUR_TWO);

                //adds the tiles and listener
                this.add(tileMap[i][j]);
                tileMap[i][j].addActionListener(this);
            } //end inner for
        } //end outer for

        resetBoard();    //places chess pieces

        this.setSize(600, 600);      //sets panel size and visibility
        this.setVisible(true);

    } //end constructor

    /**
     * Method that updates button icons to show changes to piece positions
     */
    public void refreshBoard() {
        //loops through array and displays the pieces found on the tile
        for (Tile[] tiles : tileMap) {
            for (int j = 0; j < tileMap.length; j++) {
                tiles[j].displayPiece();
                resetColors();
            }
        }
    }

    /**
     * Method rests the board colours to the original green and light green layout
     */
    public void resetColors() {
        //loops through the array and sets colours in an alternating pattern
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                if ((i + j) % 2 == 0)
                    tileMap[i][j].setBackground(SQUARE_COLOUR_ONE);
                else
                    tileMap[i][j].setBackground(SQUARE_COLOUR_TWO);
            }
        }
    }

    /**
     * Method places all of the pieces onto the game board
     */
    public void resetBoard() {
        for (int i = 0; i < tileMap.length; i++) { //pawns
            tileMap[1][i].setPiece(new Pawn(Alliance.BLACK));
            tileMap[6][i].setPiece(new Pawn(Alliance.WHITE));

            if (i == 0 || i == 7) { //rooks
                tileMap[0][i].setPiece(new Rook(Alliance.BLACK));
                tileMap[7][i].setPiece(new Rook(Alliance.WHITE));
            }
            else if (i == 1 || i == 6) { //knights
                tileMap[0][i].setPiece(new Knight(Alliance.BLACK));
                tileMap[7][i].setPiece(new Knight(Alliance.WHITE));
            }
            else if (i == 2 || i == 5) { //bishops
                tileMap[0][i].setPiece(new Bishop(Alliance.BLACK));
                tileMap[7][i].setPiece(new Bishop(Alliance.WHITE));
            }
        }

        //sets the queens and kings
        tileMap[0][3].setPiece(new Queen(Alliance.BLACK));
        tileMap[7][3].setPiece(new Queen(Alliance.WHITE));
        tileMap[0][4].setPiece(new King(Alliance.BLACK));
        tileMap[7][4].setPiece(new King(Alliance.WHITE));

        refreshBoard();  //displays the different pieces
    }

    @Override
    /*
     * Loops through and sets start tile and end tile locations depending on the button that has been selected
     */
    public void actionPerformed(ActionEvent evt) {
        //loops through tile objects checking for button clicks
        for (int i = 0; i < tileMap.length; i++) {              //loops through rows
            for (int j = 0; j < tileMap.length; j++) {          //loops through columns
                if (tileMap[i][j] == evt.getSource()) {

                    if (firstClick) {   //sets start tile if user has not already selected a tile
                        firstClick = false;
                        startTile = tileMap[i][j];
                        startTile.setBackground(new Color(255, 131, 117)); //sets colour to indicate starting tile
                        moveOptions();
                    }
                    else { //sets end tile if user has selected a starting tile
                        if (!(startTile.getRow() == i && startTile.getColumn() == j)){
                            firstClick = true;
                            resetColors();   //resets board colours removing red marker
                            endTile = tileMap[i][j];
                        }
                        else {  //if user selects the same tile twice than clear start and end tile selection
                            clearSelection();
                        }
                    }

                }
            } //inner for
        } //outer for
    } //end actionPerformed method

    /**
     * Method to clear user's piece selection
     */
    public void clearSelection () {
        startTile = null;
        endTile = null;
        resetColors();
        firstClick = true;
    }

    /**
     * Method responsible for showing all of the possible moves when a given piece is selected
     */
    public void moveOptions() {
        for (Tile[] tiles : tileMap) {
            for (int j = 0; j < tileMap.length; j++) {
                if (startTile.getPiece() != null && startTile.getPiece().isValidMove(startTile, tiles[j])) {
                    if (startTile.getPiece().isValidPath(startTile, tiles[j], tileMap)) {
                        //changes to yellow colour for all squares where piece is allowed to move
                        tiles[j].setBackground(new Color(255, 251, 133, 207));
                    }
                }
            }
        } //first for
    } //end method

    /**
     * Method to move pieces around the board
     */
    public void move() {
        tempPiece = endTile.getPiece();
        setTilePiece(endTile, startTile.getPiece());
        setTilePiece(startTile, null);
        refreshBoard();
    }

    /**
     * Method to undo the last move made by the user
     */
    public void undoMove() {
        setTilePiece(startTile, endTile.getPiece());
        setTilePiece(endTile, tempPiece);
        refreshBoard();
    }

    /**
     * Method to return a tile when given a row and colour
     * @param row location of tile
     * @param column location of tile
     * @return tile at given location
     */
    public Tile getTile(int row, int column) {
        return tileMap[row][column];
    }

    /**
     * Method to assign a piece to a given tile
     * @param tile to assign piece to
     * @param newPiece is the pieces that will be assigned
     */
    public void setTilePiece(Tile tile, Piece newPiece) {
        tileMap[tile.getRow()][tile.getColumn()].setPiece(newPiece);
    }

    /**
     * Method to check if user has only clicked once
     * @return boolean variable for first click
     */
    public boolean getFirstClick() {
        return firstClick;
    }

    /**
     * Method to return value for the starting tile selected by the user
     * @return start tile
     */
    public Tile getStartTile() {
        return startTile;
    }

    /**
     * Method to return value for the ending tile selected by the user
     * @return end tile
     */
    public Tile getEndTile() {
        return endTile;
    }

    /**
     * Method to return the entire tileMap (gives other classes an idea of what the board looks like)
     * @return two dimensional array for all the tiles on the board
     */
    public Tile[][] getTileMap() {
        return tileMap;
    }

    /**
     * Self testing main method
     * @param args
     */
    public static void main(String[] args) {
        JFrame testFrame = new JFrame("TESTING FRAME");

        Board board = new Board();
        testFrame.add(board);

        testFrame.setSize(600, 600);
        testFrame.setVisible(true);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
