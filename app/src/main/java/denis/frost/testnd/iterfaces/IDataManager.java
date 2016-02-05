package denis.frost.testnd.iterfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Денис on 05.12.2015.
 */
public interface IDataManager<T> {
    long saveImdbTop250(T obj) throws IOException, SQLException;
    long saveInTheaters(T obj) throws IOException, SQLException;
    long saveComingSoon(T obj) throws IOException, SQLException;
    boolean delete(T obj) throws SQLException;
    T getImdbTop250(int id) throws SQLException;
    T getInTheaters(int id) throws SQLException;
    T getComingSoon(int id) throws SQLException;
    List<T> getAllImdbTop250() throws SQLException;
    List<T> getAllInTheaters() throws SQLException;
    List<T> getAllComingSoon() throws SQLException;
    void deleteAll() throws SQLException;

}
