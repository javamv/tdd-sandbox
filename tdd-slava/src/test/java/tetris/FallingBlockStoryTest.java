package tetris;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static tetris.FallingBlockStoryTest.*;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:47
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({WhenABoardCreated.class, WhenTheBlockIsDropped.class, WhenTheBlockHitTheBottom.class, WhenABlockHitAnotherBlock.class})
public class FallingBlockStoryTest {

    /*Falling Block Story:
    - When a board created: should be render in textual form filled by '*', no falling blocks
    - When the block is dropped: block can be any simple letter, it should move one row per tick
    - When the block hit the bottom: on the next tick new block is automatically dropped
    - When a block hit another block: on the next tick new block is automatically dropped, game over if the block hit another block on the first row*/

    public static class WhenABoardCreated {

        static Board<String, Character> board;

        @BeforeClass
        public static void setUpTheBoard() {
            board = new CharBoard(3, 3);
        }

        @Test
        public void shouldRenderInTextualForm() {
            assertThat(board.render(), is("" +
                    "***\n" +
                    "***\n" +
                    "***\n"));
        }

        @Test
        public void noFallingBlocks() {
            assertThat(board.blockIsFalling(), is(false));
        }

    }

    public static class WhenTheBlockIsDropped {
        static Board<String, Character> board;

        @BeforeClass
        public static void setUpTheBoard() {
            board = new CharBoard(4, 3);
            Block<Character> block = new CharBlock('X');
            board.drop(block, 2);
        }

        @Test
        public void shouldRenderBlockAtTheMiddle() {
            assertThat(board.render(), is("" +
                    "*X*\n" +
                    "***\n" +
                    "***\n" +
                    "***\n"));
        }

        @Test
        public void shouldMoveOneRowPerTick() {
            board.tick();
            assertThat(board.render(), is("" +
                    "***\n" +
                    "*X*\n" +
                    "***\n" +
                    "***\n"));
        }

        @Test
        public void shouldBeFalling() {
            assertThat(board.blockIsFalling(), is(true));
        }

        @Test
        public void onlyOneFallingBlockShouldBeOnTheBoard() {
            try {
                board.drop(new CharBlock('Y'), 3);
            } catch (Exception e) {
                assertThat(e.getMessage(), is("Shouldn't be two falling blocks"));
                return;
            }
            fail("Illegal state not processed: two block are falling");
        }
    }

    public static class WhenTheBlockHitTheBottom {
        static Board<String, Character> board;

        @BeforeClass
        public static void setUpContext() {
            board = new CharBoard(3, 3);
            board.drop(new CharBlock('X'), 1);//first row
            board.tick();//second row
        }

        @Test
        public void shouldStillFalling() {
            assertThat(board.blockIsFalling(), is(true));
        }

        @Test
        public void shouldNotFallingOnTheNextTick() {
            board.tick();//third row
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        public void shouldRenderAtTheBottom() {
            assertThat(board.render(), is("" +
                    "***\n" +
                    "***\n" +
                    "X**\n"));
        }

        @Test
        public void shouldAutomaticallyDropNewBlockOnTheNextTickAtTheMiddle() {
            board.tick();
            assertThat(board.render(), is("" +
                    "X**\n" +
                    "***\n" +
                    "X**\n"));
        }

    }

    public static class WhenABlockHitAnotherBlock {
        /*on the next tick new block is automatically dropped, game over if the block hit another block on the first row*/

        static Board<String, Character> board;

        @BeforeClass
        public static void setUpContext() {
            board = new CharBoard(3, 5);
            board.drop(new CharBlock('X'), 4); // first row
            board.tick(); //second row
            board.tick(); //third row, not falling
            board.tick(); //new block at first row
        }

        @Test
        public void shouldBeAboveOnTheNextTick() {
            board.tick(); //new block just above
            assertThat(board.render(), is("" +
                    "*****\n" +
                    "***X*\n" +
                    "***X*\n"));
        }

        @Test
        public void shouldNotFalling() {
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        public void shouldDropNewBlockOnTheNextTickAtTheMiddle() {
            board.tick();
            assertThat(board.render(), is("" +
                    "***X*\n" +
                    "***X*\n" +
                    "***X*\n"));
        }

        @Test
        public void lastBlockShouldNotFalling() {
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        public void givenBlockNotFallingAtTheFirstRowResultsGameOver() {
            assertThat(board.canContinue(), is(false));
        }
    }
}
