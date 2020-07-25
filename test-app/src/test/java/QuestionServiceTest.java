import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.testApplication.config.YamlProps;
import ru.testApplication.service.QuestionServiceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = YamlProps.class)
@TestPropertySource("classpath:application.yml")
public class QuestionServiceTest {

    @Autowired
    private YamlProps props;

    @Test
    public void correctPassAmount() {
        int passLimit = 3;
        int testPassLimit = props.getLimit();

        assertEquals(passLimit, testPassLimit);
    }
}