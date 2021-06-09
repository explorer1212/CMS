import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TrueLoginFrame extends JFrame {
    public TrueLoginFrame(Connection conn) {
        this.conn = conn;
    }
    private String sysName = "登录";
    public Connection conn = null;
    JFrame jf = new JFrame(sysName);

    JLabel lblUser = new JLabel("学号");
    JLabel lblPassword = new JLabel("密码");
    JLabel lblInfo = new JLabel();
    JTextField txtUser = new JTextField();
    JTextField txtPassword = new JTextField();
    JButton btnLogin = new JButton("登录");


    public void run() {    //定义一个CreateJFrame方法
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
    }

    private void setFuc() {
        // 登录
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = txtUser.getText();
                String password = txtPassword.getText();
                System.out.println(user + " " + password);
                try (Statement stmt = conn.createStatement()) {
                    try (ResultSet rs = stmt.executeQuery("SELECT Cmoney, Sid, Cpassword " +
                            "FROM CARDS WHERE Sid = " + user + " AND Cpassword = " + password)) {

                        while (rs.next()) {
                            MainFrame mainFrame = new MainFrame(conn, user);
                            mainFrame.run();
                            jf.dispose();
                            return;
                        }
                        lblInfo.setText("用户名或密码错误！");
                        lblInfo.setForeground(Color.RED);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(btnLogin);
        panel.add(lblPassword);
        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(txtPassword);
        panel.add(lblInfo);
        jf.add(panel);

    }

    private void setVisible() {
        jf.setVisible(true);
        btnLogin.setVisible(true);
        lblPassword.setVisible(true);
        lblUser.setVisible(true);
        txtUser.setVisible(true);
        txtPassword.setVisible(true);
        lblInfo.setVisible(true);
    }

    private void setSizeAndPosition() {
        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 180,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - 150, 600, 600);
        jf.setSize(360, 300);    //设置窗体大小
        lblUser.setBounds(30, 30, 60, 30);
        txtUser.setBounds(90, 30, 160, 30);
        lblPassword.setBounds(30, 90, 60, 30);
        txtPassword.setBounds(90, 90, 160, 30);
        btnLogin.setBounds(150, 180, 60, 30);
        lblInfo.setBounds(120, 210, 120, 30);
    }

    public static void main(String[] args) {

    }


}
