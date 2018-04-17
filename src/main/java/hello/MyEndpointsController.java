package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class MyEndpointsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // Get name from query string, the same as in GreetingController, but with other endpoint
    @RequestMapping("/greeting1")
    public Greeting greeting1(@RequestParam(value="name", defaultValue="World") String name) { // Method name can be different - semms like that does not matter.
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping(
      value = "/greeting2",
      method = RequestMethod.GET,
      produces = { "application/json" }
    )
    public Greeting greeting2(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format("MyGreetingController.greeting2: " + template, name));
    }

    @RequestMapping(
      value = "/greeting-get",
      method = RequestMethod.GET,
      produces = { "application/json" }
    )
    public Greeting greetingGet(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format("MyGreetingController.greetingGet: " + template, name));
    }

    @RequestMapping(
      value = "/greeting-post",
      method = RequestMethod.POST,
      produces = { "application/json" }
    )
    public Greeting greetingPost(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format("MyGreetingController.greetingPost: " + template, name));
    }

    // Try produces "text/html"
    // curl -i -X GET localhost:8080/ex/foos
    @RequestMapping(
      value = "/ex/foos",
      method = RequestMethod.GET,
      produces = { "text/html" }  // without this 'text/plain' is returned
    )
    // @ResponseBody // If controller is annotated as RestController this ResponseBody should be useless
    public String getFoosBySimplePath() {
        return "Get some Foos";
    }

    // curl -i -X GET localhost:8080/ex/foos/134
    @RequestMapping(value = "/ex/foos/{id}", method = RequestMethod.GET)
    // @ResponseBody - useless in @RestController
    public String getFoosBySimplePathWithPathVariable(
      @PathVariable("id") long id) {
        return "Get a specific Foo with id=" + id;
    }

    // If the name of the method argument matches the name of the path variable exactly, then this can be simplified by using @PathVariable with no value:
    // Test curl command (with basic authentication)
    // curl -i -X GET -u user:password localhost:8080/ex/foos/param-without-name/104
    @RequestMapping(value = "/ex/foos/param-without-name/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosByPathWithoutParamName(
      @PathVariable String id) {
        return "Get a specific Foo with id=" + id;
        // Note that @PathVariable benefits from automatic type conversion, so we could have also declared the id as:
        // @PathVariable long id
    }

    @RequestMapping(
      value = { "/", "/home" },
      method = RequestMethod.GET
    )
    @ResponseBody
    public String getMainEndpoint() {
        return "Main endpoint, without athentication";
    }

    @RequestMapping(
      value = { "/admin/testEndpoint" },
      method = RequestMethod.GET
    )
    @ResponseBody
    public String getAdminOnlyEndpoint() {
        return "Admin only endpoint";
    }

}
