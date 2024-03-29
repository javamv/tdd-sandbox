package tetris;

/**
 * Created by IntelliJ IDEA.
 * User: raycom
 * Date: 11.09.10
 * Time: 13:12
 */
public interface Block<T> {
    T render();

    void setCoordinates(int row, int column);

    int getCurrentRow();

    int getCurrentColumn();

    int nextRow();
}
