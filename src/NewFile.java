import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by konstantin on 25/01/16.
 */
public class NewFile extends JFrame {
    Container cp = getContentPane();
    JTextField text = new JTextField();
    //
    int pd = 16;
    int current = pd;
    int finalW = 200;
    public NewFile() {
        super("Neu...");
        text.setBounds(pd, pd, finalW - 2 * pd, 24);
        cp.add(text);
        current += text.getHeight() + pd;
        JButton button = new JButton();
        button.setBounds(pd, current, finalW - 2 * pd, 24);
        button.setText("Add");
        button.addActionListener( a -> createNote() );
        cp.add(button);
        current += text.getHeight() + pd;
        createWindow();
    }
    void createNote() {
        String name = text.getText();
        if (name.length() == 0) {
            System.out.println("Empty Input-String");
        } else {
            try {
                FileOutputStream fos = new FileOutputStream("SavedNotes/" + name);
                fos.write("Hello".getBytes());
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        dispose();
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
