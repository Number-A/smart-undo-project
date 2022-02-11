//package src;

//package com.example.textedit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class HelpPage {

    // Language Translations
    private Map<String,String> windowTitleTranslation = Map.of("en", "Help Page", "fr", "Page d'aide");
    private Map<String,String> labelInfoTranslation = Map.of("en", "Application Information", "fr", "Informations sur l'application");
    private Map<String,String> labelShortcutsTranslation = Map.of("en", "Keyboard Shortcuts", "fr", "Raccourcis clavier");
    private Map<String,String> c0Translation = Map.of("en", "Undo Most Recent Edits", "fr", "Annuler mod. plus recentes");
    private Map<String,String> c1Translation = Map.of("en", "Multiple Undo", "fr", "Annulation multiple");
    private Map<String,String> c2Translation = Map.of("en", "Toggle Edit History", "fr", "Toggle Historique des Modifications");
    private Map<String,String> c3Translation = Map.of("en", "Toggle Settings Page", "fr", "Toggle la Page des Parametres");
    private Map<String,String> c4Translation = Map.of("en", "Toggle Help Page", "fr", "Toggle la Page d'Aide");
    private Map<String,String> c5Translation = Map.of("en", "Close All Windows", "fr", "Fermer toutes les fenetres");

    private JFrame frame = new JFrame();
    private JLabel labelInfo;
    private JLabel labelShortcuts;
    private Border borderInfo;
    private Border borderShortcuts;
    private JLabel appInfo;
    private JTable shortcutTable;
    private Settings settings;
    private final int indexShortcuts = 2;

    private String[][] shortcuts = {
        {"CTRL + Z", "CMD + Z", "Undo Most Recent Edits"},
        {"CTRL + UP", "CMD + UP", "Multiple Undo"},
        {"CTRL + ALT + SHIFT + E", "CMD + ALT + SHIFT + E", "Toggle Edit History"},
        {"CTRL + ALT + SHIFT + S", "CMD + ALT + SHIFT + S", "Toggle Settings Page"},
        {"CTRL + ALT + SHIFT + H", "CMD + ALT + SHIFT + H", "Toggle Help Page"},
        {"CTRL + D", "CMD + D", "Close All Windows"},
    };

    private String[] columnNames = {"Windows", "Apple", "Functionality"};

    HelpPage(Settings settings) {
        this.settings = settings;
        initComponents();
        init();
    }

    HelpPage() {
        initComponents();
        init();
    }

    private void initComponents() {
        labelInfo = new JLabel(labelInfoTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
        labelShortcuts = new JLabel(labelShortcutsTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
        borderInfo = BorderFactory.createLineBorder(Color.BLACK, 2);
        borderShortcuts = BorderFactory.createLineBorder(Color.BLACK, 2);
        appInfo = new JLabel("PLACEHOLDER FOR APPLICATION INFO TEXT");
        setLanguageTable();
    }

    private void init() {
        frame.setTitle(windowTitleTranslation.get(settings.getLanguage()));

        setValues();

        frame.add(labelInfo);
        frame.add(appInfo);
        frame.add(labelShortcuts);
        frame.add(shortcutTable);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(620,620);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void setValues() {

        // SET LANGUAGE
        frame.setTitle(windowTitleTranslation.get(settings.getLanguage()));

        labelInfo.setBounds(0,0,620,30);
        labelInfo.setFont(new Font(null, Font.PLAIN, settings.getFontSize()));
        labelInfo.setBackground(Color.lightGray);
        labelInfo.setOpaque(true);

        labelShortcuts.setBounds(0,180,620,30);
        labelShortcuts.setFont(new Font(null, Font.PLAIN, settings.getFontSize()));
        labelShortcuts.setBackground(Color.lightGray);
        labelShortcuts.setOpaque(true);

        labelInfo.setBorder(borderInfo);
        labelShortcuts.setBorder(borderShortcuts);

        appInfo.setBounds(0,30,620, 100);
        appInfo.setFont(new Font(null, Font.PLAIN, settings.getFontSize()));
        appInfo.setVerticalAlignment(SwingConstants.TOP);


        shortcutTable = new JTable(shortcuts, columnNames);
        shortcutTable.setBounds(0, 350, 620, 350);
        shortcutTable.setFont(new Font(null, Font.PLAIN, settings.getFontSize()));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        shortcutTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        shortcutTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        shortcutTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        shortcutTable.setRowHeight(28);
    }

    private void setLanguageTable() {
        shortcuts[0][indexShortcuts] = c0Translation.get(settings.getLanguage());
        shortcuts[1][indexShortcuts] = c1Translation.get(settings.getLanguage());
        shortcuts[2][indexShortcuts] = c2Translation.get(settings.getLanguage());
        shortcuts[3][indexShortcuts] = c3Translation.get(settings.getLanguage());
        shortcuts[4][indexShortcuts] = c4Translation.get(settings.getLanguage());
        shortcuts[5][indexShortcuts] = c5Translation.get(settings.getLanguage());
    }

}
