package controllers;

import java.util.*;

import static play.data.Form.form;
import play.mvc.*;
import views.html.*;
import models.*;

public class Climbers extends Controller {
    public static Result signup(){
        Climber newClimber = Climber.create(
            form().bindFromRequest().get("username"),
            form().bindFromRequest().get("password"),
            form().bindFromRequest().get("realname")
        );
        return redirect(routes.Application.login());
    }
}
