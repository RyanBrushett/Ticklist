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
}
