package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);
	
    private static final String template = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    // inject the value directoy
    @Value("${GREETING_PHRASE}")
    private String greetingPhrase;
    
    // alternatively, inject the Environment instance and pull the value from it
    @Autowired
    private Environment environment;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        
        LOG.info("Greeting phrase value injected: "+ greetingPhrase);

        String greetingFromEnv = environment.getProperty("GREETING_PHRASE");
        LOG.info("Greeting phrase value from environment: "+ greetingFromEnv);
    	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, greetingPhrase, name));
    }
}
