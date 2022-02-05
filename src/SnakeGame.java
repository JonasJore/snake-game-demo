import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        initGUI();
    }

    public void setPanel(Component panel) {
        add(panel, BorderLayout.CENTER);
        setResizable(false);
        pack();
    }

    private void initGUI() {
        setPanel(new GameBoard());
        setTitle(Strings.GAME_TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        System.out.println(Strings.GAME_TITLE);
        EventQueue.invokeLater(() -> {
            JFrame frame = new SnakeGame();
            frame.setVisible(true);
        });
    }
}
