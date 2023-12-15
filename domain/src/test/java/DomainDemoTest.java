import com.frb.DomainDemo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DomainDemoTest {

    @Test
    public void shouldCreateAnApplicationDemo() {

        final var domain = new DomainDemo("domainDemo");

        Assertions.assertNotNull(domain);
        Assertions.assertEquals("domainDemo", domain.getDescription());
    }
}
