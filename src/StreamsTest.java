import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamsTest {
    public record Employee(int id, String name, double salary, String Location, String department) {}
    public static void main(String[] args) {
        Employee e1 = new Employee(1, "John Doe", 10.0, "New York", "IT");
        Employee e2 = new Employee(2, "Dev Musk", 15.0, "New York", "HR");
        Employee e3 = new Employee(3, "Mili Doe", 20.0, "New Jersey","IT");

        List<Employee> employees = Arrays.asList(e1, e2, e3);
        Map<String, List<Employee>> emp = employees.stream().collect(Collectors.groupingBy(e -> e.Location));
        Map<String, Map<String, List<Employee>>> dept = employees.stream().collect(Collectors.groupingBy(e -> e.department
        , Collectors.groupingBy(e -> e.Location)));

        dept.forEach((k, v) -> {
            System.out.println(k +" = "+v);
        });

    }
}
