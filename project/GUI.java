/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectdatastructure;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame {

    static final Color BG      = new Color(0x0D1B2A);
    static final Color PANEL   = new Color(0x1B2B3A);
    static final Color CARD    = new Color(0x1E3248);
    static final Color ACCENT  = new Color(0x148F77);
    static final Color ACCENT2 = new Color(0x2E86C1);
    static final Color AMBER   = new Color(0xD4AC0D);
    static final Color TEXT    = new Color(0xECF0F1);
    static final Color SUBTEXT = new Color(0x85929E);
    static final Color BORDER  = new Color(0x2C3E50);
    static final Color DANGER  = new Color(0xE74C3C);
    static final Color SUCCESS = new Color(0x27AE60);

    SportsSystem system;
    JPanel       contentPanel;
    CardLayout   cardLayout;

    public GUI() {
        system = new SportsSystem();
        seedData();

        setTitle("Red Deportiva UNAL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 700);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(),    BorderLayout.CENTER);

        setVisible(true);
    }

    private void seedData() {
        system.addSport("VOLLEYBALL");
        system.addSport("RUGBY");
        system.addSport("TAEKWONDO");
        system.addSport("SWIMMING");
        system.addSport("BASKETBALL");
        system.addSport("FOOTBALL");
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
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 8, 8);
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(0x1E3248));
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 8, 8);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(TEXT);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setBackground(new Color(0, 0, 0, 0));
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

        p.add(darkScroll(studentTable), BorderLayout.CENTER);

        JPanel bar = darkPanel();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JTextField idField   = darkField(8,  "ID");
        JTextField nameField = darkField(16, "Nombre");

        JButton addBtn  = accentButton("Registrar", ACCENT);
        JButton delBtn  = accentButton("Eliminar",  DANGER);
        JButton findBtn = accentButton("Buscar",    ACCENT2);

        addBtn.addActionListener(e -> showRegisterStudentDialog());

        delBtn.addActionListener(e -> {
            String txt = idField.getText().trim();
            if (txt.isEmpty() || txt.equals("ID")) return;
            try {
                system.removeStudent(Integer.parseInt(txt));
                refreshStudentTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        findBtn.addActionListener(e -> {
            String txt = idField.getText().trim();
            if (txt.isEmpty() || txt.equals("ID")) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa un ID para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txt);
                Student stu = system.students.get(id);
                if (stu == null) {
                    JOptionPane.showMessageDialog(this, "El estudiante con ID " + id + " no está registrado.", "No encontrado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder info = new StringBuilder();
                info.append("PERFIL DEL ESTUDIANTE\n");
                info.append("═════════════════════════════════\n");
                info.append("Nombre: ").append(stu.name).append("\n");
                info.append("ID Cédula: ").append(stu.ID).append("\n\n");

                info.append("Deportes que Practica:\n");
                SingleNode<SportEntry> currPractice = stu.practice.head;
                if (currPractice == null) {
                    info.append("  Ninguno asignado\n");
                } else {
                    while (currPractice != null) {
                        info.append(" - ").append(currPractice.value.sport.name).append("\n");
                        currPractice = currPractice.next;
                    }
                }

                info.append("\nDeportes de Interés:\n");
                SingleNode<Sport> currInterest = stu.interests.head;
                if (currInterest == null) {
                    info.append("  Ninguno asignado\n");
                } else {
                    while (currInterest != null) {
                        info.append(" - ").append(currInterest.value.name).append("\n");
                        currInterest = currInterest.next;
                    }
                }

                JOptionPane.showMessageDialog(this, info.toString(), "Info de " + stu.name, JOptionPane.INFORMATION_MESSAGE);

                for (int r = 0; r < studentModel.getRowCount(); r++) {
                    if ((int) studentModel.getValueAt(r, 0) == id) {
                        studentTable.setRowSelectionInterval(r, r);
                        studentTable.scrollRectToVisible(studentTable.getCellRect(r, 0, true));
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
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
        dlg.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL);
        form.setBorder(new EmptyBorder(20, 24, 12, 24));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets  = new Insets(6, 4, 6, 4);
        gc.fill    = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        JTextField nameF = darkField(20, "");
        JTextField idF   = darkField(20, "");

        DinamicArray<Sport> dynamicSports = system.sports.getAll();
        int count = dynamicSports.getSize();

        String[]   sportNames = new String[count];
        JCheckBox[] pracCb    = new JCheckBox[count];
        JCheckBox[] intCb     = new JCheckBox[count];

        for (int i = 0; i < count; i++) {
            sportNames[i] = dynamicSports.access(i).name;
            pracCb[i] = darkCheckbox(sportNames[i]);
            intCb[i] = darkCheckbox(sportNames[i]);
        }

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1;
        form.add(label("Nombre:"), gc);
        gc.gridx = 1; form.add(nameF, gc);

        gc.gridx = 0; gc.gridy = 1;
        form.add(label("ID:"), gc);
        gc.gridx = 1; form.add(idF, gc);

        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2;
        form.add(label("Deportes que practica:"), gc);
        JPanel pracPanel = new JPanel(new GridLayout(0, 3, 6, 4));
        pracPanel.setBackground(PANEL);
        for (JCheckBox cb : pracCb) pracPanel.add(cb);
        gc.gridy = 3; form.add(pracPanel, gc);

        gc.gridy = 4; form.add(label("Deportes de interés:"), gc);
        JPanel intPanel = new JPanel(new GridLayout(0, 3, 6, 4));
        intPanel.setBackground(PANEL);
        for (JCheckBox cb : intCb) intPanel.add(cb);
        gc.gridy = 5; form.add(intPanel, gc);

        JButton ok = accentButton("Registrar", ACCENT);
        ok.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idF.getText().trim());
                String name = nameF.getText().trim();

                system.practicedSportsBuffer.clear();
                system.interestSportsBuffer.clear();

                for (int i = 0; i < count; i++) {
                    if (pracCb[i].isSelected()) system.practicedSportsBuffer.pushFront(sportNames[i]);
                    if (intCb[i].isSelected())  system.interestSportsBuffer.pushFront(sportNames[i]);
                }

                system.createStudent(name, id);
                refreshStudentTable();
                refreshSportsTable();
                dlg.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dlg, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gc.gridy = 6; gc.insets = new Insets(16, 4, 4, 4);
        form.add(ok, gc);

        JScrollPane scrollPane = new JScrollPane(form);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        dlg.add(scrollPane, BorderLayout.CENTER);
        dlg.setVisible(true);
    }

    private void refreshStudentTable() {
        studentModel.setRowCount(0);
        DinamicArray<Student> all = system.students.getAll();
        for (int i = 0; i < all.getSize(); i++) {
            Student s = all.access(i);
            StringBuilder prac = new StringBuilder();
            SingleNode<SportEntry> pos = s.practice.head;
            while (pos != null) { prac.append(pos.value.sport.name).append("  "); pos = pos.next; }
            StringBuilder inte = new StringBuilder();
            SingleNode<Sport> pos2 = s.interests.head;
            while (pos2 != null) { inte.append(pos2.value.name).append("  "); pos2 = pos2.next; }
            studentModel.addRow(new Object[]{s.ID, s.name, prac.toString().trim(), inte.toString().trim()});
        }
    }

    DefaultTableModel sportModel;

    private JPanel buildSportsPanel() {
        JPanel p = darkPanel();
        p.setLayout(new BorderLayout(0, 16));
        p.setBorder(new EmptyBorder(24, 24, 0, 24));
        p.add(sectionHeader("Deportes", "Administra los deportes del sistema y sus practicantes"), BorderLayout.NORTH);

        String[] cols = {"Deporte", "# Practicantes", "Practicantes"};
        sportModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        p.add(darkScroll(styledTable(sportModel)), BorderLayout.CENTER);

        JPanel bar = darkPanel();
        bar.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JTextField spField = darkField(14, "Nombre del deporte");
        JButton addBtn = accentButton("+ Agregar",ACCENT);
        JButton delBtn = accentButton("✕ Eliminar",DANGER);
        JButton lstBtn = accentButton("↓ Ordenar por count",ACCENT2);

        addBtn.addActionListener(e -> {
            String s = spField.getText().trim().toUpperCase();
            if (s.isEmpty() || s.equals("Nombre del deporte")) return;
            system.addSport(s);
            refreshSportsTable();
        });

        delBtn.addActionListener(e -> {
            String s = spField.getText().trim();
            if (s.isEmpty()) return;
            system.removeSport(s);
            refreshSportsTable();
            refreshStudentTable();
        });

        lstBtn.addActionListener(e -> {
            system.printSportsByCount();
            refreshSportsTable();
        });

        bar.add(label("Deporte:")); bar.add(spField);
        bar.add(addBtn); bar.add(delBtn); bar.add(lstBtn);
        p.add(bar, BorderLayout.SOUTH);

        refreshSportsTable();
        return p;
    }

    private void refreshSportsTable() {
        sportModel.setRowCount(0);
        DinamicArray<Sport> arr = system.sports.getAll();
        int n = arr.getSize();
        for (int i = 1; i < n; i++) {
            Sport key = arr.access(i);
            int j = i - 1;
            while (j >= 0 && arr.access(j).amountStu < key.amountStu) {
                arr.set(j + 1, arr.access(j));
                j--;
            }
            arr.set(j + 1, key);
        }
        for (int i = 0; i < n; i++) {
            Sport s = arr.access(i);
            StringBuilder prac = new StringBuilder();
            DobleNode<Student> pos = s.practicers.head;
            while (pos != null) { prac.append(pos.value.name).append("  "); pos = pos.next; }
            sportModel.addRow(new Object[]{s.name, s.amountStu, prac.toString().trim()});
        }
    }

    private JPanel buildConnectivityPanel() {
        JPanel p = darkPanel();
        p.setLayout(new BorderLayout(0, 16));
        p.setBorder(new EmptyBorder(24, 24, 0, 24));
        p.add(sectionHeader("Conectividad", "¿Está un estudiante conectado directa o indirectamente con un deporte?"), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);

        JTextField idField = darkField(10, "ID del estudiante");
        JTextField sportField = darkField(14, "Deporte objetivo");
        JButton searchBtn = accentButton("Verificar conexión →", ACCENT);

        JPanel resultCard = new JPanel(new BorderLayout(0, 8));
        resultCard.setBackground(CARD);
        resultCard.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(20, 24, 20, 24)
        ));

        JLabel resultIcon = new JLabel("?", SwingConstants.CENTER);
        resultIcon.setFont(new Font("SansSerif", Font.BOLD, 48));
        resultIcon.setForeground(SUBTEXT);

        JLabel resultLabel = new JLabel("Ingresa un ID y un deporte para comenzar", SwingConstants.CENTER);
        resultLabel.setForeground(SUBTEXT);
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel resultDetail = new JLabel(" ", SwingConstants.CENTER);
        resultDetail.setForeground(ACCENT);
        resultDetail.setFont(new Font("SansSerif", Font.BOLD, 13));

        resultCard.add(resultIcon,BorderLayout.NORTH);
        resultCard.add(resultLabel,BorderLayout.CENTER);
        resultCard.add(resultDetail,BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String sport = sportField.getText().trim().toUpperCase();
                if (sport.isEmpty()) return;

                boolean connected = system.isConnected(id, sport);

                if (connected) {
                    resultIcon.setForeground(SUCCESS);
                    resultIcon.setText("✓");
                    resultLabel.setForeground(TEXT);
                    resultLabel.setText("¡Conexión encontrada!");
                    resultDetail.setText("Existe cadena entre el estudiante y " + sport);
                    resultCard.setBackground(new Color(0x1A3A2A));
                } else {
                    resultIcon.setForeground(DANGER);
                    resultIcon.setText("✗");
                    resultLabel.setForeground(TEXT);
                    resultLabel.setText("Sin conexión");
                    resultDetail.setText("No existe cadena entre el estudiante y " + sport);
                    resultCard.setBackground(new Color(0x3A1A1A));
                }
                resultCard.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1; gc.anchor = GridBagConstraints.WEST;
        center.add(label("ID estudiante:"), gc);
        gc.gridx = 1; center.add(idField, gc);
        gc.gridx = 0; gc.gridy = 1;
        center.add(label("Deporte:"), gc);
        gc.gridx = 1; center.add(sportField, gc);
        gc.gridx = 0; gc.gridy = 2; gc.gridwidth = 2; gc.fill = GridBagConstraints.HORIZONTAL;
        center.add(searchBtn, gc);
        gc.gridy = 3; gc.fill = GridBagConstraints.BOTH; gc.weightx = 1; gc.weighty = 1;
        center.add(resultCard, gc);

        p.add(center, BorderLayout.CENTER);
        return p;
    }

    GraphPanel graphPanel;

    private JPanel buildCommunitiesPanel() {
        JPanel p = darkPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(24, 24, 0, 24));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BG);
        top.add(sectionHeader("Comunidades deportivas", "Visualización del grafo de conexiones entre estudiantes"), BorderLayout.CENTER);
        JButton refresh = accentButton("↺ Actualizar grafo", ACCENT2);
        refresh.addActionListener(e -> refreshCommunities());
        top.add(refresh, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);

        graphPanel = new GraphPanel();
        p.add(graphPanel, BorderLayout.CENTER);
        return p;
    }

    private void refreshCommunities() {
        DinamicArray<Student> all = system.students.getAll();
        graphPanel.setStudents(all, system);
    }

    static class GraphNode {
        Student student;
        double x, y, vx, vy;
        int community;
        Color color;

        GraphNode(Student s, double x, double y, int c) {
            student = s; this.x = x; this.y = y; community = c;
        }
    }
    
    class GraphPanel extends JPanel {
    java.util.List<GraphNode> nodes = new ArrayList<>();
    java.util.List<int[]> edges = new ArrayList<>();
    java.util.List<Color> communityColors = new ArrayList<>();
    GraphNode dragging = null;
    Point lastMouse;

    GraphPanel() {
        setBackground(new Color(0x0A1520));
        setBorder(new LineBorder(GUI.BORDER, 1)); 
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (GraphNode n : nodes) {
                    double dx = e.getX() - n.x, dy = e.getY() - n.y;
                    if (Math.sqrt(dx * dx + dy * dy) < 22) { 
                        dragging = n; 
                        break; 
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) { 
                dragging = null; 
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging != null) { 
                    dragging.x = e.getX(); 
                    dragging.y = e.getY(); 
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) { 
                lastMouse = e.getPoint(); 
                repaint();
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    void setStudents(DinamicArray<Student> all, SportsSystem sys) {
        nodes.clear(); edges.clear(); communityColors.clear();
        if (all.getSize() == 0) { repaint(); return; }

        java.util.Map<Integer, Integer> idToCommunity = new java.util.HashMap<>();
        boolean[] visited = new boolean[all.getSize()];
        int commIdx = 0;

        Color[] palette = {
            new Color(0x148F77), new Color(0x2E86C1), new Color(0xD4AC0D),
            new Color(0xE74C3C), new Color(0x8E44AD), new Color(0xE67E22),
            new Color(0x1ABC9C), new Color(0x3498DB)
        };

        for (int i = 0; i < all.getSize(); i++) {
            if (!visited[i]) {
                Color c = palette[commIdx % palette.length];
                communityColors.add(c);
                Queue<Student> q = new Queue<>();
                q.enqueue(all.access(i));
                visited[i] = true;
                idToCommunity.put(all.access(i).ID, commIdx);
                while (!q.isEmpty()) {
                    Student cur = q.dequeue();
                    SingleNode<SportEntry> sp = cur.practice.head;
                    while (sp != null) {
                        DobleNode<Student> nb = sp.value.sport.practicers.head;
                        while (nb != null) {
                            Student next = nb.value;
                            for (int k = 0; k < all.getSize(); k++) {
                                if (all.access(k) == next && !visited[k]) {
                                    visited[k] = true;
                                    idToCommunity.put(next.ID, commIdx);
                                    q.enqueue(next);
                                    break;
                                }
                            }
                            nb = nb.next;
                        }
                        sp = sp.next;
                    }
                }
                commIdx++;
            }
        }

        int W = Math.max(getWidth(), 600), H = Math.max(getHeight(), 400);
        Random rng = new Random(42);
        
        int totalCommunities = communityColors.size();

        for (int i = 0; i < all.getSize(); i++) {
            Student s = all.access(i);
            int c = idToCommunity.getOrDefault(s.ID, 0);
            
            double spacingX = W / (double) (totalCommunities + 1);
            double cx = spacingX * (c + 1);
            double cy = H * 0.5;
            
            double angle  = rng.nextDouble() * 2 * Math.PI;
            double radius = rng.nextDouble() * 70;
            
            GraphNode gn = new GraphNode(s, cx + radius * Math.cos(angle), cy + radius * Math.sin(angle), c);
            gn.color = palette[c % palette.length];
            nodes.add(gn);
        }

        for (int a = 0; a < nodes.size(); a++) {
            for (int b = a + 1; b < nodes.size(); b++) {
                if (sharesSport(nodes.get(a).student, nodes.get(b).student)) {
                    edges.add(new int[]{a, b});
                }
            }
        }

        
        repaint();
    }

    boolean sharesSport(Student a, Student b) {
        SingleNode<SportEntry> pa = a.practice.head;
        while (pa != null) {
            SingleNode<SportEntry> pb = b.practice.head;
            while (pb != null) {
                if (pa.value != null && pb.value != null && pa.value.sport == pb.value.sport) return true;
                pb = pb.next;
            }
            pa = pa.next;
        }
        return false;
    }


    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (nodes.isEmpty()) {
            g2.setColor(GUI.SUBTEXT);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            String msg = "Registra estudiantes para ver el grafo de comunidades";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
            return;
        }

        g2.setStroke(new BasicStroke(1.2f));
        for (int[] e : edges) {
            GraphNode a = nodes.get(e[0]), b = nodes.get(e[1]);
            Color base = a.color;
            g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 60));
            g2.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
        }

        for (GraphNode n : nodes) {
            
            for (int r = 18; r >= 12; r -= 3) {
                g2.setColor(new Color(n.color.getRed(), n.color.getGreen(), n.color.getBlue(), r == 18 ? 20 : 10));
                g2.fillOval((int) n.x - r, (int) n.y - r, r * 2, r * 2);
            }
            
            g2.setColor(n.color);
            g2.fillOval((int) n.x - 14, (int) n.y - 14, 28, 28);
            g2.setColor(new Color(255, 255, 255, 60));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval((int) n.x - 14, (int) n.y - 14, 28, 28);
            g2.setColor(GUI.TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            String initials = (n.student.name != null && !n.student.name.isEmpty()) ? String.valueOf(n.student.name.charAt(0)) : "?";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(initials, (int) n.x - fm.stringWidth(initials) / 2, (int) n.y + fm.getAscent() / 2 - 1);
            if (lastMouse != null) {
                double dx = lastMouse.x - n.x, dy = lastMouse.y - n.y;
                if (Math.sqrt(dx * dx + dy * dy) < 18) {
                    drawTooltip(g2, n, (int) n.x, (int) n.y - 30);
                }
            }
        }

        drawLegend(g2);
    }

    void drawTooltip(Graphics2D g2, GraphNode n, int x, int y) {
        StringBuilder sb = new StringBuilder();
        sb.append(n.student.name).append(" (").append(n.student.ID).append(")");
        SingleNode<SportEntry> pos = n.student.practice.head;
        if (pos != null) {
            sb.append(" · ");
            while (pos != null) { 
                sb.append(pos.value.sport.name).append(" "); 
                pos = pos.next; 
            }
        }
        String txt = sb.toString().trim();
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(txt) + 16, th = 22;
        int tx = Math.max(4, Math.min(getWidth() - tw - 4, x - tw / 2));
        int ty = Math.max(4, y - th);
        
        g2.setColor(new Color(0x0D1B2A));
        g2.fillRoundRect(tx, ty, tw, th, 8, 8);
        g2.setColor(GUI.BORDER);
        g2.drawRoundRect(tx, ty, tw, th, 8, 8);
        g2.setColor(GUI.TEXT);
        g2.drawString(txt, tx + 8, ty + 15);
    }

    void drawLegend(Graphics2D g2) {
        if (communityColors.isEmpty()) return;
        int x = 12, y = 12;
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.setColor(GUI.SUBTEXT);
        g2.drawString("Comunidades", x, y + 12);
        y += 20;
        for (int i = 0; i < communityColors.size(); i++) {
            g2.setColor(communityColors.get(i));
            g2.fillOval(x, y, 10, 10);
            g2.setColor(GUI.TEXT);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.drawString("C" + (i + 1), x + 14, y + 9);
            y += 18;
        }
    }
}

    private JPanel buildPerformancePanel() {
        JPanel root = darkPanel();
        root.setLayout(new BorderLayout(0, 16));
        root.setBorder(new EmptyBorder(24, 24, 16, 24));
        root.add(sectionHeader("Rendimiento y Diagnóstico",
            "Tiempos teóricos por estructura de datos · Prueba de estrés sobre el sistema real"), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setBackground(BG);
        split.setDividerSize(6);
        split.setResizeWeight(0.58);
        split.setBorder(null);

        split.setTopComponent(buildChartSection());
        split.setBottomComponent(buildDiagnosticsSection());

        root.add(split, BorderLayout.CENTER);
        return root;
    }

    private JPanel buildChartSection() {
        JPanel p = darkPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("  Complejidad empírica por operación y tamaño de entrada", SwingConstants.LEFT) {{
            setForeground(SUBTEXT);
            setFont(new Font("SansSerif", Font.PLAIN, 12));
            setBorder(new EmptyBorder(0, 0, 4, 0));
        }}, BorderLayout.NORTH);
        p.add(new ChartPanel(), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildDiagnosticsSection() {
        DiagnosticsFunctions diagnostics = new DiagnosticsFunctions();
        diagnostics.initialize();

        JPanel p = darkPanel();
        p.setLayout(new BorderLayout(0, 10));
        p.setBorder(new EmptyBorder(8, 0, 0, 0));

        JPanel topRow = darkPanel();
        //topRow.setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Prueba sobre el sistema real (100 operaciones). Note que correr el diagnostico resetea el sistema para la prueba.");
        lbl.setForeground(SUBTEXT);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        topRow.add(lbl, BorderLayout.WEST);

        JButton run100Btn = accentButton("Prueba (100)", ACCENT);
        JButton run1000Btn = accentButton("Prueba (1K)", ACCENT2);
        JButton run10000Btn = accentButton("Prueba (10K)", DANGER);
        //topRow.setLayout(new BorderLayout());
        topRow.add(run100Btn);
        topRow.add(run1000Btn);
        topRow.add(run10000Btn);
        
        p.add(topRow, BorderLayout.NORTH);

        DiagnosticsBarPanel barPanel = new DiagnosticsBarPanel();
        p.add(barPanel, BorderLayout.CENTER);

        JTextArea console = new JTextArea(3, 40);
        console.setBackground(PANEL);
        console.setForeground(SUCCESS);
        console.setFont(new Font("Monospaced", Font.PLAIN, 11));
        console.setEditable(false);
        JScrollPane scrollConsole = new JScrollPane(console);
        scrollConsole.setBorder(new LineBorder(BORDER, 1));
        p.add(scrollConsole, BorderLayout.SOUTH);

        run100Btn.addActionListener(e -> {
            system = new SportsSystem();
            seedData();
            ExecuteTest(run100Btn, 100, console, diagnostics, barPanel);
        });
        
        run1000Btn.addActionListener(e -> {
            system = new SportsSystem();
            seedData();
            ExecuteTest(run100Btn, 1000, console, diagnostics, barPanel);
        });
        
        run10000Btn.addActionListener(e -> {
            
            system = new SportsSystem();
            seedData();
            ExecuteTest(run100Btn, 10000, console, diagnostics, barPanel);
        });

        return p;
    }
    
    public void ExecuteTest(JButton runBtn, int testCount, JTextArea console, DiagnosticsFunctions diagnostics, DiagnosticsBarPanel barPanel) 
    {
        runBtn.setEnabled(false);
            console.append("[" + new java.util.Date() + "] Iniciando prueba ...\n");

            SwingWorker<long[], Void> worker = new SwingWorker<>() {
                @Override
                protected long[] doInBackground() {
                    
                    diagnostics.prefill(system, testCount);
                    
                    long ins = diagnostics.testInsertions(system);
                    long del = diagnostics.testDeletions(system);
                    long srch = diagnostics.testSearches(system);
                    long conn = diagnostics.testConnectivity(system);
                    long comm = diagnostics.testCommunities(system);
                    
                    return new long[]{ins, del, srch, conn, comm};
                }

                @Override
                protected void done() {
                    try {
                        long[] results    = get();
                        long tInsertionNs = results[0];
                        long tDeletionNs  = results[1];
                        long tSearchNs = results[2];
                        long tConnNs = results[3];
                        long tCommNs = results[4];

                        double msInsert = tInsertionNs / 1_000_000.0;
                        double msDelete = tDeletionNs  / 1_000_000.0;
                        long avgInsert  = tInsertionNs / 100;
                        long avgDelete  = tDeletionNs  / 100;

                        barPanel.setResults(tInsertionNs, tDeletionNs, tSearchNs, tConnNs, tCommNs);

                        console.append(String.format(" > Inserciones: %.3f ms total  |  %d ns/op en promedio\n", msInsert, avgInsert));
                        console.append(String.format(" > Eliminaciones: %.3f ms total  |  %d ns/op en promedio\n", msDelete, avgDelete));
                        console.append(" Prueba finalizada.\n\n");
                    } catch (Exception ex) {
                        console.append(" Error al ejecutar la prueba.\n");
                    }
                    runBtn.setEnabled(true);
                }
            };
            worker.execute();
    }

    class DiagnosticsBarPanel extends JPanel {
        long insertNs = -1;
        long deleteNs = -1;
        long searchNs = -1;
        long connectNs = -1;
        long commNs = -1;

        DiagnosticsBarPanel() {
            setBackground(BG);
            setPreferredSize(new Dimension(0, 120));
        }

        void setResults(long ins, long del, long srch, long conn, long comm) {
            this.insertNs = ins;
            this.deleteNs = del;
            this.searchNs = srch;
            this.connectNs = conn;
            this.commNs = comm;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int W = getWidth(), H = getHeight();

            if (insertNs < 0) {
                g2.setColor(SUBTEXT);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                String msg = "Ejecuta la prueba para ver los resultados aquí";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(msg, (W - fm.stringWidth(msg)) / 2, H / 2);
                return;
            }

            long maxVal = Math.max(insertNs, deleteNs);
            if (maxVal == 0) maxVal = 1;

            int ml = 100, mr = 20, mt = 12, mb = 54;
            int pw = W - ml - mr;
            int ph = H - mt - mb;

            g2.setColor(SUBTEXT);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            for (int i = 0; i <= 4; i++) {
                int y = mt + ph - (ph * i / 4);
                g2.setColor(new Color(0x1E3248));
                g2.setStroke(new BasicStroke(0.5f));
                g2.drawLine(ml, y, ml + pw, y);
                g2.setColor(SUBTEXT);
                g2.drawString(fmtNs(maxVal * i / 4), 4, y + 4);
            }

            String[] labels = {"Inserción (100 ops)", "Eliminación (100 ops)", "Búsqueda (100 ops)", "Conectividad (100 ops)", "Comunidad (1 op)"};
            long[]   values = {insertNs, deleteNs, searchNs, connectNs, commNs};
            Color[]  colors = {ACCENT2, DANGER, ACCENT, AMBER, TEXT};

            int barW  = pw / 15;
            int gap   = (pw - barW * 2) / 8;

            for (int i = 0; i < 5; i++) {
                int bh  = (int) ((double) values[i] / maxVal * ph);
                int bx  = ml + gap + i * (barW + gap);
                int by  = mt + ph - bh;

                GradientPaint gp = new GradientPaint(bx, by, colors[i], bx, mt + ph,
                    new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), 50));
                g2.setPaint(gp);
                g2.fillRoundRect(bx, by, barW, bh, 6, 6);
                g2.setColor(colors[i]);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(bx, by, barW, bh, 6, 6);

                g2.setColor(TEXT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 11));
                String valStr = fmtNs(values[i]);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(valStr, bx + (barW - fm.stringWidth(valStr)) / 2, by - 5);

                g2.setColor(TEXT);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                String lbl = labels[i];
                g2.drawString(lbl, bx + (barW - fm.stringWidth(lbl)) / 2, mt + ph + 18);

                String avgStr = "avg: " + fmtNs(values[i] / 100) + "/op";
                g2.setColor(SUBTEXT);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                FontMetrics fm2 = g2.getFontMetrics();
                g2.drawString(avgStr, bx + (barW - fm2.stringWidth(avgStr)) / 2, mt + ph + 30);
            }

            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(ml, mt, ml, mt + ph);
            g2.drawLine(ml, mt + ph, ml + pw, mt + ph);
        }

        String fmtNs(long ns) {
            if (ns < 1_000)           return ns + " ns";
            if (ns < 1_000_000)       return String.format("%.1f µs", ns / 1_000.0);
            if (ns < 1_000_000_000L)  return String.format("%.1f ms", ns / 1_000_000.0);
            return String.format("%.2f s", ns / 1_000_000_000.0);
        }
    }

    class ChartPanel extends JPanel {
        final String[] ops   = {"Insertar", "Buscar ID", "Eliminar", "Listar sport", "BFS", "Comunidades"};
        final int[]    sizes = {100, 1000, 10000, 100000};
        final long[][] data  = {
            {  35853,   10207,    10628,    18206},
            {  53995,  160706,   102609,    85427},
            {  21334,   45928,    81119,   541971},
            {    588,  223774,  1555517, 16229177},
            { 111133,  318660,  2351422,  8824663},
            { 218643, 2625430, 10318694,104432678},
        };
        final String[] bigO   = {"O(1)", "O(1)", "O(k)", "O(m)", "O(V+E)", "O(V+E)"};
        final Color[]  colors = {ACCENT, ACCENT2, DANGER, AMBER, new Color(0x8E44AD), new Color(0xE67E22)};

        int selectedOp = 0;

        ChartPanel() {
            setBackground(BG);
            setLayout(new BorderLayout());

            JPanel sel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            sel.setBackground(BG);
            ButtonGroup bg = new ButtonGroup();
            for (int i = 0; i < ops.length; i++) {
                final int idx = i;
                JToggleButton btn = new JToggleButton(ops[i] + "  " + bigO[i]);
                btn.setForeground(colors[i]);
                btn.setBackground(CARD);
                btn.setFont(new Font("SansSerif", Font.PLAIN, 11));
                btn.setBorder(new CompoundBorder(
                    new LineBorder(colors[i], 1, true),
                    new EmptyBorder(4, 10, 4, 10)));
                btn.setFocusPainted(false);
                btn.addActionListener(e -> { selectedOp = idx; repaint(); });
                bg.add(btn);
                sel.add(btn);
                if (i == 0) btn.setSelected(true);
            }
            add(sel, BorderLayout.NORTH);

            JPanel draw = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    drawChart((Graphics2D) g);
                }
            };
            draw.setBackground(BG);
            add(draw, BorderLayout.CENTER);
        }

        void drawChart(Graphics2D g2) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int W = getWidth(), H = getHeight();
            int ml = 80, mr = 40, mt = 30, mb = 50;
            int pw = W - ml - mr;
            int ph = H - mt - mb;

            if (pw <= 0 || ph <= 0) return;

            long[] row = data[selectedOp];
            long maxVal = 1;
            for (long v : row) if (v > maxVal) maxVal = v;

            g2.setColor(new Color(0x1E3248));
            g2.setStroke(new BasicStroke(0.5f));
            for (int i = 0; i <= 5; i++) {
                int y = mt + ph - (ph * i / 5);
                g2.drawLine(ml, y, ml + pw, y);
                g2.setColor(SUBTEXT);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                g2.drawString(fmtNs(maxVal * i / 5), 4, y + 4);
                g2.setColor(new Color(0x1E3248));
            }

            Color barColor = colors[selectedOp];
            int barW = pw / (sizes.length * 2);
            int[] barX = new int[sizes.length];
            int[] barY = new int[sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                int bh = (int) ((double) row[i] / maxVal * ph);
                int bx = ml + (pw / sizes.length) * i + (pw / sizes.length - barW) / 2;
                int by = mt + ph - bh;
                barX[i] = bx + barW / 2;
                barY[i] = by;

                GradientPaint gp = new GradientPaint(bx, by, barColor, bx, mt + ph,
                    new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 60));
                g2.setPaint(gp);
                g2.fillRoundRect(bx, by, barW, bh, 4, 4);
                g2.setColor(barColor);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(bx, by, barW, bh, 4, 4);

                g2.setColor(TEXT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 9));
                String val = fmtNs(row[i]);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(val, bx + (barW - fm.stringWidth(val)) / 2, by - 4);

                g2.setColor(SUBTEXT);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
                String xl = "n=10^" + (int) (Math.log10(sizes[i]) + 0.5);
                g2.drawString(xl, bx + (barW - fm.stringWidth(xl)) / 2, mt + ph + 16);
            }

            g2.setColor(new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), 180));
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int i = 0; i < sizes.length - 1; i++) {
                g2.drawLine(barX[i], barY[i], barX[i + 1], barY[i + 1]);
            }

            g2.setColor(barColor);
            for (int i = 0; i < sizes.length; i++) {
                g2.fillOval(barX[i] - 4, barY[i] - 4, 8, 8);
            }

            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1f));
            g2.drawLine(ml, mt, ml, mt + ph);
            g2.drawLine(ml, mt + ph, ml + pw, mt + ph);

            g2.setColor(TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(ops[selectedOp] + " — " + bigO[selectedOp] + " — tiempo promedio por operación", ml, mt - 8);
        }

        String fmtNs(long ns) {
            if (ns < 1_000)           return ns + "ns";
            if (ns < 1_000_000)       return String.format("%.1fµs", ns / 1_000.0);
            if (ns < 1_000_000_000L)  return String.format("%.1fms", ns / 1_000_000.0);
            return String.format("%.2fs", ns / 1_000_000_000.0);
        }
    }

    JPanel darkPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG);
        return p;
    }

    JPanel sectionHeader(String title, String subtitle) {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(BG);
        h.setBorder(new EmptyBorder(0, 0, 16, 0));
        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 20));
        t.setForeground(TEXT);
        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.PLAIN, 12));
        s.setForeground(SUBTEXT);
        h.add(t, BorderLayout.NORTH);
        h.add(s, BorderLayout.SOUTH);
        return h;
    }

    JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setBackground(CARD);
        t.setForeground(TEXT);
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));
        t.setRowHeight(32);
        t.setGridColor(BORDER);
        t.setSelectionBackground(new Color(0x148F77, false));
        t.setSelectionForeground(TEXT);
        t.setShowVerticalLines(false);
        t.getTableHeader().setBackground(PANEL);
        t.getTableHeader().setForeground(SUBTEXT);
        t.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        t.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER));
        t.setIntercellSpacing(new Dimension(12, 0));
        return t;
    }

    JScrollPane darkScroll(JComponent c) {
        JScrollPane s = new JScrollPane(c);
        s.setBackground(CARD);
        s.getViewport().setBackground(CARD);
        s.setBorder(new LineBorder(BORDER, 1, true));
        return s;
    }

    JButton accentButton(String text, Color color) {
        JButton b = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed() ? color.darker()
                    : getModel().isRollover() ? color.brighter() : color;
                g2.setColor(c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        return b;
    }

    JTextField darkField(int cols, String placeholder) {
        JTextField f = new JTextField(cols);
        f.setBackground(CARD);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(new CompoundBorder(
            new LineBorder(BORDER, 1, true),
            new EmptyBorder(6, 10, 6, 10)));
        if (!placeholder.isEmpty()) f.setText(placeholder);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { if (f.getText().equals(placeholder)) f.setText(""); }
            public void focusLost(FocusEvent e)   { if (f.getText().isEmpty()) f.setText(placeholder); }
        });
        return f;
    }

    JCheckBox darkCheckbox(String text) {
        JCheckBox cb = new JCheckBox(text);
        cb.setBackground(PANEL);
        cb.setForeground(TEXT);
        cb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cb.setFocusPainted(false);
        return cb;
    }

    JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(SUBTEXT);
        l.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return l;
    }

    JSeparator separator() {
        JSeparator s = new JSeparator();
        s.setForeground(BORDER);
        s.setBackground(BORDER);
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        s.setAlignmentX(Component.LEFT_ALIGNMENT);
        return s;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(GUI::new);
    }
}
