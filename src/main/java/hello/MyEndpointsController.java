package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.math.BigInteger;
import org.springframework.util.MultiValueMap;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.MediaType;

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

    @RequestMapping(
      value = { "/normal_user/testEndpoint" },
      method = RequestMethod.GET
    )
    @ResponseBody
    public String getNormalUserEndpoint() {
        return "Test user-role endpoint";
    }

    // Sample curl (remember about apostrophes around URL, without them only one param will be visible):
    // curl -i -X GET -u foo:bar 'localhost:8080/getwithmanyparams?id1=111&id2=93242342422354344682&name=.ABC.DE.F%2EGH%2EEA%2E'
    @RequestMapping(
      value = "/getwithmanyparams",
      // params = { "id1", "id2", "name" },
      method = RequestMethod.GET
    )
    public TestIdsDto getObjectWithManyParams(@RequestParam int id1, @RequestParam(required = false) BigInteger id2, @RequestParam(required = false) String name) {
        if (name == null) {
          return new TestIdsDto.TestIdsDtoBuilder(id1).setId2(id2).build();
        } else {
          return new TestIdsDto.TestIdsDtoBuilder(id1).setId2(id2).setName(name).build();
        }
    }

    // curl -i -X GET -u foo:bar 'localhost:8080/getlistwithmanyparams?name1=AB.CD&name2=.EF.GH&name3=IJ.KL%2E.'
    @RequestMapping(
      value = "/getlistwithmanyparams",
      method = RequestMethod.GET
    )
    public List<TestIdsDto> getObjectWithManyParams(@RequestParam String name1, @RequestParam String name2, @RequestParam() String name3) {
      List<TestIdsDto> dtos = new ArrayList();
      dtos.add(new TestIdsDto.TestIdsDtoBuilder(1).setId2(BigInteger.valueOf(1001L)).setName(name1).build());
      dtos.add(new TestIdsDto.TestIdsDtoBuilder(2).setId2(BigInteger.valueOf(1002L)).setName(name2).build());
      dtos.add(new TestIdsDto.TestIdsDtoBuilder(3).setId2(BigInteger.valueOf(1003L)).setName(name3).build());
      return dtos;
    }

    @RequestMapping(
      value = "/jsonfromstring",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getJsonFromString() {
      return "[{\"id1\":1,\"id2\":1001,\"name\":\"AB.CD\"},{\"id1\":2,\"id2\":1002,\"name\":\".EF.GH\"}]";
    }

    // Many params catched by HttpServletRequest
    // Sample curl (remember about apostrophes around URL, withou them only one param will be visible)
    // curl -i -X GET -u foo:bar 'localhost:8080/manyparamsservlet?id1=111&id2=2324234242235434547867889087897954065&name=.ABC.DE.F.GHI%2EJK%2ELMN%2E'
    @RequestMapping("/manyparamsservlet")
    public String manyParamsByServletRequest(HttpServletRequest request) {
        String message = "ID1: " + request.getParameter("id1") + ", ";
        message += "ID2: " + request.getParameter("id2") + ", ";
        message += "Name: " + request.getParameter("name");

        return message;
    }

    // Obtain query params as too different maps
    // curl -i -X GET -u admin:password 'localhost:8080/paramsasmaps?id1=111&id2=93242342422354344682&name=.ABC.DE.F%2EGH%2EEA%2EL'
    @RequestMapping(value="/paramsasmaps", method=RequestMethod.GET)
    public void queryMethod(@RequestParam String id1,
              @RequestParam Map<String, String> queryParameters,
              @RequestParam MultiValueMap<String, String> multiMap) {
        System.out.println("id1=" + id1);
        System.out.println(queryParameters);
        System.out.println(multiMap);
        // Effect:
        // id1=111
        // {id1=111, id2=93242342422354344682, name=.ABC.DE.F.GH.EA.}
        // {id1=[111], id2=[93242342422354344682], name=[.ABC.DE.F.GH.EA.]}
    }

    // Obtain query params as MultiValueMap
    // curl -i -X GET -u foo:bar 'localhost:8080/paramsmultimap?id1=111&id2=93242342422354344682&name=.ABC.DE.F%2EGH%2EEA%2E'
    @RequestMapping(value="/paramsmultimap", method=RequestMethod.GET)
    public String books(@RequestParam MultiValueMap<String, String> requestParams) {
      System.out.println(requestParams); // {id1=[111], id2=[93242342422354344682], name=[.ABC.DE.F.GH.EA.]}
      return "Check Spring log for results!";
    }

}
