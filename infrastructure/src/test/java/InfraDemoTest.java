import com.frb.ApplicationDemo;
import com.frb.DomainDemo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfraDemoTest {

    @Test
    public void shouldCreateAnApplicationDemo() {

        var app = new ApplicationDemo("appDemo");
        var domain = new DomainDemo("domainDesc");


        Assertions.assertNotNull(app);
        Assertions.assertNotNull(domain);
        Assertions.assertEquals("appDemo", app.getName());
        Assertions.assertEquals("domainDesc", domain.getDescription());
    }
}
