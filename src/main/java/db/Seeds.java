package db;

import models.Department;
import models.Employee;
import models.Engineer;
import models.Manager;

public class Seeds {
    public static void seedData(){
        DBHelper.deleteAll(Employee.class);
        DBHelper.deleteAll(Engineer.class);
        DBHelper.deleteAll(Manager.class);
        DBHelper.deleteAll(Department.class);

        Department department1 = new Department("HR");
        Department department2 = new Department("IT");
        DBHelper.save(department1);
        DBHelper.save(department2);

        Manager manager = new Manager("Michael", "Scott", 40000,department1, 100000 );
        DBHelper.save(manager);

        Engineer engineer1 = new Engineer("Creed", "Bratton", 29000, department1);
        Engineer engineer2 = new Engineer("Stanley", "Hudson", 27000, department1);
        DBHelper.save(engineer1);
        DBHelper.save(engineer2);
    }
}
