package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;
public class Boulders extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result biglist() {
      return ok(index.render(
          Crag.find.all(),
          Boulder.find.all(),
          Climber.find.byId(request().username())
      ));
    }
}
