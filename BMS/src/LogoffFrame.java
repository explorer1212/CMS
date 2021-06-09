import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LogoffFrame extends JFrame {
    public LogoffFrame(Connection conn, String id) {
        this.id = id;
        this.conn = conn;
    }

    Connection conn;
    String id;
    String sysName = "注销";
    JFrame jf = new JFrame(sysName);
    JButton btnConfirm = new JButton("确认?");
    JButton btnClose = new JButton("取消");
    boolean ok = true;

    public boolean run() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        jf.getContentPane();

        // 设置大小和位置
        setSizeAndPosition();
        // 设置可见
        setVisible();
        // 增加标签到窗体中
        add();
        // 设置组件的方法
        setFuc();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //设置窗体关闭方式

        System.out.println("ok = " + ok);
        return false;
    }


    private void setSizeAndPosition() {
        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 150,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - 100, 600, 600);
        jf.setSize(300, 200);
        btnConfirm.setBounds(55, 90, 70, 30);
        btnClose.setBounds(165, 90, 70, 30);

    }

    private void setVisible() {
        jf.setVisible(true);
        btnConfirm.setVisible(true);
        btnClose.setVisible(true);
    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(btnConfirm);
        panel.add(btnClose);
        jf.add(panel);
    }

    private void setFuc() {
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (Statement stmt = conn.createStatement()) {

                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE Sid=?")) {
                        ps.setObject(1, id); // 注意：索引从1开始
                        int n = ps.executeUpdate(); // 删除的行数
                    }

//                    sql = "DELETE FROM CARDS DBCC CHECKIDENT ('CMS.dbo.STUDENTS',RESEED, 0)"
//                            + " WHERE Sid = " + id;
//                    stmt.executeQuery(sql);
//                    sql = "DELETE FROM RECORD DBCC CHECKIDENT ('CMS.dbo.STUDENTS',RESEED, 0)"
//                            + " WHERE Sid = " + id;

                    conn.close();
                    jf.dispose();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                ok = false;
            }
        });
    }
    public static void main(String[] args) {
        LogoffFrame logoffFrame = new LogoffFrame(null, "0");
        logoffFrame.run();
    }
}
