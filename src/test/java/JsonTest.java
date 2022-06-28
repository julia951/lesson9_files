import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    ClassLoader classLoader = JsonTest.class.getClassLoader();

    @Test
    @DisplayName("Testing Json file using Jackson library")
    void jsonTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = classLoader.getResourceAsStream("jsonFile");
        JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(inputStream));

        assertThat(jsonNode.get("title").asText()).isEqualTo("A New Hope");
        assertThat(jsonNode.get("episode_id").asText()).isEqualTo("4");
        assertThat(jsonNode.get("opening_crawl").asText()).isEqualTo("It is a period of civil war....");
        assertThat(jsonNode.get("director").asText()).isEqualTo("George Lucas");
        assertThat(jsonNode.get("producer").asText()).isEqualTo("Gary Kurtz, Rick McCallum");
        assertThat(jsonNode.get("release_date").asText()).isEqualTo("1977-05-25");
    }
}