import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTests {

    private ClassLoader cl = JsonTests.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonFileTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("JsonTest.json")) {
            if (is == null) {
                throw new RuntimeException("Файл не найден");
            }
            Json json = objectMapper.readValue(is, Json.class);

            assertEquals("Buddy", json.getName());
            assertEquals("Golden Retriever", json.getBreed());
            assertEquals(5, json.getAge());
            assertEquals(30.5, json.getWeight());
            assertEquals(true, json.isVaccinated());
            assertEquals(3, json.getHobbies().size());
            assertEquals("fetch", json.getHobbies().get(0));
            assertEquals("swimming", json.getHobbies().get(1));
            assertEquals("playing with toys", json.getHobbies().get(2));
        }
    }
}