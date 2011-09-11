package tetris;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:56
 */
public class CharBoard implements Board<String, Character> {

    char[][] board;
    private Block<Character> fallingBlock, lastBlock;
    private static final int INITIAL_ROW = 0;

    public CharBoard(int rows, int columns) {
        board = new char[rows][columns];
        for (char[] tmp : board) {
            Arrays.fill(tmp, '*');
        }
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        for (char[] rows : board) {
            for (char column : rows) {
                sb.append(column);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public boolean blockIsFalling() {
        return fallingBlock != null;
    }

    public void drop(Block<Character> block, int column) {
        if (fallingBlock != null) {
            throw new RuntimeException("Shouldn't be two falling blocks");
        }
        fallingBlock = block;
        int normalizedColumn = column - 1;
        block.setCoordinates(INITIAL_ROW-1, normalizedColumn);
        if (! moveFallingBlock()) {
            fallingBlock = null;
            lastBlock = null;
        };
    }

    public void tick() {
        if (fallingBlock == null) {
            drop(new CharBlock(lastBlock.render()), lastBlock.getCurrentColumn() + 1);
            return;
        }
        if (! moveFallingBlock()) {
            lastBlock = fallingBlock;
            fallingBlock = null;
        }
    }

    private boolean moveFallingBlock() {
        int currentRow = fallingBlock.getCurrentRow();
        int currentColumn = fallingBlock.getCurrentColumn();
        if (currentRow >= 0) {
            board[currentRow][currentColumn] = '*';
        }
        int nextRow = fallingBlock.nextRow();
        board[nextRow][currentColumn] = fallingBlock.render();
        return canMoveFurther(nextRow, currentColumn);
    }

    private boolean canMoveFurther(int row, int column) {
        boolean notAtTheBottom = row < board.length - 1;
        boolean notAboveAnotherBlock = true;
        if (board.length - 1 >= row+1) {
            notAboveAnotherBlock = board[row+1][column] == '*';
        }
        return notAtTheBottom && notAboveAnotherBlock;
    }

    public Boolean canContinue() {
        return fallingBlock != null && lastBlock != null;
    }
}
