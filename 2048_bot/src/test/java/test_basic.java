import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class test_basic {

    @Test
    public void test0(){
        Game2048 testGame = new Game2048();
        ArrayList<ArrayList<Integer>> testVals = testGame.getValueGrid();

        for(int row = 0; row < testVals.size(); row++){
            for(int col = 0; col < testVals.get(row).size(); col++){
                System.out.print(testVals.get(row).get(col) + "  ");
            }
            System.out.println();
        }
    }
}
