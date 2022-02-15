package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//Группа
public class Group {
    private ArrayList<Student> students;
    private ArrayList<Predicate<Student>> filters;

    public Group(){
        students = new ArrayList<>();
        filters = new ArrayList<>();
    }

    public void fillGroup(ArrayList<Student> students) {
        this.students.clear();
        this.students.addAll(students);
    }

    public ArrayList<Student> getStudents()
    {
        return students;
    }

    //Установка фильтров поиска по списку группы
    public String setSearchFilter(byte type, String key){
        String result;
        try{
            switch (type) {
                case 1 -> filters.add((student -> Integer.parseInt(key) == student.getId()));
                case 2 -> filters.add((student -> key.equalsIgnoreCase((student.getTestResult()) ? "Да" : "Нет")));
                case 3 -> filters.add((student -> Byte.parseByte(key) == student.getDiffTestResult()));
                case 4 -> filters.add((student -> Byte.parseByte(key) == student.getExamResult()));
                case 5 -> filters.add((student -> key.equalsIgnoreCase((student.hasAttestation()) ? "Да" : "Нет")));
                case 6 -> filters.add((student -> key.equalsIgnoreCase(student.getName())));
                case 7 -> filters.add((student -> key.equalsIgnoreCase(student.getSurname())));
                case 8 -> filters.add((student -> key.equals(student.getBirthday().substring(6))));
                case 0 -> filters.clear();
            }

            if (type == 1 || type == 3 || type == 4)
                Integer.parseInt(key);
        }
        catch (NumberFormatException exception){
            filters.remove(filters.size() - 1);
        }

        result = (!filters.isEmpty() && type != 0)? "Фильтр успешно применен!" : "Введите числовое значение!";

        return result;
    }

    //Вывод списка группы с учетом выбранных фильтров
    public List<Student> applyFilters(){
        Predicate<Student> totalFilter = filters.stream().reduce(student -> true, Predicate::and);

        return students.stream().filter(totalFilter).collect(Collectors.toList());
    }

    //Проведение аттестационных работ у группы
    public String examineStudents(){
        String result;
        if (!students.isEmpty()){
            Test test = new Test((byte)10);
            DiffTest diffTest = new DiffTest((byte)10);
            Exam exam = new Exam((byte)20);
            for (Student student : students){
                test.calculateResult(student, test.startWork());
                diffTest.calculateResult(student, diffTest.startWork());
                exam.calculateResult(student, exam.startWork());
            }
            result = "Аттестация группы успешно проведена!";
        }
        else{
            result = "Группа пуста!";
        }

        return result;
    }
}
