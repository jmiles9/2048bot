import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Game of 2048
 * Should pop up a window that the user can control with key presses. (need to
 * be able to change this to neural net input later.... )
 */
public class Game2048 {

    private static final int DEFAULT_WIDTH = 4;
    private static final int GAP = 2; //gap between rows/cols

    //private int gridWidth;  do we need this????
    private int currentScore;
    private int gridWidth;
    private ArrayList<ArrayList<Cell>> cellGrid;

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


        GamePanel display = new GamePanel(gridWidth);

        JFrame f = new JFrame();
       // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(display);
        f.setVisible(true);
        f.setSize(800, 1000);
    }


    public Game2048(){
        this(DEFAULT_WIDTH);
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

            //make a panel with a grid of boxes
            JPanel gameGrid = new JPanel(new GridLayout(gridWid, gridWid, GAP, GAP));
            this.add(gameGrid);

            //add cells, save cell objects in array so we can modify them later
            for(int col = 0; col < gridWid; col++){
                for(int row = 0; row < gridWid; row++){
                    JPanel cell = new JPanel();
                    cell.setBackground(DEFAULT_COLOUR);
                    gameGrid.add(cell);
                    cells.add(cell);
                }
            }
        }



    }

    public static void main (String[]args){
        //game time!
        Game2048 testGame = new Game2048();
    }
}

