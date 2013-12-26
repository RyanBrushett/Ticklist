package controllers;

import java.util.*;

import static play.data.Form.form;
import play.mvc.*;
import views.html.crags.*;
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


        // These don't work yet and I don't think they will.
        // Notably - I made cragName the PK of Crag and, since
        // you can't very well edit that, this is going to
        // need a sober second look later
    /*public static Result renameCrag(String name){
        return ok(
            Crag.renameCrag(
                name,
                form().bindFromRequest().get("newName")
            )
        );
    }

        // And because the relationship goes boulder manytoone
        // crag, I can't delete a crag without deleting boulders
        // Poor planning but then this is a learning experience.
    public static Result deleteCrag(String cragname){
        Crag.find.ref(cragname).delete();
        return ok();
    }*/
}
