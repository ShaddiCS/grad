package topjava.grad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import topjava.grad.domain.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static topjava.grad.config.JacksonConfiguration.getMapper;

public class TestUtil {
    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        ObjectReader reader = getMapper().readerFor(clazz);
        String json = getContent(result);
        List<T> objectList;

        try {
            objectList = reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }

        return objectList;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        String json = getContent(action.andReturn());

        ObjectReader reader = getMapper().readerFor(clazz);
        T object;

        try {
            object = reader.<T>readValue(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }

        return object;
    }

    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> String writeValues(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionalProps(T obj, String propName, String password) {
        Map<String, Object> map = getMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {});
        map.putAll(Map.of(propName, password));

        return writeValues(map);
    }
}
