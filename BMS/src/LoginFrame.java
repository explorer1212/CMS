import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

public class LoginFrame extends JFrame {
    public LoginFrame(Connection conn) {
        this.conn = conn;
    }
    private String sysName = "登录";
    public Connection conn = null;
    JFrame jf = new JFrame(sysName);
    JButton btnLogin = new JButton("登录");
    JButton btnRegister = new JButton("注册");

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
                TrueLoginFrame trueLoginFrame = new TrueLoginFrame(conn);
                trueLoginFrame.run();
                jf.dispose();
            }
        });

        // 注册
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterFrame registerFrame = new RegisterFrame(conn);
                registerFrame.run();
            }
        });
    }

    private void add() {
        JPanel panel = new JPanel();
        panel.setLayout(null); // 有这条代码后能直接设置其他组件的位置
        panel.add(btnLogin);
        panel.add(btnRegister);
        jf.add(panel);

    }

    private void setVisible() {
        jf.setVisible(true);
        btnLogin.setVisible(true);
        btnRegister.setVisible(true);
    }

    private void setSizeAndPosition() {

        jf.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - 180,
                (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - 100, 600, 600);
        jf.setSize(360, 200);    //设置窗体大小
        btnRegister.setBounds(105, 100, 60, 30);
        btnLogin.setBounds(195, 100, 60, 30);
    }

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame(null);
    }


}
