package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.*;

@Entity
public class Boulder extends Model{
    @Id
    public Long id;
    public String climbName;
    public String firstAssent;
    public String grade;
    public boolean sent = false;
    public Date climbedDate;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Climber> haveSent = new ArrayList<Climber>();
    @ManyToOne
    public Crag crag;

    public static Model.Finder<String,Boulder> find = new Model.Finder(String.class,Boulder.class);

    public static List<Boulder> findBoulderSentBy(String climber){
        return find.where().eq("sent",true).eq("haveSent.username",climber).findList();
    }
    public static List<Boulder> findAllSent(){
        return find.fetch("boulder").where().eq("sent",true).findList();
    }
    public static List<Boulder> findAll(){
        return find.all();
    }
    public static List<Boulder> findBouldersNeverSent(){
        return find.where().eq("sent",false).findList();
    }
    public static Boulder getBoulderById(Long id){
        return find.fetch("boulder").where().eq("id",id).findUnique();
    }

    public static Boulder create(Boulder boulder, String crag, String grade){
        boulder.crag = Crag.find.ref(crag);
        boulder.grade = grade;
        boulder.save();
        return boulder;
    }

    /*public void addSend(String climber){
        haveSent.add(Climber.find.ref(climber));
        this.sent = true;
        this.saveManyToManyAssociations("haveSent");
    }

    public void addProject(String climber){
        projecting.add(Climber.find.ref(climber));
        this.saveManyToManyAssociations("projecting");
    }*/

}
