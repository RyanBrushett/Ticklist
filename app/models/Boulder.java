package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.*;

@Entity
public class Boulder extends Model{
    @Id
    public String climbName;
    public String firstAssent;
    public String grade;
    public boolean sent = true;
    public Date climbedDate;
    @ManyToMany(cascade = CascadeType.ALL)
    public List<Climber> haveSent = new ArrayList<Climber>();
    @ManyToOne
    public Crag crag;

    public static Model.Finder<String,Boulder> find = new Model.Finder(String.class,Boulder.class);

    public Boulder(String name, String fa, String grade, String cragname){
        climbName = name;
        firstAssent = fa;
        this.grade = grade;
        crag = Crag.find.ref(cragname);
    }

    public static List<Boulder> findBoulderSentBy(String climber){
        return find.where().eq("haveSent.username",climber).findList();
    }
    public static List<Boulder> findAllSent(){
        return find.where().eq("sent",true).findList();
    }
    public static List<Boulder> findAll(){
        return find.all();
    }
    public static List<Boulder> findBouldersNeverSent(){
        return find.where().eq("sent",false).findList();
    }
    public static List<Boulder> getBouldersByCrag(String crag){
        return find.where().eq("crag.cragName",crag).findList();
    }

    public static Boulder create(String name, String fa, String grade, String cragname){
        Boulder boulder = new Boulder(name,fa,grade,cragname);
        boulder.save();
        return boulder;
    }
}
