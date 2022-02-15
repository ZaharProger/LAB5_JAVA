package Logic;

import GUI.NewStudentWindow;
import GUI.UserWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserWindowActionManager implements ActionListener, Runnable {
    private int actionCode;
    private static DataBase dataBase = new DataBase();
    private Component userWindow;
    private static Group group = new Group();
    private static DefaultTableModel preparedGroup = new DefaultTableModel(0, 8);
    private static boolean hasHeader = false;
    private static String dataBaseName;

    public UserWindowActionManager(int actionCode, Component userWindow){
        this.actionCode = actionCode;
        this.userWindow = userWindow;

        if (!hasHeader){
            preparedGroup.addRow(DataBase.TABLE_HEADER);
            hasHeader = true;
        }
    }

    public static void updateGroup(Group updatedGroup){
        group = updatedGroup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (actionCode) {
            case 0 -> {
                dataBaseName = JOptionPane.showInputDialog(userWindow,
                        new String[]{"Введите название группы\nЕсли группы не существует, она будет создана автоматически"}, "Создание/Открытие", JOptionPane.PLAIN_MESSAGE);

                if (dataBaseName != null && !dataBaseName.equals("")){
                    JOptionPane.showMessageDialog(userWindow, dataBase.connect(dataBaseName), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                    run();
                }
            }
            case 1 -> {
                if (dataBase.getConnection() != null)
                    new NewStudentWindow("Добавление студента", dataBaseName);
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
            case 2 -> {
                if (dataBase.getConnection() != null){
                    String id = JOptionPane.showInputDialog(userWindow,
                            new String[]{"Введите ID студента"}, "Удаление", JOptionPane.PLAIN_MESSAGE);

                    if (id != null && !id.equals(""))
                        try{
                            JOptionPane.showMessageDialog(userWindow, dataBase.removeData(Integer.parseInt(id)), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                            run();
                        }
                        catch (NumberFormatException exception){
                            JOptionPane.showMessageDialog(userWindow, "Введите числовое значение!", "Внимание!", JOptionPane.ERROR_MESSAGE);
                        }
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
            case 3 -> {
                if (dataBase.getConnection() != null){
                    run();
                    JOptionPane.showMessageDialog(userWindow, "Аттестация успешно проведена!", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
            case 4, 5, 6, 7, 8, 9, 10, 11 -> {
                if (dataBase.getConnection() != null){
                    String filterKey = JOptionPane.showInputDialog(userWindow,
                            new String[]{"Введите ключ для фильтра"}, "Установка фильтра", JOptionPane.PLAIN_MESSAGE);

                    if (filterKey != null && !filterKey.equals("")){
                        JOptionPane.showMessageDialog(userWindow, group.setSearchFilter((byte)(actionCode - 3), filterKey), "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                        run();
                    }
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
            case 12 -> {
                if (dataBase.getConnection() != null){
                    run();
                    JOptionPane.showMessageDialog(userWindow, "Фильтры сброшены!", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Вы не открыли группу!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void closeAll() {
        JOptionPane.showMessageDialog(userWindow, dataBase.close(), "Внимание!", JOptionPane.WARNING_MESSAGE);
    }

    private void updatePreparedGroup(List<Student> students){
        while (preparedGroup.getRowCount() > 1)
            preparedGroup.removeRow(1);

        for (Student student : students)
            preparedGroup.addRow(student.toArrayString());
    }

    @Override
    public void run() {
        switch (actionCode) {
            case 0, 2 -> {
                dataBase.executeAllData();
                group.fillGroup(dataBase.getData());

                updatePreparedGroup(group.getStudents());
                UserWindow.updateTable(preparedGroup);
            }
            case 3 -> {
                group.examineStudents();

                updatePreparedGroup(group.getStudents());
                UserWindow.updateTable(preparedGroup);
            }
            case 4, 5, 6, 7, 8, 9, 10, 11 -> {
                System.out.println(actionCode);
                updatePreparedGroup(group.applyFilters());
                UserWindow.updateTable(preparedGroup);
            }
            case 12 -> {
                group.setSearchFilter((byte)0, null);

                updatePreparedGroup(group.applyFilters());
                UserWindow.updateTable(preparedGroup);
            }
        }
    }
}
