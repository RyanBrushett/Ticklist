import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings{
    @Override
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    static class InitialData {
        public static void insert(Application app) {
            if(Climber.find.findRowCount() == 0) {
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("init.yml");
                Ebean.save(all.get("users"));
                Ebean.save(all.get("crags"));
                Ebean.save(all.get("boulders"));
            }
        }
    }   
}
