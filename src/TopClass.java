import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TopClass implements ActionListener {
    //global constant variables

    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int PIPE_GAP = SCREEN_HEIGHT/5;
    private static final int PIPE_WIDTH = SCREEN_WIDTH/8;
    private static final int UPDATE_DIFFERENCE = 25;
    private static final int X_MOVEMENT_DIFFERENCE = 5;
    private static final int SCREEN_DELAY = 300;
    private boolean loopVar = true;
    private JFrame f = new JFrame("Flappy Bat");
    private JButton startGame;
    private JPanel topPanel;
    private static TopClass tc = new TopClass();
    private static PlayGameScreen pgs;

    public TopClass() {

    }

    public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tc.buildFrame();
                        Thread t = new Thread() {
                            public void run() {
                                tc.gameScreen(true);
                            }
                        };
                        t.start();
                }
            });
    }

    private void buildFrame() {
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("resources/flying-bat.svg"));
        f.setContentPane(createContentPane());
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setAlwaysOnTop(false);
        f.setVisible(true);
        f.setMinimumSize(new Dimension(SCREEN_WIDTH*1/4, SCREEN_HEIGHT*1/4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setIconImage(icon);


    }
    private JPanel createContentPane() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);

        LayoutManager overlay = new OverlayLayout(topPanel);
        topPanel.setLayout(overlay);

        startGame = new JButton("Comenzar");
        startGame.setBackground(Color.BLUE);
        startGame.setForeground(Color.WHITE);
        startGame.setFocusable(false);
        startGame.setFont(new Font("Calibri", Font.BOLD, 42));
        startGame.setAlignmentX(0.5f); //center horizontally on-screen
        startGame.setAlignmentY(0.5f); //center vertically on-screen
        startGame.addActionListener(this);
        topPanel.add(startGame);

        pgs = new PlayGameScreen(SCREEN_WIDTH, SCREEN_HEIGHT, true);
        topPanel.add(pgs);
        return topPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGame) {

        }
    }

    private void gameScreen(boolean isSplash) {
        BottomPipe bp1 = new BottomPipe(PIPE_WIDTH, SCREEN_HEIGHT);
        BottomPipe bp2 = new BottomPipe(PIPE_WIDTH, SCREEN_HEIGHT);
        TopPipe tp1 = new TopPipe(PIPE_WIDTH, SCREEN_HEIGHT);
        TopPipe tp2 = new TopPipe(PIPE_WIDTH, SCREEN_HEIGHT);

        int xLoc1 = SCREEN_WIDTH+SCREEN_DELAY, xLoc2 = (int) ((double) 3.0/2.0*SCREEN_WIDTH+PIPE_WIDTH/2.0)+SCREEN_DELAY;
        int yLoc1 = bottomPipeLoc(), yLoc2 = bottomPipeLoc();
        long startTime = System.currentTimeMillis();

        while(loopVar) {
            if((System.currentTimeMillis() - startTime) > UPDATE_DIFFERENCE) {
                if(xLoc1 < (0-PIPE_WIDTH)) {
                    xLoc1 = SCREEN_WIDTH;
                    yLoc1 = bottomPipeLoc();
                }
                else if(xLoc2 < (0-PIPE_WIDTH)) {
                    xLoc2= SCREEN_WIDTH;
                    yLoc2= bottomPipeLoc();
                }

                xLoc1 -= X_MOVEMENT_DIFFERENCE;
                xLoc2 -= X_MOVEMENT_DIFFERENCE;

                bp1.setX(xLoc1);
                bp1.setY(yLoc1);
                bp2.setX(xLoc2);
                bp2.setY(yLoc2);
                tp1.setX(xLoc1);
                tp1.setY(yLoc1-PIPE_GAP-SCREEN_HEIGHT); //ensure tp1 placed in proper location
                tp2.setX(xLoc2);
                tp2.setY(yLoc2-PIPE_GAP-SCREEN_HEIGHT);

                pgs.setBottomPipe(bp1, bp2);
                pgs.setTopPipe(tp1, tp2);
                topPanel.revalidate();
                topPanel.repaint();
                startTime = System.currentTimeMillis();
            }
        }

    }

    private int bottomPipeLoc() {
        int temp = 0;
        while(temp <= PIPE_GAP+50 || temp >= SCREEN_HEIGHT-PIPE_GAP) {
            temp = (int) ((double) Math.random()*((double)SCREEN_HEIGHT));
        }
        return temp;
    }
}