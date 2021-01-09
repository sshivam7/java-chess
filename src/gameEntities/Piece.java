package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

import javax.swing.*;

public abstract class Piece {
    private Alliance pieceAlliance;
    private PieceType pieceType;
    private ImageIcon pieceImg;

    /**
     * @author Shivam Sood
     * Date: 2020-04-04
     * Description: Pawn piece class. Holds information regarding movement of the Pawn piece.
     * <p>
     * Method List:
     * public boolean isValidMove(Tile startTile, Tile endTile) - Method to check if selected tiles fall within acceptable
     * move parameters for the current piece
     * public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) - Method to check board and see if the
     * move is still valid (checks for obstructions)
     * public ImageIcon getPieceImg() - Method to get piece image
     * public void setPieceType (PieceType pieceType) - Method to set the type of piece
     * public PieceType getPieceType () - Method to get the type of piece
     * public void setPieceImg(String imgWFileName, String imgBFileName) - Method to set piece image
     * public boolean isAllianceOverlap (Tile startTile, Tile endTile) - Method to test if move will overlap
     * public Alliance getPieceAlliance() - Method to get piece alliance
     */

    public Piece(Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.pieceImg = null;
    }

    /**
     * Method to check if selected tiles fall within acceptable move parameters for the
     * current piece
     *
     * @param startTile first tile selected by user
     * @param endTile   destination tile selected by user
     * @return returns true if move is valid
     */
    public abstract boolean isValidMove(Tile startTile, Tile endTile);

    /**
     * Method to check if selected tiles fall within acceptable move parameters for the
     * current piece
     *
     * @param startTile first tile selected by user
     * @param endTile   destination tile selected by user
     * @return returns true if move is valid
     */
    public abstract boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap);

    /**
     * Method to get piece image
     *
     * @return image of piece
     */
    public ImageIcon getPieceImg() {
        return pieceImg;
    }

    /**
     * Method to set the type of piece
     *
     * @param pieceType type of piece
     */
    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * Method to get the type of piece
     *
     * @return type of piece
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Method to set piece image
     *
     * @param imgWFileName white piece picture name
     * @param imgBFileName black piece picture name
     */
    public void setPieceImg(String imgWFileName, String imgBFileName) {
        if (pieceAlliance == Alliance.WHITE)
            this.pieceImg = new ImageIcon(imgWFileName);
        else
            this.pieceImg = new ImageIcon(imgBFileName);
    }

    /**
     * Method to test if move will overlap
     *
     * @param startTile initial tile selected by the user
     * @param endTile   final tile selected by the user
     * @return boolean for if the player tries to move to a tile that has their own piece
     */
    public boolean isAllianceOverlap(Tile startTile, Tile endTile) {
        return (endTile.isOccupied() && startTile.isOccupied() && endTile.getPiece().getPieceAlliance() ==
                startTile.getPiece().getPieceAlliance());
    }

    /**
     * Method to get piece alliance
     *
     * @return Alliance for piece
     */
    public Alliance getPieceAlliance() {
        return pieceAlliance;
    }
}
