package controllers;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import models.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

public class BouldersTest extends WithApplication {
    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase(),fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-init.yml");
        Ebean.save(all.get("users"));
        Ebean.save(all.get("crags"));
        Ebean.save(all.get("boulders"));
    }

    @Test
    public void newBoulderTest(){
        Result result = callAction(
            controllers.routes.ref.Boulders.addBoulder(),
            fakeRequest().withSession("username","ryanbrushett")
                .withFormUrlEncodedBody(ImmutableMap.of(
                    "climbName","Seahorse",
                    "firstAssent","(unknown)",
                    "grade","V2",
                    "cragName","Marine Lab"
                 ))
        );
        assertEquals(200,status(result));
        Boulder b1 = Boulder.find.where().eq("climbName","Seahorse").findUnique();
        List results = Boulder.find.all();
        assertEquals(4,results.size());
        assertNotNull(b1);
        assertEquals("Seahorse",b1.climbName);
        assertEquals("V2",b1.grade);
    }

    @Test
    public void deleteBoulderTest(){
        Result result = callAction(
            controllers.routes.ref.Boulders.addBoulder(),
            fakeRequest().withSession("username","ryanbrushett")
                .withFormUrlEncodedBody(ImmutableMap.of(
                    "climbName","Seahorse",
                    "firstAssent","(unknown)",
                    "grade","V2",
                    "cragName","Marine Lab"
                 ))
        );
        assertEquals(200,status(result));
        Boulder b1 = Boulder.find.where().eq("climbName","Seahorse").findUnique();
        List results = Boulder.find.all();
        assertEquals(4,results.size());
        assertNotNull(b1);
        assertEquals("Seahorse",b1.climbName);
        assertEquals("V2",b1.grade);
        Result result2 = callAction(
            controllers.routes.ref.Boulders.deleteBoulder(b1.climbName),
            fakeRequest().withSession("username","ryanbrushett"));
        assertEquals(200,status(result2));
        List results2 = Boulder.find.all();
        assertEquals(3,results2.size());
    }
}
