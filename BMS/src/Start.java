import java.sql.*;

import java.sql.DriverManager;

public class Start {
    // 驱动路径
    private static final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // 数据库地址
    private static final String DBURL = "jdbc:sqlserver://localhost:1434;DataBaseName=CMS";
    // 数据库登录用户名
    private static final String DBUSER = "SA";
    // 数据库用户密码
    private static final String DBPASSWORD = "..OqmjtqO..123";
    // 数据库连接
    public static Connection conn = null;
    // 数据库查询结果集
    public static ResultSet rs = null;
    // 数据库查询操作
    public static Statement stmt = null;

    /**
     * 程序入口函数
     * @param args
     */
    public static void main(String[] args) {
        try {
            //加载驱动程序
            Class.forName(DBDRIVER);
            // 连接数据库
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);

            //实例化Statement对象
            stmt = conn.createStatement();
            // 登录
            LoginFrame loginFrame = new LoginFrame(conn);
            loginFrame.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
