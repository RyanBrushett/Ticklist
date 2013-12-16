package controllers;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import play.mvc.*;
import play.libs.*;
import play.test.*;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

public class LoginTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
        Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("test-init.yml");
        Ebean.save(all.get("users"));
        Ebean.save(all.get("crags"));
        Ebean.save(all.get("boulders"));
    }

    @Test
    public void authenticateSuccess(){
        Result result = callAction(
            controllers.routes.ref.Application.authenticate(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "username","ryanbrushett",
                "password","Wireless01!"))
        );
        assertEquals(303,status(result));
        assertEquals("ryanbrushett",session(result).get("username"));
    }

    @Test
    public void authenticateFailure(){
        Result result = callAction(
            controllers.routes.ref.Application.authenticate(),
            fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                "username","ryanbrushett",
                "password","badpassword"))
        );
        assertEquals(400,status(result));
        assertNull(session(result).get("username"));
    }

    @Test
    public void authenticated(){
        Result result = callAction(
            controllers.routes.ref.Application.index(),
            fakeRequest().withSession("username","ryanbrushett")
        );
        assertEquals(200,status(result));
    }

    @Test
    public void notAuthenticated(){
        Result result = callAction(
            controllers.routes.ref.Application.index(),
            fakeRequest()
        );
        assertEquals(303,status(result));
        assertEquals("/login",header("Location",result));
    }
}
