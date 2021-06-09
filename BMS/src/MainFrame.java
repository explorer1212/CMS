import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import javax.swing.*;

public class MainFrame extends JFrame {    //创建一个类继承JFrame类

    public MainFrame(Connection conn, String user) {
        this.conn = conn;
        this.user = user;
        lblUser.setText(user + " 欢迎！");
    }

    public Connection conn = null;
    String sysName = "校园卡管理系统";
    String user;
    JLabel lblUser = new JLabel();
    JButton btnCharge = new JButton("充值");
    JButton btnCost = new JButton("消费");
    JButton btnChange = new JButton("修改密码");
    JButton btnQuery = new JButton("查询记录");
    JButton btnClose = new JButton("退出");
    JButton btnLogoff = new JButton("注销");
    JButton btnMoney = new JButton("查询余额");
    JFrame jf = new JFrame(sysName);
    JTextField tf = new JTextField();

    JTable table = new JTable(6, 3);
    JScrollPane pane = new JScrollPane(table);

    public void run() {    //定义一个CreateJFrame方法
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        jf.getContentPane();

        // 设置大小和位置
        setSizeAndPosition();
        // 设置可见
        setVisible();
        // 增加组件到窗体中
        add();
        // 设置组件的方法
        setFuc();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //设置窗体关闭方式
    }

    private void setFuc() {
        btnMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT Cmoney FROM CARDS WHERE Sid = " + user);
                    while (rs.next()) {
                        int money = rs.getInt(1);
                        tf.setText("余额：" + Integer.toString(money));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        btnCharge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("充值");
                tf.setBackground(Color.cyan);
                ChargeFrame chargeFrame = new ChargeFrame(conn, user);
                chargeFrame.run();
            }
        });

        btnCost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("消费");
                tf.setBackground(Color.cyan);
                CostFrame costFrame = new CostFrame(conn, user);
                costFrame.run();
            }
        });

        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("修改密码");
                ChangePasswordFrame changePasswordFrame = new ChangePasswordFrame(conn, user);
                changePasswordFrame.run();
            }
        });

        btnQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("查询记录");
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM RECORD WHERE Sid = " + user);
                    int cnt = 0;
                    while (rs.next()) {
                        ++cnt;
                    }
                    Object[][] info = new Object[cnt][3];
                    cnt = 0;
                    rs = stmt.executeQuery("SELECT * FROM RECORD WHERE Sid = " + user);
                    while (rs.next()) {
                        info[cnt][0] = rs.getString(1); // 注意：索引从1开始
                        info[cnt][1] = rs.getInt(2);
                        info[cnt++][2] = rs.getTimestamp(3);
                    }
                    String[] title = {"学号", "钱", "时间"};
                    table = new JTable(info, title);
                    pane.getViewport().add(table);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn.close();
                    jf.dispose();
                    System.out.println("断开与数据库的连接并退出");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
        });

        btnLogoff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("注销");
                LogoffFrame logoffFrame = new LogoffFrame(conn, user);
                if (logoffFrame.run()) {
                    try {
                        conn.close();
                        jf.dispose();

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

            }
        });
    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(tf);
        panel.add(btnCharge);
        panel.add(btnCost);
        panel.add(btnChange);
        panel.add(btnQuery);
        panel.add(btnClose);
        panel.add(lblUser);
        panel.add(btnLogoff);
        panel.add(pane);
        panel.add(btnMoney);
        jf.add(panel);
    }

    private void setVisible() {
        jf.setVisible(true);
        btnCharge.setVisible(true);
        btnCost.setVisible(true);
        btnChange.setVisible(true);
        btnQuery.setVisible(true);
        btnClose.setVisible(true);
        lblUser.setVisible(true);
        btnLogoff.setVisible(true);
        pane.setVisible(true);
        btnMoney.setVisible(true);
    }

    private void setSizeAndPosition() {
        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 450,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 300, 600, 600);
        jf.setSize(900, 600);    //设置窗体大小
        btnCharge.setBounds(800, 100, 90, 30);
        btnCost.setBounds(800, 160, 90, 30);
        btnChange.setBounds(800, 220, 90, 30);
        btnQuery.setBounds(800, 280, 90, 30);
        btnLogoff.setBounds(800, 340, 90, 30);
        btnClose.setBounds(800, 400, 90, 30);
        tf.setBounds(0, 20, 360, 20);
        btnMoney.setBounds(800, 400, 90, 30);
        btnClose.setBounds(800, 460, 90, 30);
        lblUser.setBounds(0, 0, 360, 20);
        pane.setBounds(90, 90, 600, 400);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(null, "18020031058");
        frame.run();
    }
}