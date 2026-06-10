/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datastructuresproject;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class GUI extends JFrame {

   
    static final Color BG        = new Color(0x0D1B2A);
    static final Color PANEL     = new Color(0x1B2B3A);
    static final Color CARD      = new Color(0x1E3248);
    static final Color ACCENT    = new Color(0x148F77);
    static final Color ACCENT2   = new Color(0x2E86C1);
    static final Color AMBER     = new Color(0xD4AC0D);
    static final Color TEXT      = new Color(0xECF0F1);
    static final Color SUBTEXT   = new Color(0x85929E);
    static final Color BORDER    = new Color(0x2C3E50);
    static final Color DANGER    = new Color(0xE74C3C);
    static final Color SUCCESS   = new Color(0x27AE60);

    
    SportsSystem system;
    JPanel       contentPanel;
    CardLayout   cardLayout;
    JTextArea    consoleArea;

    
    public GUI() {
        system = new SportsSystem();
        seedData();

        setTitle("Red Deportiva UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(buildSidebar(),   BorderLayout.WEST);
        add(buildMain(),      BorderLayout.CENTER);

        setVisible(true);
    }

    
    private void seedData() {
        system.addSport("volleyball");
        system.addSport("rugby");
        system.addSport("taekwondo");
        system.addSport("swimming");
        system.addSport("basketball");
        system.addSport("football");
    }

    
    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(PANEL);
        side.setPreferredSize(new Dimension(220, 0));
        side.setBorder(new MatteBorder(0, 0, 0, 1, BORDER));

        JLabel logo = new JLabel("RED DEPORTIVA");
        logo.setForeground(TEXT);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setBorder(new EmptyBorder(24, 20, 8, 20));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(logo);

        JLabel sub = new JLabel("Proyecto Estructuras de Datos");
        sub.setForeground(SUBTEXT);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sub.setBorder(new EmptyBorder(0, 20, 20, 20));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        side.add(sub);

        side.add(separator());

        String[][] nav = {
            {"students",     "Estudiantes"},
            {"sports",       "Deportes"},
            {"connectivity", "Conectividad"},
            {"communities",  "Comunidades"},
            {"performance",  "Rendimiento"},
        };

        ButtonGroup bg = new ButtonGroup();
        for (String[] item : nav) {
            JToggleButton btn = navButton(item[1]);
            bg.add(btn);
            btn.addActionListener(e -> {
                cardLayout.show(contentPanel, item[0]);
                if (item[0].equals("students"))    refreshStudentTable();
                if (item[0].equals("sports"))      refreshSportsTable();
                if (item[0].equals("communities")) refreshCommunities();
            });
            side.add(btn);
            if (item[0].equals("students")) btn.setSelected(true);
        }

        side.add(Box.createVerticalGlue());
        return side;
    }

    private JToggleButton navButton(String text) {
        JToggleButton btn = new JToggleButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected()) {
                    g2.setColor(new Color(0x148F77, false));
                    g2.fillRoundRect(8, 2, getWidth()-16, getHeight()-4, 8, 8);
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(0x1E3248));
                    g2.fillRoundRect(8, 2, getWidth()-16, getHeight()-4, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(TEXT);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setBackground(new Color(0,0,0,0));
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    
    private JPanel buildMain() {
        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG);

        contentPanel.add(buildStudentsPanel(),     "students");
        contentPanel.add(buildSportsPanel(),       "sports");
        contentPanel.add(buildConnectivityPanel(), "connectivity");
        contentPanel.add(buildCommunitiesPanel(),  "communities");
        contentPanel.add(buildPerformancePanel(),  "performance");

        return contentPanel;
    }

    
    DefaultTableModel studentModel;
    JTable studentTable;

    private JPanel buildStudentsPanel() {
        JPanel p = darkPanel();
        p.setLayout(new BorderLayout(0, 16));
        p.setBorder(new EmptyBorder(24, 24, 0, 24));
        
        p.add(sectionHeader("Estudiantes", "Registra, busca y elimina estudiantes del sistema"), BorderLayout.NORTH);

        
        String[] cols = {"ID", "Nombre", "Deportes practicados", "Deportes de interés"};
        studentModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        studentTable = styledTable(studentModel);

        JScrollPane scroll = darkScroll(studentTable);
        p.add(scroll, BorderLayout.CENTER);

        JPanel bar = darkPanel();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JTextField idField   = darkField(8,  "ID");
        JTextField nameField = darkField(16, "Nombre");

        JButton addBtn  = accentButton("Registrar", ACCENT);
        JButton delBtn  = accentButton("Eliminar",  DANGER);
        JButton findBtn = accentButton("Buscar",   ACCENT2);

        addBtn.addActionListener(e -> showRegisterStudentDialog());
        delBtn.addActionListener(e -> {
            String txt = idField.getText().trim();
            if (txt.isEmpty()) { log("Ingresa un ID."); return; }
            try {
                system.removeStudent(Integer.parseInt(txt));
                refreshStudentTable();
                log("Estudiante " + txt + " eliminado.");
            } catch (NumberFormatException ex) { log("ID inválido."); }
        });
        findBtn.addActionListener(e -> {
            String txt = idField.getText().trim();
            if (txt.isEmpty()) { log("Ingresa un ID."); return; }
            try {
                system.searchStudent(Integer.parseInt(txt));
                
                int id = Integer.parseInt(txt);
                for (int r = 0; r < studentModel.getRowCount(); r++) {
                    if ((int) studentModel.getValueAt(r, 0) == id) {
                        studentTable.setRowSelectionInterval(r, r);
                        studentTable.scrollRectToVisible(studentTable.getCellRect(r, 0, true));
                        log("Estudiante encontrado en fila " + (r+1));
                        break;
                    }
                }
            } catch (NumberFormatException ex) { log("ID inválido."); }
        });

        bar.add(label("ID:"));      bar.add(idField);
        bar.add(label("Nombre:")); bar.add(nameField);
        bar.add(addBtn); bar.add(delBtn); bar.add(findBtn);

        p.add(bar, BorderLayout.SOUTH);
        refreshStudentTable();
        return p;
    }

    private void showRegisterStudentDialog() {
    JDialog dlg = new JDialog(this, "Registrar estudiante", true);
    dlg.setSize(460, 500);
    dlg.setLocationRelativeTo(this);
    dlg.getContentPane().setBackground(PANEL);
    dlg.setLayout(new BorderLayout(0, 0));

    JPanel form = new JPanel(new GridBagLayout());
    form.setBackground(PANEL);
    form.setBorder(new EmptyBorder(20, 24, 12, 24));
    GridBagConstraints gc = new GridBagConstraints();
    gc.insets  = new Insets(6, 4, 6, 4);
    gc.fill    = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;

    JTextField nameF = darkField(20, "");
    JTextField idF   = darkField(20, "");
    
    DinamicArray<Sport> dynamicSports = new DinamicArray<>();
    system.sports.inOrderToArray(system.sports.root, dynamicSports);
    int count = dynamicSports.getSize();

    
    String[] sportNames = new String[count];
    JCheckBox[] pracCb = new JCheckBox[count];
    JCheckBox[] intCb  = new JCheckBox[count];

    for (int i = 0; i < count; i++) {
        sportNames[i] = dynamicSports.access(i).name;
        pracCb[i] = darkCheckbox(sportNames[i]);
        intCb[i]  = darkCheckbox(sportNames[i]);
    }

    
    gc.gridx=0; gc.gridy=0; gc.gridwidth=1;
    form.add(label("Nombre:"), gc);
    gc.gridx=1; form.add(nameF, gc);

    gc.gridx=0; gc.gridy=1;
    form.add(label("ID:"), gc);
    gc.gridx=1; form.add(idF, gc);

    
    gc.gridx=0; gc.gridy=2; gc.gridwidth=2;
    form.add(label("Deportes que practica:"), gc);
    JPanel pracPanel = new JPanel(new GridLayout(0, 3, 6, 4));
    pracPanel.setBackground(PANEL);
    for (JCheckBox cb : pracCb) pracPanel.add(cb);
    gc.gridy=3; form.add(pracPanel, gc);

    
    gc.gridy=4; form.add(label("Deportes de interés:"), gc);
    JPanel intPanel = new JPanel(new GridLayout(0, 3, 6, 4));
    intPanel.setBackground(PANEL);
    for (JCheckBox cb : intCb) intPanel.add(cb);
    gc.gridy=5; form.add(intPanel, gc);

    JButton ok = accentButton("Registrar", ACCENT);
    ok.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idF.getText().trim());
            String name = nameF.getText().trim();
            if (name.isEmpty()) { log("Ingresa un nombre."); return; }

            system.practicedSportsBuffer.clear();
            system.interestSportsBuffer.clear();
            
            
            for (int i = 0; i < count; i++) {
                
                if (pracCb[i].isSelected()) system.practicedSportsBuffer.pushFront(sportNames[i]);
                if (intCb[i].isSelected())  system.interestSportsBuffer.pushFront(sportNames[i]);
            }
            system.createStudent(name, id, true);
            refreshStudentTable();
            refreshSportsTable();
            log("Estudiante " + name + " (ID " + id + ") registrado.");
            dlg.dispose();
        } catch (NumberFormatException ex) { log("ID debe ser un número."); }
    });

    gc.gridy=6; gc.insets = new Insets(16, 4, 4, 4);
    form.add(ok, gc);

    
    JScrollPane scrollPane = new JScrollPane(form);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    dlg.add(scrollPane, BorderLayout.CENTER);
    dlg.setVisible(true);
}
