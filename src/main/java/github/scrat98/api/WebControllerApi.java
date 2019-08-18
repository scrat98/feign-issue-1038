package github.scrat98.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import github.scrat98.api.dto.SearchRequestDto;
import github.scrat98.api.dto.SearchResponseDto;
import github.scrat98.api.expanders.PageableExpander;
import github.scrat98.api.expanders.SearchRequestDtoExpander;
import org.springframework.data.domain.Pageable;

@Headers({"Accept: application/json", "Content-Type: application/json"})
public interface WebControllerApi {

  @RequestLine("GET /api/1.0/search?{request}&{pageable}")
  SearchResponseDto search(
      @Param(value = "request", expander = SearchRequestDtoExpander.class) SearchRequestDto request,
      @Param(value = "pageable", expander = PageableExpander.class) Pageable pageable
  );
}