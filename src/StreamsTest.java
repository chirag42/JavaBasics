import java.util.Arrays;
import java.util.Comparator;
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

        // Get names of all employees in uppercase
        System.out.println("Question: Get names of all employees in uppercase - ");
        List<String> upperNames = employees.stream().map(e -> e.name.toUpperCase()).toList();
        System.out.println(upperNames);
        System.out.println("----------------------------");

        // Group employees by location
        System.out.println("Question: Group employees by location - ");
        Map<String, List<Employee>> emp = employees.stream().collect(Collectors.groupingBy(e -> e.Location));
        System.out.println(emp);
        System.out.println("----------------------------");

        //Count employees in each department
        System.out.println("Question: Count employees in each department - ");
        Map<String, Long> countByDept = employees.stream().collect(Collectors.groupingBy(e -> e.department, Collectors.counting()));
        System.out.println(countByDept);
        System.out.println("----------------------------");

        //Find the average salary of all employees
        System.out.println("Find the average salary of all employees - ");
        Double averageSalary = employees.stream().collect(Collectors.averagingDouble(e -> e.salary));
        System.out.println(averageSalary);
        System.out.println("----------------------------");

        //Sort employees by salary in descending order
        System.out.println("Sort employees by salary in descending order - ");
        employees.stream().sorted(Comparator.comparing((Employee e) -> e.salary).reversed()).forEach(System.out::println);
        System.out.println("----------------------------");

        //Get list of employee names from 'New York'
        System.out.println("Get list of employee names from 'New York' - ");
        employees.stream().filter(e -> e.Location.equals("New York")).forEach(System.out::println);
        System.out.println("----------------------------");

        // Average salary by department
        System.out.println("Average salary by department - ");
        Map<String, Double> avgSalaryBydept = employees.stream().collect(Collectors.groupingBy(e -> e.department, Collectors.averagingDouble(e_ -> e_.salary)));
        System.out.println(avgSalaryBydept);
        System.out.println("----------------------------");

        // Group employees by department and then location
        System.out.println("Group employees by department and then location - ");
        Map<String, Map<String, List<Employee>>> dept = employees.stream().collect(Collectors.groupingBy(e -> e.department
        , Collectors.groupingBy(e -> e.Location)));

        dept.forEach((k, v) -> {
            System.out.println(k +" = "+v);
        });
        System.out.println("----------------------------");

        //Partition employees by salary > x amount
        System.out.println("Partition employees by salary > x amount - ");
        Map<Boolean, List<Employee>> partitionBySalary = employees.stream().collect(Collectors.partitioningBy(e -> e.salary > 15));
        System.out.println(partitionBySalary);
        System.out.println("----------------------------");









    }
}
