package models;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import java.util.List;

public class ModelsTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void createAndRetrieveClimber(){
        new Climber("ryanbrushett","Ryan Brushett","Wireless01!").save();
        Climber ryan = Climber.find.where().eq("username","ryanbrushett").findUnique();
        assertNotNull(ryan);
        assertEquals("Ryan Brushett",ryan.name);
    }

    @Test
    public void tryAuthenticateUser(){
        new Climber("ryanbrushett","Ryan Brushett","Wireless01!").save();
        assertNotNull(Climber.authenticate("ryanbrushett","Wireless01!"));
        assertNull(Climber.authenticate("ryanbrushett","badpassword"));
        assertNull(Climber.authenticate("bananas","badpassword"));
    }

    @Test
    public void findCragsInvolving(){
        new Climber("ryanbrushett","Ryan Brushett","Wireless01!").save();
        new Climber("katesargent","Kate Sargent","swisscheese").save();

        Crag.create("Main Face","Flackrock, NL","ryanbrushett");
        Crag.create("Bloodbath","Flatrock, NL","katesargent");

        List<Crag> results = Crag.findInvolving("ryanbrushett");
        assertEquals(1,results.size());
        assertEquals("Main Face",results.get(0).cragName);
    }

    @Test
    public void findBouldersInvolving(){
        Climber ryan = new Climber("ryanbrushett","Ryan Brushett","Wireless01!");
        ryan.save();

        Crag crag = Crag.create("Main Face","Flatrock, NL","ryanbrushett");
        Boulder b1 = new Boulder();
        b1.climbName = "Nazis and their Chickens";
        b1.grade = "V3";
        b1.crag = crag;
        b1.haveSent.add(ryan);
        b1.sent = true;
        b1.save();

        Boulder b2 = new Boulder();
        b2.climbName = "Nosferatu";
        b2.grade = "V5";
        b2.crag = crag;
        b2.save();

        List<Boulder> results = Boulder.findBoulderSentBy("ryanbrushett");
        assertEquals(1,results.size());
        assertEquals("Nazis and their Chickens",results.get(0).climbName);
    }

    @Test
    public void findAllBoulders(){
        Climber ryan = new Climber("ryanbrushett","Ryan Brushett","Wireless01!");
        ryan.save();

        Crag crag = Crag.create("Main Face","Flatrock, NL","ryanbrushett");
        Boulder b1 = new Boulder();
        b1.climbName = "Nazis and their Chickens";
        b1.grade = "V3";
        b1.crag = crag;
        b1.haveSent.add(ryan);
        b1.sent = true;
        b1.save();

        Boulder b2 = new Boulder();
        b2.climbName = "Nosferatu";
        b2.grade = "V5";
        b2.crag = crag;
        b2.save();

        List<Boulder> results = Boulder.findAll();
        assertEquals(2,results.size());
        assertEquals("Nosferatu",results.get(1).climbName);
    }

    @Test
    public void findAllUnsent(){
        Climber ryan = new Climber("ryanbrushett","Ryan Brushett","Wireless01!");
        ryan.save();

        Crag crag = Crag.create("Main Face","Flatrock, NL","ryanbrushett");
        Boulder b1 = new Boulder();
        b1.climbName = "Nazis and their Chickens";
        b1.grade = "V3";
        b1.crag = crag;
        b1.haveSent.add(ryan);
        b1.sent = true;
        b1.save();

        Boulder b2 = new Boulder();
        b2.climbName = "Nosferatu";
        b2.grade = "V5";
        b2.crag = crag;
        b2.save();

        List<Boulder> results = Boulder.findBouldersNeverSent();
        assertEquals(1,results.size());
        assertEquals("Nosferatu",results.get(0).climbName);
    }

    @Test
    public void findBouldersNeverSentBy(){
        Climber ryan = new Climber("ryanbrushett","Ryan Brushett","Wireless01!");
        ryan.save();

        Crag crag = Crag.create("Main Face","Flatrock, NL","ryanbrushett");

        Boulder b1 = new Boulder();
        b1.climbName = "Nazis and their Chickens";
        b1.grade = "V3";
        b1.crag = crag;
        b1.haveSent.add(ryan);
        b1.sent = true;
        b1.save();

        Boulder b2 = new Boulder();
        b2.climbName = "Nosferatu";
        b2.grade = "V5";
        b2.crag = crag;
        b2.save();
    }
}
