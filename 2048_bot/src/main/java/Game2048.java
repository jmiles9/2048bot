import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Game of 2048
 * Should pop up a window that the user can control with key presses. (need to
 * be able to change this to neural net input later.... )
 */
public class Game2048 implements KeyListener {

    private static final int DEFAULT_WIDTH = 4;
    private static final int GAP = 2; //gap between rows/cols
    private static final int LEFT = 37;
    private static final int UP = 38;
    private static final int RIGHT = 39;
    private static final int DOWN = 40;

    //private int gridWidth;  do we need this????
    private int currentScore;
    private int gridWidth;
    private ArrayList<ArrayList<Cell>> cellGrid;

    private GamePanel display;

    /**
     * Initialises a game with a customised grid size
     * @param gridW : the width of the game grid. regular games are 4x4 --> gridWidth = 4
     */
    public Game2048(int gridW){
        currentScore = -1;
        this.gridWidth = gridW;
        this.cellGrid = new ArrayList<>();

        //initialise grid of empty cells
        for(int row = 0; row < gridWidth; row++){
            ArrayList<Cell> r = new ArrayList<>();
            for(int col = 0; col < gridWidth; col++){
                 r.add(new Cell());
            }
            cellGrid.add(r);
        }

        //initialise two cells at random to have a value of two or four
        //need 6 random integers, first four between 0-gridWidth, second two boolean
        Random rd = new Random();
        int oldC = -1;
        int oldR = -1;
        for(int i = 0; i < 2; i++){
            int col = -1;
            int row = -1;
            int count = 0;
            do {
                col = rd.nextInt(gridWidth);
                row = rd.nextInt(gridWidth);
                count ++;
            } while(col == oldC && row == oldR && count < 100); //make sure we don't accidentally get the same cell twice
            if(count >= 100) System.out.println("random number generator has a problem, restart pls"); //figure out better solution
            oldC = col;
            oldR = row;

            int num = rd.nextInt(2);
            if(num == 0) num = 2;
            if(num == 1) num = 4;

            cellGrid.get(row).get(col).filled = true;
            cellGrid.get(row).get(col).value = num;
        }


        display = new GamePanel(gridWidth);

        JFrame f = new JFrame();
       // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(display);
        f.addKeyListener(this);
        f.setVisible(true);
        f.setSize(800, 1000);
    }


    public Game2048(){
        this(DEFAULT_WIDTH);
    }

    /**
     *  takes the direction to move the cells, and creates a new cellgrid with all the cells shifted over
     *  in the designated direction. if two cells have the same value and collide, they should combine
     * @param direction the direction in which to move the cells. input should be one of LEFT, RIGHT, DOWN, UP.
     *                  these are defined above to be equal to the value of their respective keyboard arrow codes.
     */
    private void handleInput(int direction){
       // System.out.println("handling input");
        //go thru row (col) and move one by one. check next one over to see if same val, if yes, combine?
        //go thru, compare with next and swap if previous is empty. val same ==> combine (do later)
        //placeholder?? increment when filled??
        //after, add new tile. take num unfilled cells and get random num then place tile by counting that num?

        //TODO: this only shifts them left. change it so it does all directions
        if(direction == LEFT) {
            System.out.println("moving left");
            for (int row = 0; row < cellGrid.size(); row++) {
                int i = 0;
                int n = 1;
                while (n < cellGrid.size()) {
                    Cell nextCell = cellGrid.get(row).get(n);
                    if (nextCell.filled) {
                        Cell currCell = cellGrid.get(row).get(i);
                        if (!currCell.filled) {
                            //swap cells index i
                            cellGrid.get(row).set(n, new Cell()); //set farther index cell to empty
                            cellGrid.get(row).set(i, nextCell);
                            n++;
                        } else {
                            //if the comparison cell is not empty, check if it is the same value.
                            // if yes, combine. if no, increase i and DO NOT increase n
                            if (currCell.value == nextCell.value) {
                                cellGrid.get(row).get(i).value *= 2;
                                cellGrid.get(row).set(n, new Cell());
                                n++;
                            } else {
                                i++;
                                if (n == i) n++;
                            }
                        }
                    } else n++;
                }
            }
        }
        //SHIFT RIGHT///////////////
        else if(direction == RIGHT) {
            System.out.println("moving right");
            for (int row = 0; row < cellGrid.size(); row++) {
                int i = cellGrid.get(row).size() - 1;
                int n = i - 1;
                while (n >= 0) {
                    Cell nextCell = cellGrid.get(row).get(n);
                    if (nextCell.filled) {
                        Cell currCell = cellGrid.get(row).get(i);
                        if (!currCell.filled) {
                            //swap cells index i
                            cellGrid.get(row).set(n, new Cell()); //set farther index cell to empty
                            cellGrid.get(row).set(i, nextCell);
                            n--;
                        } else {
                            //if the comparison cell is not empty, check if it is the same value.
                            // if yes, combine. if no, increase i and DO NOT increase n
                            if (currCell.value == nextCell.value) {
                                cellGrid.get(row).get(i).value *= 2;
                                cellGrid.get(row).set(n, new Cell());
                                n--;
                            } else {
                                i--;
                                if (n == i) n--;
                            }
                        }
                    } else n--;
                }
            }
        }

        TESTprintgrid();

        display.updateGamePanel();
    }

    public void TESTprintgrid(){
        for(int row = 0; row < cellGrid.size(); row++){
            for(int col = 0; col < cellGrid.get(row).size(); col++){
                System.out.print(cellGrid.get(row).get(col).value + "  ");
            }
            System.out.println();
        }
    }

    public ArrayList<ArrayList<Integer>> getValueGrid(){
        ArrayList<ArrayList<Integer>> valGrid = new ArrayList<>();
        for(int row = 0; row < gridWidth; row++){
            ArrayList<Integer> r = new ArrayList<>();
            for(int col = 0; col < gridWidth; col++){
                r.add(cellGrid.get(row).get(col).value);
            }
            valGrid.add(r);
        }
        return valGrid;
    }

    //CELL CLASS///////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Cell object. Represents the boxes with the numbers in them
     */
    private class Cell{
        private int value;
        private boolean filled;

        //initialise an empty cell
        private Cell(){
            value = 0;
            filled = false;
        }

        private Cell(int val, boolean fill){
            value = val;
            filled = fill;
        }
    }


    ///GAME PANEL STUFF////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This boy does all the work with the GUI
     */
    private class GamePanel extends JPanel{

        private final Color DEFAULT_COLOUR = Color.LIGHT_GRAY;
        private final Color FIRST_COLOUR = Color.GRAY;
        private final Color SECOND_COLOUR = Color.PINK;
        private final Color THIRD_COLOUR = Color.RED;

        private int gridWid;
        private ArrayList<JPanel> cells;

        /**
         * Initialise a game on a gridW x gridW grid of boxes of the default colour,
         *  with two starting cells.
         * @param gridW : size of grid to define
         */
        private GamePanel(int gridW){
            super(new GridLayout(1,1)); //initialise a jpanel
            this.gridWid = gridW;
            cells = new ArrayList<>();

            this.updateGamePanel();
        }

        private void updateGamePanel(){
            System.out.println("updating game panel");
            //remove all the cells and remake with new cell array
            this.removeAll();
            cells.clear();

            //make a panel with a grid of boxes
            JPanel gameGrid = new JPanel(new GridLayout(gridWid, gridWid, GAP, GAP));
            this.add(gameGrid);

            //add cells, save cell objects in array so we can modify them later
            for(int row = 0; row < gridWid; row++) {
                for (int col = 0; col < gridWid; col++) {
                    JPanel cellPanel = new JPanel(new GridBagLayout());
                    Cell cell = cellGrid.get(row).get(col);
                    if (cell.filled) {
                        cellPanel.setBackground(FIRST_COLOUR);
                        JLabel l = new JLabel(String.valueOf(cell.value));
                        l.setFont(new Font("Arial", 1, 40));
                        cellPanel.add(l);
                    } else {
                        cellPanel.setBackground(DEFAULT_COLOUR);
                        cellPanel.add(new JLabel(" "));
                    }
                    gameGrid.add(cellPanel);
                    cells.add(cellPanel);
                }
            }
            this.validate();
        }

    }

    //KEY LISTENER INTERFACE METHODS///////////////////////////////////////////////////////////////////////////////////

    /**
     * When a key is pressed, if the key is one of the arrows, it should save the value and call the update function
     * name tbd.
     * @param e the key event. not really sure how this works but it does
     */
    public void keyPressed(KeyEvent e){
        System.out.println("key pressed");
        int keyCode = e.getKeyCode();

        if (keyCode == UP || keyCode == DOWN || keyCode == LEFT || keyCode == RIGHT)
            handleInput(keyCode);
    }

    //dont care about these guys but they have to be here
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main (String[]args){
        //game time!
        Game2048 testGame = new Game2048();
    }
}

