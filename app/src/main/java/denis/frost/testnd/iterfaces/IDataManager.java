package denis.frost.testnd.iterfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Денис on 05.12.2015.
 */
public interface IDataManager<T> {
    long save(T obj) throws IOException, SQLException;
    boolean delete(T obj) throws SQLException;
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;

}
