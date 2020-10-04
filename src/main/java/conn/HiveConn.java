package conn;

import org.apache.hive.jdbc.HiveStatement;

import java.sql.*;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-31
 */
public class HiveConn {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static String Url = "jdbc:hive2://mini1:10000";

    private static Connection conn = null;
    //    private static Statement state = null;
    private static ResultSet res = null;


    public static void main(String[] args) {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(Url, "root", "123456");
//            PreparedStatement statement = conn.prepareStatement("select * from my.test1");
//            ResultSet resultSet = statement.executeQuery();
//            resultSet

            Statement statement = conn.createStatement();
            HiveStatement hiveStatement = (HiveStatement) conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("select * from qftest.test1 where userid=?");
            ps.setString(1,"A");
            ResultSet rs = ps.executeQuery();
//            ResultSet rs = statement.executeQuery("select * from qftest.test1");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
            }
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
