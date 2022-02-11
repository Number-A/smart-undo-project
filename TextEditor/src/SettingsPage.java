//package src;

//package com.example.textedit;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class SettingsPage {
    Settings settings;

    // Language Translations
    private Map<String,String> windowTitleTranslation = Map.of("en", "Settings Window", "fr", "Parametres");
    private Map<String,String> labelAppTranslation = Map.of("en", "Application Settings", "fr", "Parametres de l'application");
    private Map<String,String> labelAccessTranslation = Map.of("en", "Accessibility Settings", "fr", "Parametres d'accessibilite");
    private Map<String,String> labelActionTranslation = Map.of("en", "Action Limits", "fr", "Limites d'actions");
    private Map<String,String> labelAppLanguageTranslation = Map.of("en", "Application Language", "fr", "Langue de l'application");
    private Map<String,String> supportedLanguagesEnglishTranslation = Map.of("en", "English", "fr", "Anglais");
    private Map<String,String> supportedLanguagesFrenchTranslation = Map.of("en", "French", "fr", "Francais");
    private Map<String,String> labelFontSizeTranslation = Map.of("en", "Font Size", "fr", "Taille de police");
    private Map<String,String> labelMaxDeleteTranslation = Map.of("en", "Maximum group edits delete", "fr", "Nombre maximal de modifications de groupe supprimees");
    private Map<String,String> labelEditMaxDeleteTranslation = Map.of("en", "Edits", "fr", "Modifications");

    private JFrame frame = new JFrame();
    private JLabel labelApp;
    private JLabel labelAccess ;
    private JLabel labelAction;
    private Border borderApp;
    private Border borderAccess;
    private Border borderAction;

    //applicationSettings
    private JLabel labelAppLanguage;

    private String[] supportedLanguages = {"English", "French"};

    private JComboBox comboBoxLanguage;

    //accessibilitySettings

    private JLabel labelFontSize;

    private String[] fontSizes = {"10", "11", "12", "13", "14", "15", "16", "36"};

    private JComboBox comboBoxFontSize;

    //actionLimitSettings
    //5 is the default but there should be an option for less edits also

    private JLabel labelMaxDelete;

    private String[] numEditMaxDelete = {"10", "15", "20", "1", "3", "5", "None"};

    private JComboBox comboBoxEditMaxDelete;

    private JLabel labelEditMaxDelete;

    SettingsPage(Settings settings){
        this.settings = settings;
        Settings();
    }

    SettingsPage() {
        Settings();
    }

    private void Settings(){
        initComponents();
        addFrame();
        setValues();

    }

    private void initComponents() {
        borderApp = BorderFactory.createLineBorder(Color.BLACK, 2);
        borderAccess = BorderFactory.createLineBorder(Color.BLACK, 2);
        borderAction = BorderFactory.createLineBorder(Color.BLACK, 2);

        labelApp = new JLabel(labelAppTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
        labelAccess = new JLabel(labelAccessTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
        labelAction = new JLabel(labelActionTranslation.get(settings.getLanguage()), SwingConstants.CENTER);

        //applicationSettings
        labelAppLanguage = new JLabel(labelAppLanguageTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(supportedLanguages);
        comboBoxLanguage = new JComboBox();
        comboBoxLanguage.setModel(model);

        //accessibilitySettings
        labelFontSize = new JLabel(labelFontSizeTranslation.get(settings.getLanguage()), SwingConstants.CENTER);

        comboBoxFontSize = new JComboBox(fontSizes);

        //actionLimitSettings
        labelMaxDelete = new JLabel(labelMaxDeleteTranslation.get(settings.getLanguage()), SwingConstants.CENTER);

        comboBoxEditMaxDelete = new JComboBox(numEditMaxDelete);

        labelEditMaxDelete = new JLabel(labelEditMaxDeleteTranslation.get(settings.getLanguage()), SwingConstants.CENTER);
    }
    
    private void addFrame() {
        comboBoxFontSize.setSelectedIndex(getDefaultIndex());
    	ActionListener cbFontActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fontSize = Integer.parseInt(String.valueOf(comboBoxFontSize.getSelectedItem()));
                settings.setFontSize(fontSize);
                System.out.println("clicked combobox " + fontSize);
                frame.setVisible(false);
                frame.setVisible(true);
                updateAllUI();
                setValues();
            }
        };

        ActionListener cbALanguagectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String language = getLanguageCode(String.valueOf(comboBoxLanguage.getSelectedItem()));
                settings.setLanguage(language);
                frame.setVisible(false);
                frame.setVisible(true);
                updateAllUI();
                setValues();
            }
        };

        WindowListener w = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                settings.save();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };

        frame.addWindowListener(w);
        comboBoxLanguage.addActionListener(cbALanguagectionListener);
        comboBoxFontSize.addActionListener(cbFontActionListener);
        frame.setTitle("Settings Window");
        frame.add(labelApp);
        frame.add(labelAppLanguage);
        frame.add(comboBoxLanguage);
        frame.add(labelAccess);
        frame.add(labelFontSize);
        frame.add(comboBoxFontSize);
        frame.add(labelAction);
        frame.add(labelMaxDelete);
        frame.add(comboBoxEditMaxDelete);
        frame.add(labelEditMaxDelete);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(435,600);
        frame.setLayout(null);
        frame.setVisible(true);
        System.out.println("refresh");
    }
    
    private void setValues() {

        // SET LANGUAGES
        frame.setTitle(windowTitleTranslation.get(settings.getLanguage()));

        labelApp.setText(labelAppTranslation.get(settings.getLanguage()));
        labelAccess.setText(labelAccessTranslation.get(settings.getLanguage()));
        labelAction.setText(labelActionTranslation.get(settings.getLanguage()));
        labelAppLanguage.setText(labelAppLanguageTranslation.get(settings.getLanguage()));
        labelFontSize.setText(labelFontSizeTranslation.get(settings.getLanguage()));
        labelMaxDelete.setText(labelMaxDeleteTranslation.get(settings.getLanguage()));
        labelEditMaxDelete.setText(labelEditMaxDeleteTranslation.get(settings.getLanguage()));
        String[] newLanguage = {supportedLanguagesEnglishTranslation.get(settings.getLanguage()), supportedLanguagesFrenchTranslation.get(settings.getLanguage())};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(newLanguage);
        System.out.println("b4 " +comboBoxLanguage.getModel());
        comboBoxLanguage.setModel(model);
        System.out.println("after " + comboBoxLanguage.getModel());

        // SET FONT SIZES
        labelApp.setBounds(0,0,420,30);
        labelApp.setFont(new Font(null, Font.BOLD, settings.getFontSize()));
        labelApp.setBackground(Color.lightGray);
        labelApp.setOpaque(true);

        labelAppLanguage.setBounds(130, 70, 160, 20);
        labelAppLanguage.setFont(new Font(null, Font.PLAIN,settings.getFontSize()));

        comboBoxLanguage.setBounds(170, 100, 80, 20);

        labelAccess.setBounds(0,180,420,30);
        labelAccess.setFont(new Font(null, Font.BOLD,settings.getFontSize()));
        labelAccess.setBackground(Color.lightGray);
        labelAccess.setOpaque(true);

        labelFontSize.setBounds(120, 250, 180, 20);
        labelFontSize.setFont(new Font(null, Font.PLAIN,settings.getFontSize()));

        comboBoxFontSize.setBounds(170, 280, 80, 20);

        labelAction.setBounds(0,360,420,30);
        labelAction.setFont(new Font(null, Font.BOLD,settings.getFontSize()));
        labelAction.setBackground(Color.lightGray);
        labelAction.setOpaque(true);

        labelMaxDelete.setBounds(10, 410, 400, 60);
        labelMaxDelete.setFont(new Font(null, Font.PLAIN, settings.getFontSize()));

        // SET MISC

        labelApp.setBorder(borderApp);
        labelAccess.setBorder(borderAccess);
        labelAction.setBorder(borderAction);

        comboBoxEditMaxDelete.setBounds(170, 480, 80, 20);
    }

    private void updateAllUI(){
    	System.out.println("updating all UI");
        labelApp.updateUI();

        labelAppLanguage.updateUI();

        labelAccess.updateUI();

        labelFontSize.updateUI();

        labelAction.updateUI();
    }

    // given font size saved in settings, make it first value from fontSizes array
    private int getDefaultIndex() {

        printArray(fontSizes);
        System.out.println("Finding index for settings size: " + settings.getFontSize());
        for (int i = 0; i < fontSizes.length; i++) {
            if(settings.getFontSize() == Integer.parseInt(String.valueOf(fontSizes[i]))) {
                return i;
            }
        }
        return 0;
    }

    private void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private String getLanguageCode(String language) {
        switch(language) {
            case "French":
                return "fr";
            case "English":
                return "en";
                default:
                    return "en";
        }
    }
}
