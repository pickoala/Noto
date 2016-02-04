import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by konstantin on 04/02/16.
 */
public class RenameFile extends JFrame {
    Container cp = getContentPane();
    WindowManager manager = new WindowManager();
    JTextField newName = new JTextField();

    int pd = 16;
    int finalw = 240;
    int current = pd;

    String workName;

    public RenameFile(String name) {
        setTitle("Rename");
        workName = name;

        JLabel lOldName = new JLabel();
        lOldName.setBounds(pd, current, finalw - 2 * pd, 24);
        lOldName.setText("Old name:");
        cp.add(lOldName);
        current += lOldName.getHeight();

        JTextField oldName = new JTextField();
        oldName.setBounds(pd, current, finalw - 2 * pd, 24);
        oldName.setText(name);
        oldName.setEditable(false);
        cp.add(oldName);
        current += oldName.getHeight() + pd;

        JLabel lNewName = new JLabel();
        lNewName.setBounds(pd, current, finalw - 2 * pd, 24);
        lNewName.setText("New Name");
        cp.add(lNewName);
        current += lNewName.getHeight();

        newName.setBounds(pd, current, 200, 24);
        cp.add(newName);
        current += newName.getHeight() + pd;

        JButton bAppend = new JButton();
        bAppend.setBounds(pd, current, finalw - 2 * pd, 24);
        bAppend.setText("Append");
        bAppend.addActionListener( a -> rename());
        cp.add(bAppend);
        current += bAppend.getHeight() + pd;
        manager.createWindow(this, cp, finalw, current);
    }
    void rename() {
        Path oName = Paths.get("SavedNotes/" + workName);
        if (newName.getText().isEmpty() || newName.getText().length() == 0) { dispose(); return; }
        Path nName = Paths.get(newName.getText());
        try {
            Files.move(oName, oName.resolveSibling(nName));

        } catch (IOException exc) {
            exc.printStackTrace();
        }
        dispose();
        Selector.getWindows()[0].dispose();
        new Selector();}
}
