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
    @OneToMany(mappedBy="crag",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
    public List<Boulder> boulders;

    public Crag(String cragName, String location){
        this.cragName = cragName;
        this.location = location;
    }

    public static Model.Finder<String,Crag> find = new Model.Finder(String.class,Crag.class);

    public static Crag create (String cragName, String location){
        Crag crag = new Crag(cragName, location);
        crag.save();
        return crag;
    }

    public static List<Crag> findAll(){
        return find.all();
    }
    
        // These don't work yet and I don't think they will.
        // Notably - I made cragName the PK of Crag and, since
        // you can't very well edit that, this is going to
        // need a sober second look later 
    public static String renameCrag(String name, String newName){
        Crag crag = find.where().eq("cragName",name).findUnique();
        crag.cragName = newName;
        crag.update();
        return crag.cragName;
    }
}
