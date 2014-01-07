package models;

import play.db.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Climber extends Model{

    @Id
    public String username;
    public String name;
    public String password;

    public Climber(String username, String name, String password){
        this.username = username;
        this.name     = name;
        this.password = password;
    }

    public static Finder<String,Climber> find = new Finder<String,Climber>(
        String.class, Climber.class
    );

    public static Climber authenticate(String username, String password){
        return find.where().eq("username",username).eq("password",password).findUnique();
    }

    public static Climber create(String uname, String pword, String fullname){
        Climber user = new Climber(uname,fullname,pword);
        user.save();
        return user;
    }
}
