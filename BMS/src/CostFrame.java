import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class CostFrame extends JFrame {
    public CostFrame(Connection conn, String id) {
        this.id = id;
        this.conn = conn;
    }

    Connection conn;
    String id;
    String sysName = "充值";
    JFrame jf = new JFrame(sysName);
    JButton btnConfirm = new JButton("确认");
    JLabel lblInfo = new JLabel();
    JLabel lblMoney = new JLabel("金额");
    JButton btnClose = new JButton("关闭");
    JTextField txtMoney = new JTextField();

    public void run() {
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


    private void setSizeAndPosition() {
        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 150,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - 100, 600, 600);
        jf.setSize(300, 200);
        lblMoney.setBounds(30, 30, 60, 30);
        txtMoney.setBounds(90, 30, 90, 30);
        btnConfirm.setBounds(75, 90, 60, 30);
        btnClose.setBounds(165, 90, 60, 30);
        lblInfo.setBounds(50, 120, 100, 30);

    }

    private void setVisible() {
        jf.setVisible(true);
        lblMoney.setVisible(true);
        txtMoney.setVisible(true);
        btnConfirm.setVisible(true);
        btnClose.setVisible(true);
        lblInfo.setVisible(true);
    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(lblMoney);
        panel.add(txtMoney);
        panel.add(btnConfirm);
        panel.add(lblInfo);
        panel.add(btnClose);
        jf.add(panel);
    }

    private void setFuc() {
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int money = Integer.parseInt(txtMoney.getText());
                int n = 0;
                // 更新CARDS中的money
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE CARDS SET Cmoney = Cmoney - ? WHERE Sid = ?")) {
                    ps.setObject(1, money); // 注意：索引从1开始
                    ps.setObject(2, id); // grade
                    n = ps.executeUpdate();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                lblInfo.setForeground(Color.RED);
                if (1 == n) {
                    lblInfo.setText("缴费成功");

                    // 设置充值记录
                    Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO Record (Sid, Rmoney, Rdate) VALUES (?,?,?)")) {
                        ps.setObject(1, id); // 注意：索引从1开始
                        ps.setObject(2, -money); // grade
                        ps.setObject(3, sqlTimestamp.toString()); // name
                        n = ps.executeUpdate(); // 1
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    lblInfo.setText("余额不足！");
                }

            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
    }

    public static void main(String[] args) {
        CostFrame costFrame = new CostFrame(null, "18020030158");
        costFrame.run();
    }
}
