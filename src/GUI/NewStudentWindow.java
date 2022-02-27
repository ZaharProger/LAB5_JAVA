package GUI;

import Logic.NewStudentWindowActionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.util.ArrayList;

public class NewStudentWindow extends JFrame {
    private static final Color elementsColor = new Color(255, 255, 255);
    private static final Color windowColor = new Color(238, 238, 238);
    private static final Font regularFont = new Font("Times New Roman", Font.PLAIN, 16);
    private static final Font headerFont = new Font("Times New Roman", Font.BOLD, 16);
    private NewStudentWindowActionManager actionManager;
    private static ArrayList<JTextField> fields = new ArrayList<>();

    public NewStudentWindow(String header, String dataBaseName){
        super(header);

        fields.clear();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);

        actionManager = new NewStudentWindowActionManager(0, this, dataBaseName);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(windowColor);

        JLabel nameLabel = new JLabel();
        nameLabel.setFont(regularFont);
        nameLabel.setBackground(windowColor);
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setText("Имя:");
        nameLabel.setBounds(50, 150, 50, 30);

        JTextField nameField = new JTextField();
        nameField.setBackground(elementsColor);
        nameField.setFont(regularFont);
        nameField.setBounds(50, 190, 200, 50);
        fields.add(nameField);

        JLabel surnameLabel = new JLabel();
        surnameLabel.setFont(regularFont);
        surnameLabel.setBackground(windowColor);
        surnameLabel.setForeground(Color.BLACK);
        surnameLabel.setText("Фамилия:");
        surnameLabel.setBounds(50, 350, 100, 30);

        JTextField surnameField = new JTextField();
        surnameField.setBackground(elementsColor);
        surnameField.setFont(regularFont);
        surnameField.setBounds(50, 390, 200, 50);
        fields.add(surnameField);

        JLabel birthdayLabel = new JLabel();
        birthdayLabel.setFont(regularFont);
        birthdayLabel.setBackground(windowColor);
        birthdayLabel.setForeground(Color.BLACK);
        birthdayLabel.setText("Дата рождения (дд.мм.гггг):");
        birthdayLabel.setBounds(50, 550, 200, 30);

        JTextField birthdayField = new JTextField();
        birthdayField.setBackground(elementsColor);
        birthdayField.setFont(regularFont);
        birthdayField.setBounds(50, 590, 200, 50);
        fields.add(birthdayField);

        JLabel testLabel = new JLabel();
        testLabel.setFont(regularFont);
        testLabel.setBackground(windowColor);
        testLabel.setForeground(Color.BLACK);
        testLabel.setText("Зачет (да, нет):");
        testLabel.setBounds(530, 150, 100, 30);

        JTextField testField = new JTextField();
        testField.setBackground(elementsColor);
        testField.setFont(regularFont);
        testField.setBounds(530, 190, 200, 50);
        fields.add(testField);

        JLabel diffTestLabel = new JLabel();
        diffTestLabel.setFont(regularFont);
        diffTestLabel.setBackground(windowColor);
        diffTestLabel.setForeground(Color.BLACK);
        diffTestLabel.setText("Дифф. зачет (2 - 5):");
        diffTestLabel.setBounds(530, 350, 200, 30);

        JTextField diffTestField = new JTextField();
        diffTestField.setBackground(elementsColor);
        diffTestField.setFont(regularFont);
        diffTestField.setBounds(530, 390, 200, 50);
        fields.add(diffTestField);

        JLabel examLabel = new JLabel();
        examLabel.setFont(regularFont);
        examLabel.setBackground(windowColor);
        examLabel.setForeground(Color.BLACK);
        examLabel.setText("Экзамен (2 - 5):");
        examLabel.setBounds(530, 550, 200, 30);

        JTextField examField = new JTextField();
        examField.setBackground(elementsColor);
        examField.setFont(regularFont);
        examField.setBounds(530, 590, 200, 50);
        fields.add(examField);

        JButton addButton = new JButton("Добавить");
        addButton.setFont(headerFont);
        addButton.setBackground(elementsColor);
        addButton.setForeground(Color.BLACK);
        addButton.setBounds(325, 700, 150, 50);

        addButton.addActionListener(actionManager);

        contentPane.add(nameLabel);
        contentPane.add(nameField);
        contentPane.add(surnameLabel);
        contentPane.add(surnameField);
        contentPane.add(birthdayLabel);
        contentPane.add(birthdayField);
        contentPane.add(testLabel);
        contentPane.add(testField);
        contentPane.add(diffTestLabel);
        contentPane.add(diffTestField);
        contentPane.add(examLabel);
        contentPane.add(examField);
        contentPane.add(addButton);

        setVisible(true);
    }

    public static String mergeEnteredData(){
        StringBuilder dataConnector = new StringBuilder();
        for (JTextField field : fields)
            dataConnector.append(field.getText()).append(" ");


        return dataConnector.toString();
    }
}
