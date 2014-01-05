package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;

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

    public static Result addBoulder(){
        return ok(addboulder.render(
            Crag.find.all(),
            Climber.find.byId(request().username())
        ));
    }
}
