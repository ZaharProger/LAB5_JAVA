package Logic;

import GUI.NewStudentWindow;
import GUI.UserWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewStudentWindowActionManager implements ActionListener, Runnable {
    private int actionCode;
    private DataBase dataBase;
    private NewStudentWindow userWindow;
    private Student student;
    private boolean isCorrect;

    public NewStudentWindowActionManager(int actionCode, NewStudentWindow userWindow, String dataBaseName){
        this.actionCode = actionCode;
        this.userWindow = userWindow;

        student = new Student();

        dataBase = new DataBase();
        dataBase.connect(dataBaseName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (actionCode){
            case 0 -> {
                run();
                if (isCorrect){
                    actionCode = 1;
                    run();
                    JOptionPane.showMessageDialog(userWindow, "Студент успешно добавлен!", "Внимание!", JOptionPane.INFORMATION_MESSAGE);
                    userWindow.dispose();
                }
                else
                    JOptionPane.showMessageDialog(userWindow, "Введите информацию согласно требованиям в скобках перед полями ввода!", "Внимание!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void run() {
        switch (actionCode){
            case 0 -> {
                String[] enteredData = NewStudentWindow.mergeEnteredData().trim().split(" ");

                if (Analyzer.analyzeNum(enteredData[4], 2, 5) && Analyzer.analyzeNum(enteredData[5], 2, 5) &&
                    Analyzer.analyzeDate(enteredData[2]) && (enteredData[3].equalsIgnoreCase("да") || enteredData[3].equalsIgnoreCase("нет"))){

                    isCorrect = true;

                    student.setName(enteredData[0]);
                    student.setSurname(enteredData[1]);
                    student.setBirthday(Analyzer.normalizeDate(enteredData[2]));
                    student.setTestResult(enteredData[3].equalsIgnoreCase("да"));
                    student.setDiffTestResult(Byte.parseByte(enteredData[4]));
                    student.setExamResult(Byte.parseByte(enteredData[5]));

                    dataBase.addData(student);
                }
                else
                    isCorrect = false;
            }
            case 1 ->{
                dataBase.executeAllData();

                Group group = new Group();
                group.fillGroup(dataBase.getData());

                DefaultTableModel preparedGroup = new DefaultTableModel(0, 8);
                preparedGroup.addRow(DataBase.TABLE_HEADER);

                ArrayList<Student> students = group.getStudents();

                for (Student student : students)
                    preparedGroup.addRow(student.toArrayString());

                UserWindowActionManager.updateGroup(group);
                UserWindow.updateTable(preparedGroup);

                actionCode = 0;
            }
        }
    }
}
