package specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static credentials.RestfulBookerCredentials.ADMIN;
import static io.restassured.RestAssured.preemptive;

public class RequestSpecifications {
    public static final RequestSpecification CONTENT_TYPE_AND_AUTH_SPEC = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setAuth(preemptive().basic(ADMIN.login, ADMIN.password))
            .build();
}
