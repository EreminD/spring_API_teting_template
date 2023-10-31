package ru.inno.myspringtests;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Component
@Scope("prototype")
public class HttpSteps {
    private RequestSpecification requestSpecification;
    private ValidatableResponse response;

    @PostConstruct
    private void postConstruct() {
        requestSpecification = given();
    }

    @Step("Установить заголовок {key}={value}")
    public void setHeader(String key, String value) {
        requestSpecification.header(new Header(key, value));
    }

    @Step("Установить Content-Type=application/json")
    public void setContentType() {
        requestSpecification.contentType(ContentType.JSON);
    }

    @Step("Указать тело запроса")
    @Attachment(type = "text", value = "request")
    public String setBody(String body) {
        requestSpecification.body(body);
        return body;
    }

    @Step("отправить POST запрос на {url}")
    public void post(String url) {
        response = requestSpecification.when().post(url).then();
    }

    @Step("проверить, что в теле ответа есть {path}:{value}")
    public void checkBodyContains(String path, String value) {
        response.body(path, equalTo(value));
    }

    @Step("проверить, что status code = {code}")
    public void checkStatusCode(int code) {
        response.statusCode(code);
    }
}
