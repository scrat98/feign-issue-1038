package github.scrat98.api.expanders;

import feign.Param;
import github.scrat98.api.dto.SearchRequestDto;

public class SearchRequestDtoExpander implements Param.Expander {
  @Override
  public String expand(final Object value) {
    if (!(value instanceof SearchRequestDto)) {
      throw new IllegalStateException("Error while expanding SearchRequestDto");
    }

    SearchRequestDto searchRequestDto = (SearchRequestDto) value;
    String res = "";
    if (searchRequestDto.name != null) {
      res += "name=" + searchRequestDto.name + "&";
    }

    if (searchRequestDto.version != null) {
      res += "version=" + searchRequestDto.version;
    }

    return res;
  }
}