import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Image;

enum Direction {
    LEFT, RIGHT, UP, DOWN
}

public class Snake extends JComponent {
    private final int DOT_SIZE;
    private int snakeJointsOnStart;

    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private final ImageIcon snakeJointIcon = new ImageIcon(Strings.SNAKE_JOINT_ICON);
    private final ImageIcon snakeHeadIcon = new ImageIcon(Strings.SNAKE_JOINT_ICON);

    private final int[] x;
    private final int[] y;

    private static final int snakeStartPositionX = 150;
    private static final int snakeStartPositionY = 150;

    public Snake(int[] x, int[] y, int DOT_SIZE) {
        this.snakeJointsOnStart = 1;
        this.DOT_SIZE = DOT_SIZE;
        this.x = x;
        this.y = y;
    }

    public boolean isLeftDirection() {
        return leftDirection;
    }

    public boolean isRightDirection() {
        return rightDirection;
    }

    public boolean isUpDirection() {
        return upDirection;
    }

    public boolean isDownDirection() {
        return downDirection;
    }

    public int getSnakeJointsOnStart() {
        return this.snakeJointsOnStart;
    }

    public void setDirection(Direction direction) {
        switch (direction) {
            case LEFT -> {
                this.setLeftDirection(true);
                this.setUpDirection(false);
                this.setDownDirection(false);
            }
            case RIGHT -> {
                this.setRigthDirection(true);
                this.setUpDirection(false);
                this.setDownDirection(false);
            }
            case UP -> {
                this.setUpDirection(true);
                this.setRigthDirection(false);
                this.setLeftDirection(false);
            }
            case DOWN -> {
                this.setDownDirection(true);
                this.setRigthDirection(false);
                this.setLeftDirection(false);
            }
        }
    }

    public void initSnake() {
        for (int i = 0; i < snakeJointsOnStart; i++) {
            x[i] = snakeStartPositionX;
            y[i] = snakeStartPositionY;
        }
    }

    public Image getSnakeJointResource() {
        return snakeJointIcon.getImage();
    }

    public Image getSnakeHeadResource() {
        return snakeHeadIcon.getImage();
    }

    public void eatApple() {
        snakeJointsOnStart++;
    }

    public void drawSnake(Graphics graphics) {
        for (int i = 0; i < snakeJointsOnStart; i++) {
            if (i == 0) {
                graphics.drawImage(this.getSnakeHeadResource(), x[i], y[i], this);
            } else {
                graphics.drawImage(this.getSnakeJointResource(), x[i], y[i], this);
            }
        }
    }

    public void moveSnake() {
        for (int i = snakeJointsOnStart; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (leftDirection) {
            x[0] -= this.DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += this.DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= this.DOT_SIZE;
        }

        if (downDirection) {
            y[0] += this.DOT_SIZE;
        }

        repaint();
    }

    private void setLeftDirection(boolean direction) {
        this.leftDirection = direction;
    }

    private void setRigthDirection(boolean direction) {
        this.rightDirection = direction;
    }

    private void setUpDirection(boolean direction) {
        this.upDirection = direction;
    }

    private void setDownDirection(boolean direction) {
        this.downDirection = direction;
    }
}
