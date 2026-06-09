import java.util.*;

class Main {
    public static void main(String[] args) {
        System.out.println("Start small. Ship something.");
        List<Employee> list = new ArrayList<>();
        Employee emp1 = new Employee("IT", 4000);
        Employee emp2 = new Employee("IT", 5000);
        Employee emp3 = new Employee("QA", 5500);
        Employee emp4 = new Employee("QA", 4000);
        list.add(emp1);
        list.add(emp2);
        list.add(emp4);
        list.add(emp3);
        getSecondHighest(list);
    }
    static class Employee {
        private String name;
        private String department;
        private int salary;
        public Employee(String department, int salary) {
            this.department = department;
            this.salary = salary;
        }
        public int getSalary() {
            return this.salary;
        }
        public String getDepartment() {
            return this.department;
        }
    }
    public static void getSecondHighest(List<Employee> employees) {
        Map<String, int[]> maxSalary = new HashMap<>();
        for (Employee employee: employees) {
            String dept = employee.getDepartment();
            maxSalary.putIfAbsent(dept, new int[]{0,0});
            int[] sal = maxSalary.get(dept);
            if (sal[0] == 0) {
                sal[0] = employee.getSalary();
            }
            else if (sal[0] < employee.getSalary()) {
                int temp = sal[0];
                sal[0] = employee.getSalary();
                sal[1] = temp;
            }
            else {
                sal[1] = Math.max(employee.getSalary(), sal[1]);
            }
        }

        Map<String, Integer> result = new HashMap<>();
        for (String key : maxSalary.keySet()) {
            result.put(key, maxSalary.get(key)[1]);
        }
        System.out.println(result);
    }
}