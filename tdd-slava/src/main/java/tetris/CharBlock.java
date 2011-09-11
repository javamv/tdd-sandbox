package tetris;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 13:12
 */
public class CharBlock implements Block<Character> {

    char letter;
    Integer row;
    Integer column;

    public CharBlock(char letter) {
        this.letter = letter;
    }

    public Character render() {
        return letter;
    }

    public void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getCurrentRow() {
        return row;
    }

    public int getCurrentColumn() {
        return column;
    }

    public int nextRow() {
        ++row;
        return row;
    }
}
