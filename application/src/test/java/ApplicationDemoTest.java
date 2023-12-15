import com.frb.ApplicationDemo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationDemoTest {

    @Test
    public void shouldCreateAnApplicationDemo() {

        final var app = new ApplicationDemo("appDemo");

        Assertions.assertNotNull(app);
        Assertions.assertEquals("appDemo", app.getName());
    }
}
