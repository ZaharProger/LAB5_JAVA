package Logic;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;

//БД
public class DataBase {
    private static final String CONNECTION_ADDRESS_MASK = "jdbc:sqlite:./";
    public static final String[] TABLE_HEADER = new String[]{"ID", "Имя", "Фамилия", "Дата рождения", "Зачет", "Дифф. зачет",
                                                                "Экзамен", "Аттестация"};
    private Connection connection;
    private ArrayList<Student> data;

    public DataBase(){
        data = new ArrayList<>();
        connection = null;
    }

    public ArrayList<Student> getData(){
        return data;
    }

    public Connection getConnection(){
        return connection;
    }

    //Подключение к БД
    public String connect(String name){
        String result;
        try{
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(CONNECTION_ADDRESS_MASK + name + ".db");
            create();
            result = "База данных успешно открыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }
        catch (NullPointerException exception){
            result = "Название группы не было введено пользователем!";
        }

        return result;
    }

    //Создание таблицы в БД
    private void create() throws SQLException{
        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS 'students' (" +
                    "'id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "'name' VARCHAR(50) NOT NULL, " +
                    "'surname' VARCHAR(50) NOT NULL, " +
                    "'birthday' VARCHAR(10) NOT NULL, " +
                    "'test_result' TINYINT NOT NULL, " +
                    "'diff_result' TINYINT NOT NULL, " +
                    "'exam_result' TINYINT NOT NULL);");
        }
    }

    //Извлечение всех записей из БД
    public void executeAllData(){
        data.clear();

        try (Statement statement = connection.createStatement()) {
            ResultSet executionResult = statement.executeQuery("SELECT * FROM students;");

            while (executionResult.next()){
                data.add(new Student(executionResult.getInt("id"), executionResult.getString("name"), executionResult.getString("surname"),
                        executionResult.getString("birthday"), executionResult.getBoolean("test_result"), executionResult.getByte("diff_result"),
                        executionResult.getByte("exam_result")));
            }

        }
        catch (SQLException  | NullPointerException exception){
            exception.printStackTrace();
        }
    }

    //Добавление в БД
    public void addData(Student student){
        try (Statement statement = connection.createStatement()){
            statement.execute(String.format("INSERT INTO students ('name', 'surname', 'birthday', 'test_result', 'diff_result', 'exam_result')" +
                            " VALUES ('%s', '%s', '%s', %d, %d, %d);", student.getName(), student.getSurname(), student.getBirthday(),
                    student.getTestResult()? 1 : 0, student.getDiffTestResult(), student.getExamResult()));
        }
        catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    //Удаление из БД
    public String removeData(int id){
        String result;
        try (Statement statement = connection.createStatement()){
            ResultSet executionResult = statement.executeQuery(String.format("SELECT id FROM students WHERE id = %d;", id));
            if (executionResult.next()){
                statement.execute(String.format("DELETE FROM students WHERE id = %d;", id));
                result = "Информация о студенте успешно удалена!";
            }
            else{
                result = "Информация о студенте не найдена!";
            }
        }
        catch(SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    //Закрытие БД
    public String close(){
        String result;
        try{
            data.clear();
            connection.close();
            result = "База данных успешно закрыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }
        catch (NullPointerException exception){
            result = "Ни одна группа не была открыта. Нет групп для закрытия!";
        }

        return result;
    }
}
