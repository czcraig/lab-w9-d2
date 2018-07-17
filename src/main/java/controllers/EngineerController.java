package controllers;

import db.DBHelper;
import models.*;
import models.Engineer;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

public class EngineerController {

    public EngineerController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
        get("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/engineers/index.vtl");
            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        post("/engineers/:id/delete", (request, response) -> {
            DBHelper.delete(DBHelper.find(Integer.parseInt(request.params("id")), Engineer.class));
            response.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);

        get("/engineers/:id/edit", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> department = DBHelper.getAll(Department.class);
            model.put("engineer", DBHelper.find(Integer.parseInt(request.params("id")), Engineer.class));
            model.put("departments", department);
            model.put("template", "templates/engineers/update.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/engineers/:id", (request, response) -> {
            HashMap<String, Object> model = new HashMap<>();
            String firstName = request.queryParams("first-name");
            String lastName = request.queryParams("last-name");
            int salary = Integer.valueOf(request.queryParams("salary"));
            Department department = DBHelper.find(Integer.valueOf(request.queryParams("department")), Department.class);
            Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
            newEngineer.setId(Integer.parseInt(request.params("id")));
            DBHelper.update(newEngineer);
            response.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);
        }
    }
