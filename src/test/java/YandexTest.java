import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class YandexTest {

    @Test
    public void postUnknownWord() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", "синхрафазатрон");
        given().
                contentType(ContentType.JSON)
                .body(jsonObject.toJSONString()).
                when().post("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("синхрафазатрон"))
                .body("code", is(1));
    }

    @Test
    public void getUnknownWord() {
        given().
                param("text", "синхрафазатрон").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("синхрафазатрон"))
                .body("code", is(1));
    }

    @Test
    public void setCityWithLowerCase() {
        given().
                param("text", "москва").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("москва"))
                .body("code", is(3))
                .body("s[0]", is("Москва"));
    }

    @Test
    public void setTheSameWords() {
        given().
                param("text", "поезд поехал поехал").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("поехал"))
                .body("code", is(2));
    }

    @Test
    public void setCityWithLowercaseAndUnknownWord() {
        given().
                param("text", "москва синхрафазатрон").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("москва"))
                .body("code", is(4));
    }

    @Test
    public void setInvalidValueToTextParam() {
        given().
                param("text", 0).
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(400);
    }

    @Test
    public void setRuLanguage() {
        given().
                param("lang", "ru").
                param("text", "корова").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("корова"));
    }

    @Test
    public void setUkLanguage() {
        given().
                param("lang", "uk").
                param("text", "корова").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("корова"));
    }

    @Test
    public void setEngLanguage() {
        given().
                param("lang", "eng").
                param("text", "cow").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200)
                .body("word", is("cow"));
    }

    @Test
    public void setInvalidLanguage() {
        given().
                param("lang", "Pold").
                param("text", "cow").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(400);
    }

    @Test
    public void checkIgnoreDigitsWithOptionsTwo() {
        given().
                param("options", 2).
                param("text", "вп17х4534").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200);
    }

    @Test
    public void checkIgnoreMailsUrl() {
        given().
                param("options", 4).
                param("text", "work@gmail.com").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200);
    }

    @Test
    public void checkHighlightTheSameWord() {
        given().
                param("options", 8).
                param("text", "машина машина").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200);
    }

    @Test
    public void checkInvalidLowercaseAndUppercaseSymbols() {
        given().
                param("options", 512).
                param("text", "москва").
                when().get("https://speller.yandex.net/services/spellservice/checkText")
                .then()
                .statusCode(200);
    }



}
