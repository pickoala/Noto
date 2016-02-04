import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by konstantin on 25/01/16.
 */
public class Selector extends JFrame {
    Container cp = getContentPane();
    DefaultListModel model = new DefaultListModel();
    JList list = new JList(model);
    JTextField searchBox = new JTextField();
    ArrayList<String> el = new ArrayList<>();
    EditFile ef = new EditFile("0");
    WindowManager manager = new WindowManager();

    JFrame newFile = new JFrame();
    JFrame renameFile = new JFrame();

    int pd = 8;
    int sidebar = 150;
    int current = pd;
    int currentS = pd;
    int finalW = 600;

    public Selector() {
        super("Noto ALPHA");
        addMainList();
        addSearch();
        addSidebarButton("Add", a -> addNote() );
        currentS += 2 * pd;
        addSidebarButton("Open", a -> editNote() );
        addSidebarButton("Rename", a -> renameNote() );
        addSidebarButton("Delete", a -> deleteNote() );
        loadNotes();
        list.setSelectedIndex(0);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closingWindow();
            }
        });
        manager.createWindow(this, cp, finalW, current);
    }

    void addMainList() {
        list.setBounds(pd, pd, finalW - 2 * pd - sidebar, 500);
        cp.add(list);
        current += list.getHeight() + pd;
    }
    void addSearch() {
        searchBox.setBounds(pd, current, list.getWidth(), 24);
        searchBox.addActionListener( a -> search() );
        cp.add(searchBox);
        current += searchBox.getHeight() + pd;
    }
    void addSidebarButton(String s, ActionListener acl) {
        JButton b = new JButton();
        b.setBounds(finalW - pd - sidebar, currentS, sidebar, 24);
        b.setText(s);
        b.addActionListener(acl);
        cp.add(b);
        currentS += b.getHeight() + pd;
    }

    void addNote() {
        int cur = pd;
        int fw = 200;

        newFile = new JFrame();
        newFile.setTitle("New...");
        Container con = newFile.getContentPane();

        JTextField text = new JTextField();
        text.setBounds(pd, cur, fw - 2 * pd, 24);
        con.add(text);
        cur += text.getHeight() + pd;

        JButton append = new JButton();
        append.setBounds(pd, cur, fw - 2 * pd, 24);
        append.setText("Create");
        append.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String name = text.getText();
                if (name.length() == 0) System.out.println("Empty Input-String");
                else { try {
                        FileOutputStream fos = new FileOutputStream("SavedNotes/" + name);
                        fos.write("Hello".getBytes());
                    } catch (IOException exc) {
                        System.out.println(exc);
                    }
                }
                newFile.dispose();
                loadNotes();
            }
        });
        con.add(append);
        cur += append.getHeight() + pd;

        newFile.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        newFile.setSize(fw, cur + 22);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        newFile.setLocation( (d.width - newFile.getWidth()) / 2, (d.height - newFile.getHeight()) / 2);
        newFile.setResizable(false);
        con.setLayout(null);
        newFile.setVisible(true);
    }
    void editNote() {
        ef = new EditFile(model.getElementAt(list.getSelectedIndex()).toString());
        ef.setVisible(true);
    }
    void renameNote() {
        String sName = model.getElementAt(list.getSelectedIndex()).toString();
        new RenameFile(sName);
    }
    void deleteNote() {}
    void search() {
        String keyword = searchBox.getText();
        model.clear();
        for (int i = 0; i < el.size(); i++) {
            if (el.get(i).contains(keyword)) {
                model.addElement(el.get(i));
            }
        }
        if (model.size() != 0) {
            list.setSelectedIndex(0);
        }
    }
    
    void loadNotes() {
        model.clear();
        el.clear();
        final String loc = "SavedNotes";
        File[] files = new File(loc).listFiles();
        getFiles(files);
    }
    void getFiles(File[] files) {
        for (File file : files) {
            if (!file.getName().contains(".DS_Store")) {
                model.addElement(file.getName());
                el.add(file.getName());
            }
        }
    }
    void closingWindow() {
        if (renameFile.isVisible()) renameFile.dispose();
        if (newFile.isVisible()) newFile.dispose();
        if (ef.isVisible()) ef.dispose();
        dispose();
    }

    public static void main(String[] args) { new Selector(); }
}
