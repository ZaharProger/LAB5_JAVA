package Logic;

import GUI.UserWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ActionManager implements ActionListener, Runnable {
    private int actionCode;
    private static DataBase dataBase = new DataBase();
    private Component userWindow;
    private static Group group = new Group();

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
                if (name != null && !name.equals("")){
                    JOptionPane.showMessageDialog(userWindow, dataBase.connect(name), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                    run();
                }
            }
            case 1 -> {
                if (dataBase.getConnection() != null)
                    run();
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
            case 4 -> {
                if (dataBase.getConnection() != null){
                    run();
                    JOptionPane.showMessageDialog(userWindow, "Аттестация успешно проведена!", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void closeAll() {
        JOptionPane.showMessageDialog(userWindow, dataBase.close(), "Внимание!", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void run() {
        switch (actionCode) {
            case 0 -> {
                dataBase.executeAllData();

                group.fillGroup(dataBase.getData());

                DefaultTableModel preparedGroup = new DefaultTableModel(0, 8);
                preparedGroup.addRow(DataBase.TABLE_HEADER);

                ArrayList<Student> students = group.getStudents();
                for (Student student : students)
                    preparedGroup.addRow(student.toArrayString());

                UserWindow.updateTable(preparedGroup);
            }
            case 1 -> {
                DefaultTableModel preparedGroup = new DefaultTableModel(0, 8);
                preparedGroup.addRow(DataBase.TABLE_HEADER);

                List<Student> filteredStudents = group.applyFilters();

                for (Student student : filteredStudents)
                    preparedGroup.addRow(student.toArrayString());

                UserWindow.updateTable(preparedGroup);
            }
            case 4 -> group.examineStudents();
        }
    }
}
