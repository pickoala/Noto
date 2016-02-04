import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by konstantin on 25/01/16.
 */
public class EditFile extends JFrame {
    Container cp = getContentPane();
    JTextArea textArea = new JTextArea();
    JScrollPane scrollText = new JScrollPane(textArea);

    int pd = 8;
    int current = 0;
    int finalW = 500;

    String workName;

    public EditFile(String fileName) {
        super(fileName);
        addTextArea();
        workName = fileName;
        loadText();
        createWindow();
    }
    void addTextArea() {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollText.setBounds(pd, pd, finalW - 2 * pd, 500);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        a(scrollText);
        current += scrollText.getHeight() + 2 * pd;
    }
    void loadText() {
        String output = "#error";
        try {
            File file = new File("SavedNotes////" + workName);
            FileInputStream fin = new FileInputStream(file);
            output = "";
            int byteCounter = (int)file.length();
            byte[] source = new byte[byteCounter];
            fin.read(source);
            fin.close();
            for (int i = 0; i < byteCounter; i++) {
                output += (char)source[i];
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
            output = "#error";

        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
            output = "#error";

        } finally {
            textArea.setText(output);
        }
    }
    void saveText() {
        String written = textArea.getText();
        if (written == "#error" || written.length() == 0) {
            System.out.println("Empty Input-String : " + written);

        } else {
            try {
                FileOutputStream fos = new FileOutputStream("SavedNotes/" + workName);
                fos.write(written.getBytes());

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        dispose();
    }
    void a(Component c) {
        cp.add(c);
    }
    void createWindow() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //saveText();
                Selector.getWindows()[0].dispose();
                dispose();
            }
        });
        setSize(finalW, current + 22);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( (d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
        setResizable(false);
        cp.setLayout(null);
        if (workName == "0") return;
        else setVisible(true);
    }
}
