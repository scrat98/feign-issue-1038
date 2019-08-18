package github.scrat98;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.Request.HttpMethod;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import github.scrat98.api.WebControllerApi;
import github.scrat98.api.dto.SearchRequestDto;
import github.scrat98.api.dto.SearchResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class FeignApiTest {

  @LocalServerPort
  private int port;

  private ObjectMapper jacksonObjectMapper = new ObjectMapper().findAndRegisterModules();

  private WebControllerApi api() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder(jacksonObjectMapper))
        .decoder(new JacksonDecoder(jacksonObjectMapper))
        .target(WebControllerApi.class, "http://localhost:" + port);
  }

  private WebControllerApi workaroundApi() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder((Object object, Type bodyType, RequestTemplate template) -> {
          JacksonEncoder jacksonEncoder = new JacksonEncoder(jacksonObjectMapper);
          HttpMethod method = HttpMethod.valueOf(template.method());
          // as workaround I use enhanced jackson encoder, because in RequestTemplate
          // there were a body parameters in spite of the GET method
          if (method != HttpMethod.GET) {
            jacksonEncoder.encode(object, bodyType, template);
          }
        })
        .decoder(new JacksonDecoder(jacksonObjectMapper))
        .target(WebControllerApi.class, "http://localhost:" + port);
  }

  void apiWorks(WebControllerApi api) {
    // given
    SearchRequestDto searchRequest = new SearchRequestDto("feign", "10.3.0");
    Pageable pageable = PageRequest.of(0, 20, Sort.by(
        new Sort.Order(Direction.DESC, "name"),
        new Sort.Order(Direction.ASC, "version")
    ));
    SearchResponseDto expectedResult = new SearchResponseDto(searchRequest, pageable);
    // when
    SearchResponseDto actualResult = api.search(searchRequest, pageable);
    // then
    Assertions.assertEquals(actualResult, expectedResult);
  }

  @TestFactory
  Collection<DynamicTest> testsFeignApi() {
    return Arrays.asList(
        DynamicTest.dynamicTest("test with normal feign api",
            () -> apiWorks(api())),
        DynamicTest.dynamicTest("test with workaround feign api",
            () -> apiWorks(workaroundApi()))
    );
  }
}
