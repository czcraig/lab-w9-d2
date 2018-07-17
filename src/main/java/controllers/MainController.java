package controllers;

import db.DBHelper;
import db.Seeds;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class MainController {
    public static void main(String[] args) {

        ManagersController managersController = new ManagersController();
        EmployeesController employeesController = new EmployeesController();
        EngineerController engineerController = new EngineerController();

        Seeds.seedData();

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap();
            model.put("template", "templates/home.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);
    }
}

