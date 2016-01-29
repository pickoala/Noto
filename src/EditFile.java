import javax.swing.*;
import java.awt.*;

/**
 * Created by konstantin on 25/01/16.
 */
public class EditFile extends JFrame {
    Container cp = getContentPane();
    int pd = 8;
    int current = pd;
    int finalW = 500;
    public EditFile(String fileName) {
        super(fileName);
        createWindow();
    }
    void createWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(finalW, current + 22);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        setResizable(false);
        cp.setLayout(null);
        setVisible(true);
    }
}
