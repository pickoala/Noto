import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by konstantin on 25/01/16.
 */
public class Selector extends JFrame {
    Container cp = getContentPane();
    DefaultListModel model = new DefaultListModel();
    JList list = new JList(model);
    RenameFile rf;
    ActionListener al = ae -> renameHelper();

    int pd = 8;
    int sidebar = 150;
    int current = pd;
    int currentS = pd;
    int finalW = 600;

    public Selector() {
        super("Noto ALPHA");
        addMainList();
        addSidebarButton("Add", 0);
        currentS += 2 * pd;
        addSidebarButton("Open", 1);
        addSidebarButton("Rename", 2);
        addSidebarButton("Delete", 3);
        loadNotes();
        list.setSelectedIndex(0);
        createWindow();
    }
    void addMainList() {
        list.setBounds(pd, pd, finalW - 2 * pd - sidebar, 500);
        cp.add(list);
        current += list.getHeight() + pd;
    }
    void addSidebarButton(String s, int method) {

        JButton b = new JButton();
        b.setBounds(finalW - pd - sidebar, currentS, sidebar, 24);
        b.setText(s);
        switch (method) {

            case 0:
                b.addActionListener( a -> addNote() );
                break;
            case 1:
                b.addActionListener( a -> editNote() );
                break;
            case 2:
                b.addActionListener( a -> renameNote() );
                break;
            case 3:
                b.addActionListener( a -> deleteNote() );
            default:
                break;
        }
        cp.add(b);

        currentS += b.getHeight() + pd;
    }
    void addNote() { new NewFile(); }
    void editNote() {
        new EditFile(model.getElementAt(list.getSelectedIndex()).toString());
    }
    void renameNote() {
        rf = new RenameFile(model.getElementAt(list.getSelectedIndex()).toString(), al);
    }
    void renameHelper() {
        Path oName = Paths.get("SavedNotes/" + rf.sName);
        Path nName = Paths.get(rf.newName.getText());
        try {
            Files.move(oName, oName.resolveSibling(nName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        loadNotes();
        list.setSelectedIndex(0);
        rf.dispose();
    }
    void deleteNote() {}
    void loadNotes() {

        model.clear();
        final String loc = "SavedNotes";
        File[] files = new File(loc).listFiles();
        getFiles(files);
    }
    void getFiles(File[] files) {

        for (File file : files) {

            if (!file.getName().contains(".DS_Store")) {
                model.addElement(file.getName());
            }

        }

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
    public static void main(String[] args) { new Selector(); }
}
