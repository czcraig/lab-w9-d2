package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class EmployeesController {
    
        public EmployeesController(){
            this.setupEndpoints();
        }

        private void setupEndpoints(){

            VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
        get("/employees", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/employees/index.vtl");
            List<Employee> employees = DBHelper.getAll(Employee.class);
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

            get("/engineers/new", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                List<Department> department = DBHelper.getAll(Department.class);
                model.put("departments", department);
                model.put("template", "templates/engineers/create.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            post("/engineers", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                String firstName = request.queryParams("first-name");
                String lastName = request.queryParams("last-name");
                int salary = Integer.valueOf(request.queryParams("salary"));
                Department department = DBHelper.find(Integer.valueOf(request.queryParams("department")), Department.class);
                Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
                DBHelper.save(newEngineer);
                response.redirect("/engineers");
                return null;
            }, velocityTemplateEngine);
    }
}
