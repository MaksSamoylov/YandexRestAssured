import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AllureTest2 {
    @Test
    public void testPut() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Igor");
        jsonObject.put("job", "HR");
        given().
                contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("job", is("HR"))
                .log().all();
    }

}
