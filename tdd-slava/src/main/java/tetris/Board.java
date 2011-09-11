package tetris;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:56
 */
public interface Board<T> {
    T render();

    boolean blockIsFalling();

    void drop(Block block, int row);

    void tick();

    Boolean canContinue();
}
