package ru.inno.myspringtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ApiTests {

    @Autowired
    private HttpSteps steps;

    @Value("${base_url}")
    private String url;

    @Test
    void todoTest() {
        steps.setHeader("ABC", "123");
        steps.setContentType();
        steps.setBody("{\"title\":\"test\",\"completed\":false}");
        steps.post(url);
        steps.checkBodyContains("title", "test");
        steps.checkStatusCode(200);
    }
}
