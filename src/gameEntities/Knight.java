package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

/**
 * @author Shivam Sood
 * Date: 2020-04-04
 * Description: Knight piece class. Holds information regarding movement of the knight piece.
 *
 * Method List:
 * public boolean isValidMove(Tile startTile, Tile endTile) - Method to check if selected tiles fall within acceptable
 *                                                            move parameters for the current piece
 * public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) - Method to check board and see if the
 *                                                                              move is still valid (checks for obstructions)
 */

public class Knight extends Piece {

    public Knight(Alliance pieceAlliance) {
        super(pieceAlliance);
        this.setPieceType(PieceType.KNIGHT);
        this.setPieceImg("Images/wKnight.png", "Images/bKnight.png");
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
        int changeY = Math.abs(endTile.getRow() - startTile.getRow());
        int changeX = Math.abs(endTile.getColumn() - startTile.getColumn());

        //checks for overlap
        if (isAllianceOverlap(startTile, endTile))
            return false;
        else
            return (changeX == 2 && changeY == 1) || (changeX == 1 && changeY == 2);  //motion for knight
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
    //always true since the knight can jump over pieces
    public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
        return true;
    }
}
