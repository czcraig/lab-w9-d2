package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ManagersController {

        public ManagersController(){
            this.setupEndpoints();
        }

        private void setupEndpoints(){

            VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

            get("/managers", (req, res) -> {
                Map<String, Object> model = new HashMap();
                model.put("template", "templates/managers/index.vtl");
                List<Manager> managers = DBHelper.getAll(Manager.class);
                model.put("managers", managers);
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            get("/managers/new", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                List<Department> department = DBHelper.getAll(Department.class);
                model.put("departments", department);
                model.put("template", "templates/managers/create.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            post("/managers", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                String firstName = request.queryParams("first-name");
                String lastName = request.queryParams("last-name");
                int salary = Integer.valueOf(request.queryParams("salary"));
                Department department = DBHelper.find(Integer.valueOf(request.queryParams("department")), Department.class);
                double budget = Double.valueOf(request.queryParams("budget"));
                Manager newManager = new Manager(firstName, lastName, salary, department, budget);
                DBHelper.save(newManager);
                response.redirect("/managers");
                return null;
            }, velocityTemplateEngine);

            post("/managers/:id/delete", (request, response) -> {
                DBHelper.delete(DBHelper.find(Integer.parseInt(request.params("id")), Manager.class));
                response.redirect("/managers");
                return null;
            }, velocityTemplateEngine);

            get("/managers/:id/edit", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                List<Department> department = DBHelper.getAll(Department.class);
                model.put("manager", DBHelper.find(Integer.parseInt(request.params("id")), Manager.class));
                model.put("departments", department);
                model.put("template", "templates/managers/update.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            post("/managers/:id", (request, response) -> {
                HashMap<String, Object> model = new HashMap<>();
                String firstName = request.queryParams("first-name");
                String lastName = request.queryParams("last-name");
                int salary = Integer.valueOf(request.queryParams("salary"));
                Department department = DBHelper.find(Integer.valueOf(request.queryParams("department")), Department.class);
                double budget = Double.valueOf(request.queryParams("budget"));
                Manager newManager = new Manager(firstName, lastName, salary, department, budget);
                newManager.setId(Integer.parseInt(request.params("id")));
                DBHelper.update(newManager);
                response.redirect("/managers");
                return null;
            }, velocityTemplateEngine);
        }
}