package GUI;

import Logic.ActionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserWindow extends JFrame {
    private static final String[] groupMenuItems = new String[]{"Группа", "Создать/Открыть", "Обновить"};
    private static final String[] editMenuItems = new String[]{"Редактировать", "Добавить студента", "Удалить студента", "Провести аттестацию"};
    private static final String[] filterMenuItems = new String[]{"Фильтры", "ID", "Зачет", "Дифф. зачет", "Экзамен", "Аттестация", "Имя", "Фамилия", "Год рождения", "Сбросить"};
    private static final String[] hotKeys = new String[]{"O", "U", "A", "R", "T", "E"};
    private static final Color elementsColor = new Color(231, 232, 232);
    private static final Color windowColor = new Color(175, 175, 175);
    private static final Font regularFont = new Font("Times New Roman", Font.PLAIN, 14);
    private static int menuItemIndex;
    private ActionManager actionManager;
    private static JTable groupTable;

    public UserWindow(String header){
        super(header);

        menuItemIndex = 0;
        actionManager = new ActionManager(32767, UserWindow.this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 800);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionManager.closeAll();
            }
        });

        JMenu groupMenu = createMenuItem(groupMenuItems);
        groupMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(hotKeys[0].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        groupMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(hotKeys[1].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenu editMenu = createMenuItem(editMenuItems);
        editMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(hotKeys[2].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(hotKeys[3].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(hotKeys[4].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenu filterMenu = createMenuItem(filterMenuItems);
        filterMenu.getItem(8).setAccelerator(KeyStroke.getKeyStroke(hotKeys[5].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(elementsColor);
        menuBar.add(groupMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);

        JScrollBar scrollbar = new JScrollBar();
        scrollbar.setBackground(elementsColor);
        scrollbar.setSize(50, 800);

        groupTable = new JTable(0, 0);
        groupTable.setFont(regularFont);
        groupTable.setOpaque(false);
        groupTable.setBackground(Color.WHITE);
        groupTable.setForeground(Color.BLACK);
        groupTable.setRowHeight(30);
        groupTable.setRowHeight(0, 50);
        groupTable.setRowSelectionAllowed(false);
        groupTable.setColumnSelectionAllowed(false);
        groupTable.setShowGrid(true);

        DefaultTableCellRenderer stringCellRenderer = (DefaultTableCellRenderer)groupTable.getDefaultRenderer(String.class);
        stringCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        stringCellRenderer.setVerticalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer numCellRenderer = (DefaultTableCellRenderer)groupTable.getDefaultRenderer(Integer.class);
        numCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        numCellRenderer.setVerticalAlignment(SwingConstants.CENTER);

        Container container = getContentPane();
        container.setBackground(windowColor);
        container.add(menuBar, BorderLayout.NORTH);
        container.add(groupTable, BorderLayout.CENTER);
        container.add(scrollbar, BorderLayout.EAST);

        setVisible(true);
    }

    private JMenu createMenuItem(String[] menuItems){
        JMenu menu = new JMenu(menuItems[0]);
        menu.setFont(regularFont);

        for (int i = 1; i < menuItems.length; ++i) {
            JMenuItem item = new JMenuItem(menuItems[i]);
            item.setBackground(elementsColor);
            item.setFont(regularFont);
            item.addActionListener(new ActionManager(menuItemIndex++, UserWindow.this));

            menu.add(item);
        }

        return menu;
    }

    public static void updateTable(DefaultTableModel updatedData){
        groupTable.setModel(updatedData);
    }
}

