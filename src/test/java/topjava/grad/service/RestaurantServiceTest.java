package topjava.grad.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = "classpath:schema.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }
}