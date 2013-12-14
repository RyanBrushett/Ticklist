package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import java.util.List;
import java.util.Map;
import com.avaje.ebean.Ebean;
import play.*;
import play.libs.*;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        if(Climber.find.findRowCount() == 0){
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-init.yml");
            Ebean.save(all.get("users"));
            Ebean.save(all.get("crags"));
            Ebean.save(all.get("boulders"));
        }
    }

    @Test
    public void basicDbTest(){
        assertEquals(1,Climber.find.findRowCount());
        assertEquals(2,Crag.find.findRowCount());
        assertEquals(3,Boulder.find.findRowCount());
    }

    @Test
    public void authenticationTest(){
        assertNotNull(Climber.authenticate("ryanbrushett","Wireless01!"));
        assertNull(Climber.authenticate("ryanbrushett","badpassword"));
        assertNull(Climber.authenticate("katesargent","badpassword"));
    }

    @Test
    public void boulderCheckSent(){
        List<Boulder> boulders;
        boulders = Boulder.findAllSent();
        assertEquals(3,boulders.size());
    }

    @Test
    public void boulderCheckNeverSent(){
        List<Boulder> boulders;
        boulders = Boulder.findBouldersNeverSent();
        assertEquals(0,boulders.size());
    }

    @Test
    public void boulderCheckSentBy(){
        List<Boulder> boulders;
        boulders = Boulder.findBoulderSentBy("ryanbrushett");
        assertEquals(2,boulders.size());
        assertEquals("Nazis and their Chickens",boulders.get(0).climbName);
    }

    @Test
    public void boulderCheckBouldersByCrag(){
        List<Boulder> mainface  = Boulder.getBouldersByCrag("Main Face");
        List<Boulder> marinelab = Boulder.getBouldersByCrag("Marine Lab");
        assertEquals(2,mainface.size());
        assertEquals(1,marinelab.size());
        assertEquals(marinelab.get(0).climbName,"Poseidon");
        assertEquals(mainface.get(0).climbName,"Nazis and their Chickens");
    }

    @Test
    public void cragCheckCrags(){
        List<Crag> crags = Crag.findAll();
        assertEquals(2,crags.size());
        assertEquals(crags.get(0).cragName,"Main Face");
    }
}
