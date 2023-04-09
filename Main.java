import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static Integer turn = 0;
    public static ArrayList<Integer> grid = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid of Buttons");
        JPanel panel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++){
            grid.add(-1);
        }
        boolean loop = true;


        for (int i = 0; i < 9; i++) {
            Btn button = new Btn(i);
            button.setPreferredSize(new Dimension(300, 300));
            eventListenerMultiplayer(button, frame, loop);
            panel.add(button);
        }

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    public static void setGrid(int n){
        grid.set(n, turn % 2);
        turn += 1;
    }


    public static void eventListenerMultiplayer(Btn button, JFrame frame, boolean loop){
        button.addActionListener(e -> {
            Btn clickedButton = (Btn) e.getSource();
            setBtn(clickedButton);
            if (turn > 4){
                if(checkWin(button.label)){
                    System.out.format((turn % 2 == 0 ? "X": "O") + " Wins", turn % 2);
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
            if (turn == 9){
                if(checkWin(button.label)){
                    System.out.format((turn % 2 == 1 ? "X": "O") + " Wins/n", turn % 2);
                } else {
                    System.out.println("Draw");
                }
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
    public static boolean setBtn(Btn button){
        if(grid.get(button.label) == -1) {
            if (turn % 2 == 0) {
                button.setText("O");
            } else {
                button.setText("X");
            }
            setGrid(button.label);
            return true;
        }
        return false;
    }

    public static boolean checkWin(int n){
        int x = n % 3, y = n / 3, current = grid.get(n);
        return getVertical(x) || getHorizontal(y) || getDiagonal(x, y);

    }

    public static int getPos(int x, int y){
        return x + 3 * y;
    }

    public static boolean getVertical(int x){
        return Objects.equals(grid.get(getPos(x, 0)), grid.get(getPos(x, 1))) &&
                Objects.equals(grid.get(getPos(x, 1)), grid.get(getPos(x, 2)));
    }
    public static boolean getHorizontal(int y){
        return Objects.equals(grid.get(getPos(0, y)), grid.get(getPos(1, y))) &&
                Objects.equals(grid.get(getPos(1, y)), grid.get(getPos(2, y)));
    }

    public static boolean getDiagonal(int x, int y){
        if (x == 1 ^ y == 1){
            return false;
        } else {
            boolean to_return = false;
            if(x == y){
                to_return = Objects.equals(grid.get(getPos(0, 0)), grid.get(getPos(1, 1))) &&
                        Objects.equals(grid.get(getPos(1, 1)), grid.get(getPos(2, 2)));
            }
            if(x + y == 2){
                to_return |= Objects.equals(grid.get(getPos(0, 2)), grid.get(getPos(1, 1))) &&
                        Objects.equals(grid.get(getPos(1, 1)), grid.get(getPos(2, 0)));
            }
            return to_return;
        }
    }

}

class Btn extends JButton{
    public int label;
    public Btn(int label){
        super("");
        this.label = label;
    }
}