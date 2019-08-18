package github.scrat98.api.expanders;

import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableExpander implements Param.Expander {
  @Override
  public String expand(final Object value) {
    if (!(value instanceof Pageable)) {
      throw new IllegalStateException("Error while expanding Pageable");
    }

    Pageable pageable = (Pageable) value;
    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();
    Sort sort = pageable.getSort();

    String res = "";
    res += String.format("page=%d&", page);
    res += String.format("size=%d&", size);
    if (sort != null) {
      String sortStr = sort
          .stream()
          .map((order) ->
              "sort=" +
                  order.getProperty() + "," +
                  order.getDirection().toString().toLowerCase() + "&")
          .reduce("", String::concat);
      res += sortStr;
    }

    return res;
  }
}
