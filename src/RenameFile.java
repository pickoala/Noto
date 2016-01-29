import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by konstantin on 29/01/16.
 */
public class RenameFile extends JFrame {
    Container cp = getContentPane();
    JTextField oldName = new JTextField();
    JLabel arrow = new JLabel();
    JTextField newName = new JTextField();
    JButton bAppend = new JButton();

    int pd = 16;
    int cX = pd;
    int cY = pd;

    String sName;

    public RenameFile(String name, ActionListener al) {
        super("Rename");
        sName = name;

        oldName.setBounds(cX, cY, 200, 24);
        oldName.setText(sName);
        oldName.setEditable(false);
        cp.add(oldName);
        cX += oldName.getWidth() + pd;

        arrow.setBounds(cX, cY, 24, 24);
        arrow.setText("→");
        cp.add(arrow);
        cX += arrow.getWidth() + pd;

        newName.setBounds(cX, cY, 200, 24);
        cp.add(newName);
        cX += newName.getWidth();
        cY += 24 + pd;

        bAppend.setBounds(cX - 150, cY, 150, 24);
        bAppend.setText("Append");
        bAppend.addActionListener(al);
        cp.add(bAppend);
        cX += pd;
        cY += 24 + pd;

        createWindow();
    }
    void append() {
        Path oName = Paths.get("SavedNotes/" + sName);
        Path nName = Paths.get(newName.getText());
        try {
            Files.move(oName, oName.resolveSibling(nName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
    }
    void createWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(cX, cY + 22);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        setResizable(false);
        cp.setLayout(null);
        setVisible(true);
    }
}
