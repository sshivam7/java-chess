package gameComponents;

import gameEntities.Pawn;
import gameEntities.Piece;

import javax.swing.*;

/**
 * @author Shivam S.
 * Date: 2020-04-04
 * Description: Tile class to make up one of the 64 tiles found on the chess board
 *
 * Method List:
 * public boolean isOccupied() - Method to check if the current tile has a piece
 * public void displayPiece() - Method to display the image of the piece found on this tile
 * public int getRow() - Method to get tile row
 * public int getColumn() - Method to get tile column
 * public Piece getPiece() - Method to get piece associated with tile
 * public Pawn getPawn() - Method to get pawn on tile
 * public void setPiece(Piece newPiece) - Method to set new piece on tile
 */

public class Tile extends JButton {
    //private instance data
    private Piece piece;
    private int row;
    private int column;

    /**
     * Default Tile constructor. When the object is created holds values for object location
     * and the piece on the tile
     *
     * @param row    takes row location
     * @param column takes column location
     * @param piece  takes piece on tile
     */
    public Tile(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    /**
     * method to check if current tile has a piece
     * @return true if there is a piece
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     * Method to display piece image found on the tile
     */
    public void displayPiece() {
        if (isOccupied())
            this.setIcon(piece.getPieceImg());
        else
            this.setIcon(null);
    }

    /**
     * Method to get tile row
     * @return row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Method to get tile column
     * @return column
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Method to get piece associated with tile
     * @return piece on tile
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Method to get pawn on tile
     * @return Pawn
     */
    public Pawn getPawn() {
        return (Pawn) this.piece;
    }

    /**
     * Method to set new piece onto tile
     * @param newPiece piece to be set
     */
    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

}
