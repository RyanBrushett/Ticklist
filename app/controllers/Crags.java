package controllers;

import java.util.*;

import static play.data.Form.form;
import play.mvc.*;
import views.html.crags.*;
import views.html.*;
import models.*;

@Security.Authenticated(Secured.class)
public class Crags extends Controller {

    public static Result addCrag(){
        Crag newCrag = Crag.create(
            form().bindFromRequest().get("cragName"),
            form().bindFromRequest().get("location")
        );
        return ok(item.render(newCrag));
    }

    public static Result deleteCrag(String cragname){
        Crag.find.ref(cragname).delete();
        return ok();
    }

    public static Result listCrags(){
        return ok(locations.render(
            Crag.find.all(),
            Climber.find.byId(request().username())
        ));
    }
}
