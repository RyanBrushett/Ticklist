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
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Climber> haveSent = new ArrayList<Climber>();
    @ManyToOne
    public Crag crag;

    public static Model.Finder<String,Boulder> find = new Model.Finder(String.class,Boulder.class);

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

    public static Boulder create(Boulder boulder, String crag, String grade){
        boulder.crag = Crag.find.ref(crag);
        boulder.grade = grade;
        boulder.save();
        return boulder;
    }
}
