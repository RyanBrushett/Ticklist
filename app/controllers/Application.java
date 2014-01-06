package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;
import views.html.*;
public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
      return ok(index.render(
          Crag.find.all(),
          Boulder.findBoulderSentBy(request().username()),
          Climber.find.byId(request().username())
      ));
    }

    public static Result javascriptRoutes(){
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.Crags.addCrag(),
                controllers.routes.javascript.Crags.deleteCrag(),
                controllers.routes.javascript.Boulders.addBoulder(),
                controllers.routes.javascript.Boulders.deleteBoulder()
            )
        );
    }

    public static Result login(){
       return ok(
           login.render(form(Login.class))
       );
    }

    public static Result logout(){
        session().clear();
        flash("success","You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }

    public static Result authenticate(){
       Form<Login> loginForm = form(Login.class).bindFromRequest();
       if (loginForm.hasErrors()){
           return badRequest(login.render(loginForm));
       }
       else{
           session().clear();
           session("username",loginForm.get().username);
           return redirect(
               routes.Application.index()
           );
       }
    }
    public static Result about(){
        return ok(
            about.render()
        );
    }

        // Internal static Login class
    public static class Login{
       public String username;
       public String password;

       public String validate(){
          if (Climber.authenticate(username,password) == null){
             return "Invalid user or password";
          }
          return null;
       }
    }
}
