package models;

import java.util.*;
import javax.persistence.*;
import play.db.*;
import play.db.ebean.*;

@Entity
public class Crag extends Model{
    @Id
    public String cragName;
    public String location;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Climber> climbers = new ArrayList<Climber>();

    public Crag(String cragName, String location, Climber climber){
        this.cragName = cragName;
        this.location = location;
        this.climbers.add(climber);
    }

    public static Model.Finder<String,Crag> find = new Model.Finder(String.class,Crag.class);

    public static Crag create (String cragName, String location, String climber){
        Crag crag = new Crag(cragName, location, Climber.find.ref(climber));
        crag.save();
        crag.saveManyToManyAssociations("climbers");
        return crag;
    }

    public static List<Crag> findInvolving(String climber){
        return find.where().eq("climbers.username",climber).findList();
    }

    public static List<Crag> findAll(){
        return find.all();
    }
}
