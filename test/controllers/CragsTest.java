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

public class CragsTest extends WithApplication {
    @Before
    public void setUp(){
        start(fakeApplication(inMemoryDatabase(),fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-init.yml");
        Ebean.save(all.get("users"));
        Ebean.save(all.get("crags"));
        Ebean.save(all.get("boulders"));
    }

    @Test
    public void newCragTest(){
        Result result = callAction(
            controllers.routes.ref.Crags.addCrag(),
            fakeRequest().withSession("username","ryanbrushett")
                .withFormUrlEncodedBody(ImmutableMap.of("cragName","Blood Bath","location","Flatrock NL"))
        );
        assertEquals(200,status(result));
        Crag crag1 = Crag.find.where().eq("cragName","Blood Bath").findUnique();
        List results = Crag.findAll();
        assertEquals(3,results.size());
        assertNotNull(crag1);
        assertEquals("Flatrock NL",crag1.location);
        assertEquals("Blood Bath",crag1.cragName);
    }
}
