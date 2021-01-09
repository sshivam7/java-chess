package gameComponents;

import gameEntities.Piece;

/**
 * @author Shivam Sood
 * Date: 2020-04-04
 * Description: Player class to handle information regarding player such as player alliance, score, and turn
 *
 * Method List:
 * public void updateScore(Piece killedPiece) - Method to update score depending on the piece that has been captured
 * public void fixScore(Piece revivedPiece) - Method to fix score if a move is undone
 * public int getScore () - Method returns value for score
 * public Alliance getPlayerAlliance () - Method return player alliance (either black or white)
 * public boolean isTurn() - Method checks to see if its the users turn
 * public void setTurn (boolean newTurnValue) - Method to set new value for users turn
 */

public class Player {
    //Global variables
    private Alliance playerAlliance;
    private boolean isTurn;
    private int score;

    /**
     * Default Player constructor to set default values
     * @param pAlliance value for player alliance (black or white)
     * @param isTurn value to if its the current player's turn
     */
    public Player (Alliance pAlliance, boolean isTurn) {
        //sets default values for global variables
        this.playerAlliance = pAlliance;
        this.isTurn = isTurn;
        this.score = 0;
    }

    /**
     * Method to update player score depending on the piece that has been captured
     * @param killedPiece takes in type of piece captured
     */
    public void updateScore(Piece killedPiece) {
        //assigns a score from 1,3,5,and 8 depending on the piece captured
        switch (killedPiece.getPieceType()) {
            case PAWN :
                this.score += 1;
                break;
            case ROOK:
                this.score += 5;
                break;
            case KNIGHT:
            case BISHOP:
                this.score += 3;
                break;
            case QUEEN:
                this.score += 8;
                break;
            default:
                break;
        }
    }

    /**
     * Method to fix the score if a move is undone
     * @param revivedPiece takes in value for the piece that is revived
     */
    public void fixScore(Piece revivedPiece) {
        //subtracts the corresponding value (added previously) for the piece that is revived
        if (revivedPiece != null) {
            switch (revivedPiece.getPieceType()) {
                case PAWN:
                    this.score -= 1;
                    break;
                case ROOK:
                    this.score -= 5;
                    break;
                case KNIGHT:
                case BISHOP:
                    this.score -= 3;
                    break;
                case QUEEN:
                    this.score -= 8;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method returns value for score
     * @return score
     */
    public int getScore () {
        return this.score;
    }

    /**
     * Method returns player alliance (either black or white)
     * @return alliance
     */
    public Alliance getPlayerAlliance () {
        return this.playerAlliance;
    }

    /**
     * Method returns value depending on if its the users turn
     * @return boolean value for users turn
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * Method sets new value for users turn
     * @param newTurnValue boolean value that replaces the old value
     */
    public void setTurn (boolean newTurnValue) {
        this.isTurn = newTurnValue;
    }
}
