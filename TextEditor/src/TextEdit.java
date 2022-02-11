//package src;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TextEdit extends JFrame implements ActionListener {
    private static JTextArea area;
    private static JFrame frame;
    private static JTable editTable;
    private static int returnValue = 0;
    private Stopwatch stopWatch;
    private final long EDIT_DELAY_MILLISECONDS = 2000;  // Save an edit after 2 seconds of inactivity
    private int charsChanged = 0;                       // Temporary; track number of characters added (positive) or removed (negative)
    public Settings settings;

    // Language Translations
    private Map<String,String> windowTitleTranslation = Map.of("en", "Text Edit", "fr", "");
    private Map<String,String> menuItemNewTranslation = Map.of("en", "New", "fr", "Nouveau");
    private Map<String,String> menuItemOpenTranslation = Map.of("en", "Open", "fr", "Ouvrir");
    private Map<String,String> menuItemSaveTranslation = Map.of("en", "Save", "fr", "Sauvegarder");
    private Map<String,String> menuItemQuitTranslation = Map.of("en", "Quit", "fr", "Quitter");

    private Map<String,String> menuItemBrilliantUndoTranslation = Map.of("en", "BrilliantUndo", "fr", "BrilliantUndo");
    private Map<String,String> menuItemUndoEditsTranslation = Map.of("en", "Undo Selected Edits", "fr", "Annuler les Modifications Selectionnees");
    private Map<String,String> menuItemDeleteEditsTranslation = Map.of("en", "Delete Selected Edits", "fr", "Supprimer les Modifications Selectionnees");
    private Map<String,String> menuItemClearEditHistoryTranslation = Map.of("en", "Clear Edit History", "fr", "Effacer l'Historique des Modifications");
    private Map<String,String> menuItemToggleEditHistoryTranslation = Map.of("en", "Toggle Edit History", "fr", "Toggle l'Historique des Modifications");
    private Map<String,String> menuItemToggleSettingsTranslation = Map.of("en", "Toggle Settings Page", "fr", "Toggle la page de Configuration");
    private Map<String,String> menuItemToggleHelpPageTranslation = Map.of("en", "Toggle Help Page", "fr", "Toggle la page d'aide");
    private Map<String,String> menuItemCloseWindowsTranslation = Map.of("en", "Close All Windows", "fr", "Fermer toutes les fenetres");

    public TextEdit() {
        settings = new Settings();
        run();
    }

    public void run() {
        frame = new JFrame("Text Edit");
        stopWatch = new Stopwatch();

        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set attributes of the app window
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        

        JPanel editHistoryPanel = new JPanel();
        JScrollPane scrollpane = new JScrollPane(editHistoryPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, area, scrollpane);

        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(560);

        Dimension minimumSize = new Dimension(160,480);

        area.setMinimumSize(minimumSize);
        scrollpane.setMinimumSize(minimumSize);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                OnChangeEvent(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                OnChangeEvent(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        area.getDocument().addDocumentListener(documentListener);

        //Drop Down Menus
        JMenuBar menu_main = new JMenuBar();

        JMenu menu_file = new JMenu("File");

        JMenuItem menuitem_new = new JMenuItem("New");
        JMenuItem menuitem_open = new JMenuItem("Open");
        JMenuItem menuitem_save = new JMenuItem("Save");
        JMenuItem menuitem_quit = new JMenuItem("Quit");

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menu_main.add(menu_file);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        frame.setJMenuBar(menu_main);

        //Add Brilliant Undo Menu

        JMenu menu_brilliant_undo = new JMenu("Brilliant Undo");

        JMenuItem menuitem_undo_edits = new JMenuItem("Undo Selected Edits");
        JMenuItem menuitem_delete_edits = new JMenuItem("Delete Selected Edits");
        JMenuItem menuitem_clear_edit_history = new JMenuItem("Clear Edit History");
        JMenuItem menuitem_toggle_edit_history = new JMenuItem("Toggle Edit History");
        JMenuItem menuitem_toggle_settings = new JMenuItem("Toggle Settings Page");
        JMenuItem menuitem_toggle_help_page = new JMenuItem("Toggle Help Page");
        JMenuItem menuitem_close_windows = new JMenuItem("Close All Windows");

        menuitem_undo_edits.addActionListener(this);
        menuitem_delete_edits.addActionListener(this);
        menuitem_clear_edit_history.addActionListener(this);
        menuitem_toggle_edit_history.addActionListener(this);
        menuitem_toggle_settings.addActionListener(this);
        menuitem_toggle_help_page.addActionListener(this);
        menuitem_close_windows.addActionListener(this);

        //Edit History

        JLabel editHistoryLabel = new JLabel("Edit History");
        editHistoryPanel.add(editHistoryLabel, 0);

        String[] headers = {"Type of edit", "Excerpt", "Number of characters", "Time stamp"};
        String[][] emptyEdit = {{"Add.", "This is ... a test.", "14", "10:57:38"},{"Add.", "Yay I ... is working.", "25", "10:57:59"}};

        //int[] colWidth = {30,120,30,60};

        editTable = new JTable(emptyEdit, headers);

        DefaultTableModel dtm = new DefaultTableModel(0, 4);

        dtm.addRow(new Object[] {"Paragraph", "", "", ""});

        for (int row = 0; row < 30; row++)
        {
            dtm.addRow(new Object[] {"Add.", "Hello my ... is Jason", Integer.toString(row), "9:28:27"});
        }

        dtm.removeRow(6);
        dtm.setValueAt("Del.", 4, 0);
        
        dtm.setColumnIdentifiers(headers);
        editTable.setModel(dtm);

        //Adding components
        editHistoryPanel.add(editTable);
        frame.add(splitPane);

        menu_main.add(menu_file);
        menu_main.add(menu_brilliant_undo);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        menu_brilliant_undo.add(menuitem_undo_edits);
        menu_brilliant_undo.add(menuitem_delete_edits);
        menu_brilliant_undo.add(menuitem_clear_edit_history);
        menu_brilliant_undo.add(menuitem_toggle_edit_history);
        menu_brilliant_undo.add(menuitem_toggle_settings);
        menu_brilliant_undo.add(menuitem_toggle_help_page);
        menu_brilliant_undo.add(menuitem_close_windows);

        frame.setJMenuBar(menu_main);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        if (ae.equals("Open")) {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try{
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
                    while(scan.hasNextLine()){
                        String line = scan.nextLine() + "\n";
                        ingest = ingest + line;
                    }
                    area.setText(ingest);
                }
                catch ( FileNotFoundException ex) { ex.printStackTrace(); }
            }
            // SAVE
        } else if (ae.equals("Save")) {
            returnValue = jfc.showSaveDialog(null);
            try {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                FileWriter out = new FileWriter(f);
                out.write(area.getText());
                out.close();
            } catch (FileNotFoundException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"File not found.");
            } catch (IOException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f,"Error.");
            }
        } else if (ae.equals("New")) {
            area.setText("");
        } else if (ae.equals("Quit")) { System.exit(0);
        } else if (ae.equals("Toggle Help Page")){ HelpPage myHelpPage = new HelpPage(settings);
        } else if (ae.equals("Toggle Settings Page")){ SettingsPage mySettingsPage = new SettingsPage(settings);
        }
    }

    // Called when the contents of the text document are changed
    // TODO: Call the 2-second check every loop (or every regular interval)
    private void OnChangeEvent(DocumentEvent e) {
        long time = stopWatch.getElapsedMilliseconds();
        if (time >= EDIT_DELAY_MILLISECONDS) {
            System.out.println("Elapsed milliseconds: " + time);
            // Save edit. For now, just report the number of characters added or removed
            System.out.println("Edit saved");
            if (charsChanged < 0) {
                System.out.println((charsChanged * -1) + " characters removed");
            } else {
                System.out.println(charsChanged + " characters added");
            }
            charsChanged = 0;
        }
        // TODO: Track the edit in a string buffer
        // TODO: Account for copy/paste action, or highlight-deletes
        if (e.getType().equals(DocumentEvent.EventType.INSERT)) {
            charsChanged++;
        }  else if (e.getType().equals(DocumentEvent.EventType.REMOVE)) {
            charsChanged--;
        }
        stopWatch.start();
    }
}
