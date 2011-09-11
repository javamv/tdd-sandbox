package tetris;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:56
 */
public class CharBoard implements Board<String> {
    public CharBoard(int row, int column) {

    }

    public String render() {
        return null;
    }

    public boolean blockIsFalling() {
        return false;
    }

    public void drop(Block block, int row) {
    }

    public void tick() {
    }

    public Boolean canContinue() {
        return true;
    }
}
