package zadanie1;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class MainZad1 {
    //execute uzywamy przy poleceniu create
    //executeUpdate uzywamy przy poleceniu insert, update i delete
    // executeQuery uzywamy przy poleceniu select

    // dane potrzebne do łączenia się z bazą H2
    public static final String DB_URL = "jdbc:h2:~/test2";
    public static final String USER = "sa";
    public static final String PASSWORD = "";

    public static void createTableMovies(DataSource ds) throws SQLException {
        try (Connection con = ds.getConnection()) {
            Statement stm = con.createStatement();
            stm.execute("drop table if exists movies;" +
                    "create table movies(" +
                    "id int primary key auto_increment," +
                    "title varchar(255)," +
                    "genre varchar(255)," +
                    "yearOfRelease int" +
                    ")"
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inputRecordToTableMovies(DataSource ds, String title, String genre, int yearOfRelease) throws SQLException {
        try (Connection con = ds.getConnection()) {
            PreparedStatement stm = con.prepareStatement("insert into movies(title, genre, yearOfRelease) values (?,?,?)");
            stm.setString(1, title);
            stm.setString(2,genre);
            stm.setInt(3, yearOfRelease);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMovie(DataSource ds, int id, String title, String genre, int yearOfRelease) throws SQLException {
        try (Connection con = ds.getConnection()) {
            PreparedStatement stm = con.prepareStatement("update movies set title=?, genre=?, yearOfRelease=? where id=?");
            stm.setInt(4, id);
            stm.setString(1, title);
            stm.setString(2, genre);
            stm.setInt(3, yearOfRelease);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie(DataSource ds, int id) throws SQLException {
        try (Connection con = ds.getConnection()) {
            PreparedStatement stm = con.prepareStatement("delete from movies where id=?");
            stm.setInt(1, id);
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnAllMovies(DataSource ds) throws SQLException {
        try (Connection con = ds.getConnection()) {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from movies");
            while (rs.next()) {
                System.out.print(rs.getInt(1) + " ");
                System.out.print(rs.getString(2) + " ");
                System.out.print(rs.getString(3) + " ");
                System.out.println(rs.getInt(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl(DB_URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);

//        createTableMovies(ds);
//        inputRecordToTableMovies(ds, "Krzyk", "horror", 2001);
//        inputRecordToTableMovies(ds, "Kobiety mafii", "kryminał", 2017);
//        inputRecordToTableMovies(ds, "Głupi i głupszy", "komedia", 2002);
//        updateMovie(ds, 1, "Chłopaki nie płaczą", "komedia", 2003);
//        deleteMovie(ds, 2);
        returnAllMovies(ds);
    }
}
