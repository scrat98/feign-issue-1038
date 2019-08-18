package github.scrat98.controllers;

import github.scrat98.api.WebControllerApi;
import github.scrat98.api.dto.SearchRequestDto;
import github.scrat98.api.dto.SearchResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0")
public class WebController implements WebControllerApi {

  @GetMapping("/search")
  public SearchResponseDto search(final SearchRequestDto request, final Pageable pageable) {
    return new SearchResponseDto(request, pageable);
  }
}
