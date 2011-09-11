package tetris;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static tetris.FallingBlockStoryTestSuite.*;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:47
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({WhenABoardCreated.class, WhenTheBlockIsDropped.class, WhenTheBlockHitTheBottom.class, WhenABlockHitAnotherBlock.class})
public class FallingBlockStoryTestSuite {

    /*Falling Block Story:
    - When a board created: should be render in textual form filled by '*', no falling blocks
    - When the block is dropped: block can be any simple letter, it should move one row per tick
    - When the block hit the bottom: on the next tick new block is automatically dropped
    - When a block hit another block: on the next tick new block is automatically dropped, game over if the block hit another block on the first row*/

    public static class WhenABoardCreated {

        Board<String> board;

        @Before
        public void setUpTheBoard() {
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
        Board<String> board;

        @Before
        public void setUpTheBoard() {
            board = new CharBoard(3, 4);
            Block block = new CharBlock('X');
            board.drop(block, 2);
        }

        @Test
        @Ignore
        public void shouldRenderBlockAtTheMiddle() {
            assertThat(board.render(), is("" +
                    "*X*\n" +
                    "***\n" +
                    "***\n" +
                    "***\n"));
        }

        @Test
        @Ignore
        public void shouldMoveOneRowPerTick() {
            board.tick();
            assertThat(board.render(), is("" +
                    "***\n" +
                    "*X*\n" +
                    "***\n" +
                    "***\n"));
        }

        @Test
        @Ignore
        public void shouldBeFalling() {
            assertThat(board.blockIsFalling(), is(true));
        }

        @Test
        @Ignore
        public void onlyOneFallingBlockShouldBeOnTheBoard() {
            try {
                board.drop(new CharBlock('Y'), 3);
            } catch (Exception e) {
                assertThat(e.getMessage(), is("Shouldn't be two falling blocks"));
            }
            fail("Illegal state not processed: two block are falling");
        }
    }

    public static class WhenTheBlockHitTheBottom {
        Board<String> board;

        @Before
        public void setUpContext() {
            board = new CharBoard(3, 3);
            board.drop(new CharBlock('X'), 1);//first row
            board.tick();//second row
        }

        @Test
        @Ignore
        public void shouldStillFalling() {
            assertThat(board.blockIsFalling(), is(true));
        }

        @Test
        @Ignore
        public void shouldNotFallingOnTheNextTick() {
            board.tick();//third row
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        @Ignore
        public void shouldRenderAtTheBottom() {
            assertThat(board.render(), is("" +
                    "***\n" +
                    "***\n" +
                    "*X*\n"));
        }

        @Test
        @Ignore
        public void shouldAutomaticallyDropNewBlockOnTheNextTickAtTheMiddle() {
            assertThat(board.render(), is("" +
                    "*X*\n" +
                    "***\n" +
                    "*X*\n"));
        }

    }

    public static class WhenABlockHitAnotherBlock {
        /*on the next tick new block is automatically dropped, game over if the block hit another block on the first row*/

        Board<String> board;

        @Before
        public void setUpContext() {
            board = new CharBoard(5, 3);
            board.drop(new CharBlock('X'), 4); // first row
            board.tick(); //second row
            board.tick(); //third row, still falling
            board.tick(); //third row, not falling
            board.tick(); //new block at first row
        }

        @Test
        @Ignore
        public void shouldBeAboveOnTheNextTick() {
            board.tick();
            assertThat(board.render(), is("" +
                    "***\n" +
                    "*X*\n" +
                    "*X*\n"));
        }

        @Test
        @Ignore
        public void shouldNotFalling() {
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        @Ignore
        public void shouldAutomaticallyDropNewBlockOnTheNextTickAtTheMiddle() {
            assertThat(board.render(), is("" +
                    "*X*\n" +
                    "*X*\n" +
                    "*X*\n"));
        }

        @Test
        @Ignore
        public void lastBlockShouldNotFalling() {
            assertThat(board.blockIsFalling(), is(false));
        }

        @Test
        @Ignore
        public void givenBlockNotFallingAtTheFirstRowResultsGameOver() {
            assertThat(board.canContinue(), is(false));
        }
    }
}
