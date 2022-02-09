package Logic;

import GUI.UserWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionManager implements ActionListener {
    private int actionCode;
    private static DataBase dataBase = new DataBase();
    private Component userWindow;

    public ActionManager(int actionCode, Component userWindow){
        this.actionCode = actionCode;
        this.userWindow = userWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (actionCode) {
            case 0 -> {
                String name = JOptionPane.showInputDialog(userWindow,
                        new String[]{"Введите название группы\nЕсли группы не существует, она будет создана автоматически"}, "Создание/Открытие", JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.equals(""))
                    JOptionPane.showMessageDialog(userWindow, dataBase.connect(name), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void closeAll() {
        JOptionPane.showMessageDialog(userWindow, dataBase.close(), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
    }
}
