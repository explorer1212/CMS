import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;


public class RegisterFrame extends JFrame {
    public RegisterFrame(Connection conn) {
        this.conn = conn;
    }
    private String sysName = "注册";
    public Connection conn = null;
    JFrame jf = new JFrame(sysName);
    JLabel lblSName = new JLabel("姓名");
    JLabel lblSID = new JLabel("学号");
    JLabel lblSsex = new JLabel("性别");

    JTextField txtSName = new JTextField();
    JTextField txtSID = new JTextField();
    JTextField txtSsex = new JTextField();

    JButton btnConfirm = new JButton("确认");
    public void run() {	//定义一个CreateJFrame方法
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
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//设置窗体关闭方式
    }

    private void setFuc() {
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtSName.getText();
                String id = txtSID.getText().toString();
                String sex = txtSsex.getText();
                try {
                    // 学生注册
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO STUDENTS (Sname, Sid, Ssex) VALUES (?,?,?)")) {
                        ps.setObject(1, name); // 注意：索引从1开始
                        ps.setObject(2, id); // grade
                        ps.setObject(3, sex); // name
                        int n = ps.executeUpdate(); // 1
                    }

                    // 学生卡
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO CARDS (Cmoney, Sid, Cpassword) VALUES (?,?,?)")) {
                        ps.setObject(1, 100); // 注意：索引从1开始
                        ps.setObject(2, id); // grade
                        ps.setObject(3, id); // name
                        int n = ps.executeUpdate(); // 1
                    }

                    // 预充值100块
                    Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO Record (Sid, Rmoney, Rdate) VALUES (?,?,?)")) {
                        ps.setObject(1, id); // 注意：索引从1开始
                        ps.setObject(2, 100); // grade
                        ps.setObject(3, sqlTimestamp.toString()); // name
                        int n = ps.executeUpdate(); // 1
                    }
                    // conn.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                jf.dispose(); // 关闭窗口
            }
        });
    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(lblSName);
        panel.add(lblSID);
        panel.add(lblSsex);
        panel.add(txtSName);
        panel.add(txtSID);
        panel.add(txtSsex);
        panel.add(btnConfirm);
        jf.add(panel);

    }

    private void setVisible() {
        jf.setVisible(true);
        lblSID.setVisible(true);
        lblSName.setVisible(true);
        lblSsex.setVisible(true);
        txtSID.setVisible(true);
        txtSName.setVisible(true);
        txtSsex.setVisible(true);
        btnConfirm.setVisible(true);
    }

    private void setSizeAndPosition() {
        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 180,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - 180, 600, 600);
        jf.setSize(360, 360);	//设置窗体大小
        lblSName.setBounds(30, 30, 60, 30);
        txtSName.setBounds(90, 30, 160, 30);
        lblSID.setBounds(30, 90, 60, 30);
        txtSID.setBounds(90, 90, 160, 30);
        lblSsex.setBounds(30, 150, 60, 30);
        txtSsex.setBounds(90, 150, 160, 30);
        btnConfirm.setBounds(120, 270, 60, 30);
    }

    public static void main(String[] args) {
        RegisterFrame registerFrame = new RegisterFrame(null);
        registerFrame.run();
    }
}
