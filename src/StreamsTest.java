import java.util.*;
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

        // Find all employees whose name starts with "D".
        System.out.println("Find all employees whose name starts with 'D' - ");
        employees.stream().filter(e -> e.name.startsWith("D")).forEach(System.out::println);
        System.out.println("----------------------------");

        //Get a list of distinct departments.
        System.out.println("Get a list of distinct departments (List) - ");
        List<String> deptList = employees.stream()
                .map(e -> e.department)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(deptList);
        System.out.println("----------------------------");

        //Concatenate all employee names into a single string, separated by commas.
        System.out.println("Concatenate all employee names into a single string, separated by commas - ");
        String allEmployees = employees.stream().map(e -> e.name).collect(Collectors.joining(", "));
        System.out.println(allEmployees);
        System.out.println("----------------------------");

        // Find the employee with the maximum salary.
        System.out.println("Find the employee with the maximum salary - ");
        Employee employeeWithMaxIncome = employees.stream().max(Comparator.comparingDouble(e -> e.salary)).orElse(null);
        System.out.println(employeeWithMaxIncome);
        System.out.println("----------------------------");

        // Group employees by the first character of their name
        System.out.println("Group employees by first character of name - ");
        Map<Character, List<String>> groupEmployeesByFirstChar = employees.stream().
                collect(Collectors.groupingBy(e -> e.name.charAt(0), Collectors.mapping(e -> e.name, Collectors.toList())));
        System.out.println(groupEmployeesByFirstChar);
        System.out.println("----------------------------");

        // Find the total salary expense for all employees.
        System.out.println("Find the total salary expense for all employees - ");
        Double sumAllEmployeeSalary = employees.stream().mapToDouble(e -> e.salary ).sum();
        System.out.println(sumAllEmployeeSalary);
        System.out.println("----------------------------");

        // Group employees by department, and get average salary in each location under that department.
        System.out.println("Group employees by department, and get average salary in each location under that department - ");
        Map<String,Map<String,Double>> mapAgg = employees.stream()
                .collect(Collectors.groupingBy(e -> e.department,
                        Collectors.groupingBy(e -> e.Location, Collectors.averagingDouble(e -> e.salary))));
        System.out.println(mapAgg);
        System.out.println("----------------------------");


        //Partition employees based on whether they belong to the 'IT' department or not.
        //
        //Group employees by location, and within that, group by salary brackets (<15, >=15).









    }
}
