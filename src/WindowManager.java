import javax.swing.*;
import java.awt.*;

/**
 * Created by konstantin on 04/02/16.
 */

public class WindowManager {

    public void createWindow(JFrame f, Container c, int x, int y) {

        f.setSize(x, y + 22);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(
                (d.width - f.getWidth()) / 2,
                (d.height - f.getHeight()) / 2
        );

        f.setResizable(false);
        c.setLayout(null);

        f.setVisible(true);

    }

}
