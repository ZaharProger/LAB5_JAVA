package GUI;

import Logic.ActionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserWindow extends JFrame {
    private static final String[] groupMenuItems = new String[]{"Группа", "Создать/Открыть"};
    private static final String[] editMenuItems = new String[]{"Редактировать", "Добавить студента", "Удалить студента", "Напечатать список группы", "Провести аттестацию"};
    private static final String[] filterMenuItems = new String[]{"Фильтры", "ID", "Зачет", "Дифф. зачет", "Экзамен", "Аттестация", "Имя", "Фамилия", "Год рождения", "Сбросить"};
    private static final String[] hotKeys = new String[]{"O", "A", "R", "P", "T", "E"};
    private static final Color menuBarColor = new Color(231, 232, 232);
    private static final Color windowColor = new Color(175, 174, 174);
    private static final Font regularFont = new Font("Times New Roman", Font.PLAIN, 14);
    private static final Font headerFont = new Font("Times New Roman", Font.BOLD, 16);
    private static int menuItemIndex;
    private ActionManager actionManager;

    public UserWindow(String header){
        super(header);

        menuItemIndex = 0;
        actionManager = new ActionManager(32767, UserWindow.this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionManager.closeAll();
            }
        });

        JMenu groupMenu = createMenuItem(groupMenuItems);
        groupMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(hotKeys[0].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenu editMenu = createMenuItem(editMenuItems);
        editMenu.getItem(0).setAccelerator(KeyStroke.getKeyStroke(hotKeys[1].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(1).setAccelerator(KeyStroke.getKeyStroke(hotKeys[2].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(2).setAccelerator(KeyStroke.getKeyStroke(hotKeys[3].charAt(0), KeyEvent.CTRL_DOWN_MASK));
        editMenu.getItem(3).setAccelerator(KeyStroke.getKeyStroke(hotKeys[4].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenu filterMenu = createMenuItem(filterMenuItems);
        filterMenu.getItem(8).setAccelerator(KeyStroke.getKeyStroke(hotKeys[5].charAt(0), KeyEvent.CTRL_DOWN_MASK));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(menuBarColor);
        menuBar.add(groupMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);

        Container container = getContentPane();
        container.setBackground(windowColor);
        container.add(menuBar, BorderLayout.NORTH);

        setVisible(true);
    }

    private JMenu createMenuItem(String[] menuItems){
        JMenu menu = new JMenu(menuItems[0]);
        menu.setFont(regularFont);

        for (int i = 1; i < menuItems.length; ++i) {
            JMenuItem item = new JMenuItem(menuItems[i]);
            item.setBackground(menuBarColor);
            item.setFont(regularFont);
            item.addActionListener(new ActionManager(menuItemIndex++, UserWindow.this));

            menu.add(item);
        }

        return menu;
    }
}

