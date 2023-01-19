// lo que propiamente se ve al jugar

import javax.swing.*;
import java.awt.*;

public class PlayGameScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    private int screenWidth, screenHeight;
    private boolean isSplash = true;
    private String message = "Flappy Bat";
    private Font primaryFont = new Font("Goudy Stout", Font.BOLD, 56), failFont = new Font("Calibri", Font.BOLD, 56);
    private int messageWidth = 0;
    private BottomPipe bp1, bp2;
    private TopPipe tp1, tp2;

    public PlayGameScreen(int screenWidth, int screenHeight, boolean isSplash) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.isSplash = isSplash;
    }

    // DRAWING

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

       //cielo (background)
        g.setColor(new Color(89, 81, 247));
        g.fillRect(0, 0, screenWidth, screenHeight*7/8);
        //ground
        g.setColor(new Color(147, 136, 9));
        g.fillRect(0, screenHeight*7/8, screenWidth, screenHeight/8);
        g.setColor(Color.BLACK);
        g.drawLine(0, screenHeight*7/8, screenWidth, screenHeight*7/8);

        if(bp1 != null && bp2 != null && tp1 != null && tp2 != null) {
            g.drawImage(bp1.getPipe(), bp1.getX(), bp1.getY(), null);
            g.drawImage(bp2.getPipe(), bp2.getX(), bp2.getY(), null);
            g.drawImage(tp1.getPipe(), tp1.getX(), tp1.getY(), null);
            g.drawImage(tp2.getPipe(), tp2.getX(), tp2.getY(), null);

        }
        try {
            g.setFont(primaryFont);
            FontMetrics metric = g.getFontMetrics(primaryFont);
            messageWidth = metric.stringWidth(message);
        }
        catch(Exception e) {
            g.setFont(failFont);
            FontMetrics metric = g.getFontMetrics(failFont);
            messageWidth = metric.stringWidth(message);
        }
        g.drawString(message, screenWidth/2-messageWidth/2, screenHeight/4);
    }

    public void setBottomPipe(BottomPipe bp1, BottomPipe bp2) {
        this.bp1 = bp1;
        this.bp2 = bp2;
    }
    public void setTopPipe(TopPipe tp1, TopPipe tp2) {
        this.tp1 = tp1;
        this.tp2 = tp2;
    }
}