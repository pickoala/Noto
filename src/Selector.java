import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by konstantin on 25/01/16.
 */
public class Selector extends JFrame {
    Container cp = getContentPane();
    DefaultListModel model = new DefaultListModel();
    JList list = new JList(model);
    int pd = 8;
    int sidebar = 150;
    int current = pd;
    int currentS = pd;
    int finalW = 600;
    Selector() {
        super("Noto ALPHA");
        addMainList();
        addSidebarButton("Add", 0);
        addSidebarButton("Edit", 1);
        addSidebarButton("Delete", 2);
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
                b.addActionListener( a -> deleteNote() );
            default:
                break;
        }
        cp.add(b);
        currentS += b.getHeight() + pd;
    }
    void addNote() { new NewFile(); }
    void editNote() {
        String name = model.getElementAt(list.getSelectedIndex()).toString();
        new EditFile(name);
    }
    void deleteNote() {}
    void loadNotes() {
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
