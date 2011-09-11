package tetris;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 12:56
 */
public interface Board<T, V> {
    T render();

    boolean blockIsFalling();

    void drop(Block<V> block, int column);

    void tick();

    Boolean canContinue();
}
