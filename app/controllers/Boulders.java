package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;
import views.html.boulders.*;

@Security.Authenticated(Secured.class)
public class Boulders extends Controller {

    public static Result biglist() {
      return ok(index.render(
          Crag.find.all(),
          Boulder.find.all(),
          Climber.find.byId(request().username())
      ));
    }

    public static Result filteredList(String cragName){
        return ok(index.render(
            Crag.find.where().eq("cragName",cragName).findList(),
            Boulder.getBouldersByCrag(cragName),
            Climber.find.byId(request().username())
        ));
    }

    public static Result addPage(){
        return ok(addboulder.render(
            Crag.find.all(),
            Climber.find.byId(request().username())
        ));
    }

    public static Result addBoulder(){
        Boulder newBoulder = Boulder.create(
            form().bindFromRequest().get("climbName"),
            form().bindFromRequest().get("firstAssent"),
            form().bindFromRequest().get("grade"),
            form().bindFromRequest().get("cragName")
        );
        return ok(testItem.render(newBoulder));
    }

    public static Result deleteBoulder(String name){
        Boulder.find.ref(name).delete();
        return ok();
    }
}
