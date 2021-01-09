package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

/**
 * @author Shivam Sood
 * Date: 2020-04-04
 * Description: Bishop piece class. Holds information regarding movement of the bisop piece.
 *
 * Method List:
 * public boolean isValidMove(Tile startTile, Tile endTile) - Method to check if selected tiles fall within acceptable
 *                                                            move parameters for the current piece
 * public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) - Method to check board and see if the
 *                                                                              move is still valid (checks for obstructions)
 */

public class Bishop extends Piece {

    /**
     * Default constructor for the bishop piece
     * @param pieceAlliance alliance the piece is part of
     */
    public Bishop(Alliance pieceAlliance) {
        super(pieceAlliance);
        this.setPieceType(PieceType.BISHOP);
        this.setPieceImg("Images/wBishop.png",  "Images/bBishop.png");
    }

    /**
     * Method to check if selected tiles fall within acceptable move parameters for the
     * current piece
     *
     * @param startTile first tile selected by user
     * @param endTile   destination tile selected by user
     * @return returns true if move is valid
     */
    @Override
    public boolean isValidMove(Tile startTile, Tile endTile) {
        //calculates change in x and y values
        int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        //checks if same coloured piece occupies destination tile
        if (isAllianceOverlap(startTile, endTile))
            return false;
        else
            return changeX == changeY; //chang in x and y values is the same for diagonal motion
    }

    /**
     * Method to check board and see if the move is still valid (checks for obstructions)
     * @param startTile first tile selected by user
     * @param endTile destination tile selected by user
     * @param tileMap array of all the tiles found on the board
     * @return true or false depending on if the move is possible
     */
    @Override
    public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        int yChange = endTile.getRow() - startTile.getRow();
        int xChange = endTile.getColumn() - startTile.getColumn();

        int xDir = Integer.signum(xChange);
        int yDir = Integer.signum(yChange);

        //if tile is between start and end is occupied than path is not valid
        for (int i = 1; i < Math.abs(xChange); i++) {
            if (tileMap[startTile.getRow() + i * yDir][startTile.getColumn() + i * xDir].isOccupied()) {
                return false;
            }
        }
        return true;
    }
}
