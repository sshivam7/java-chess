package gameComponents;

import javax.swing.*;
import java.awt.*;

/**
 * @author Shivam Sood
 * Date: 2020-04-04
 * Description: Class creates a panel to show information such as user alliance and score
 *
 * Method List:
 * public void setCheck() - Method to change text colour to red when player is check
 * public void setTurn() - Method to set text colout to green if its the current players turn
 * public void clearText() - Method to restore text colour to default (Black)
 * public void setScore () - Method to update score on panel
 * public static void main(String[] args) - Self-testing main method
 */

public class ScorePanel extends JPanel {
    //Global Variables
    private Player gamePlayer;
    private JLabel name;
    private JLabel score;

    /**
     * Default score panel constructor to create panel with relevant information
     * @param gamePlayer takes player object
     */
    public ScorePanel (Player gamePlayer) {
        super();
        this.setLayout(null); //uses null layout
        this.gamePlayer = gamePlayer;

        name = new JLabel(gamePlayer.getPlayerAlliance().toString());                  //Displays player alliance
        name.setFont(new Font ("TimesRoman", Font.BOLD, 50));
        name.setBounds(20,20,300,70);
        this.add(name);

        JLabel scoreTag = new JLabel("Score: ");                                  //Label for score tag
        scoreTag.setFont(new Font ("TimesRoman", Font.BOLD, 20));
        scoreTag.setBounds(400,20,300,70);
        this.add(scoreTag);

        score = new JLabel("0");                                                 //Displays player score
        score.setFont(new Font ("TimesRoman", Font.BOLD, 50));
        score.setBounds(500,20,300,70);
        this.add(score);

        //sets panel features
        this.setSize(600,100);
        this.setBackground(new Color(238, 238, 210));   //TODO fix colour not working
        this.setVisible(true);
    }

    /**
     * Method to change text colour to red when player is in check
     */
    public void setCheck() {
        name.setForeground(new Color(255, 131, 117));
    }

    /**
     * Method to set turn colour if its the current players turn
     */
    public void setTurn() {
        if(gamePlayer.isTurn())
            name.setForeground(new Color(85, 238, 116));
        else
            clearText();
    }

    /**
     * Method to restore text colour to default
     */
    public void clearText() {
        name.setForeground(Color.BLACK);
    }

    /**
     * Method to update score on panel
     */
    public void setScore () {
        this.score.setText(Integer.toString(gamePlayer.getScore()));
    }

    /**
     * Self-testing main method
     * @param args
     */
    public static void main(String[] args) {
        JFrame testFrame = new JFrame("Testing Frame");

        ScorePanel sp = new ScorePanel(new Player(Alliance.WHITE, true));
        testFrame.add(sp);

        testFrame.setSize(600, 100);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setVisible(true);
    }
}
