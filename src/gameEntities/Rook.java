package gameEntities;

import gameComponents.Alliance;
import gameComponents.Tile;

/**
 * @author Shivam Sood
 * Date: 2020-04-04
 * Description: Rook piece class. Holds information regarding movement of the Rook piece.
 *
 * Method List:
 * public boolean isValidMove(Tile startTile, Tile endTile) - Method to check if selected tiles fall within acceptable
 *                                                            move parameters for the current piece
 * public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) - Method to check board and see if the
 *                                                                              move is still valid (checks for obstructions)
 */

public class Rook extends Piece {

    public Rook (Alliance pieceAlliance) {
        super(pieceAlliance);
        this.setPieceType(PieceType.ROOK);
        this.setPieceImg("Images/wRook.png", "Images/bRook.png");
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

        if (isAllianceOverlap(startTile, endTile))
            return false;
        else
            return (changeX > 0 && changeY == 0) || (changeX == 0 && changeY > 0); //tests for linear movement
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
    public boolean isValidPath(Tile startTile, Tile endTile, Tile[][] tileMap) {
            int yChange = endTile.getRow() - startTile.getRow();
            int xChange = endTile.getColumn() - startTile.getColumn();

            int xDir = Integer.signum(xChange);
            int yDir = Integer.signum(yChange);

            if (yDir == 0) { //horizontal obstruction
                for (int i = 1; i < Math.abs(xChange); i++) {
                    if (tileMap[startTile.getRow()][startTile.getColumn() + i * xDir].isOccupied()) {
                        return false;
                    }
                }
            }
            else {   //vertical obstruction
                for (int i = 1; i < Math.abs(yChange); i++) {
                    if (tileMap[startTile.getRow() + i * yDir][startTile.getColumn()].isOccupied()) {
                        return false;
                    }
                }
            }
        return true;
        }
}
