package github.scrat98.api.dto;

import org.springframework.data.domain.Pageable;

public class SearchResponseDto {

  public SearchResponseDto() {}

  public SearchResponseDto(SearchRequestDto request, Pageable pageable) {
    this.name = request.name;
    this.version = request.version;
    this.page = pageable.getPageNumber();
    this.size = pageable.getPageSize();
    this.sort = pageable.getSort().toString();
  }

  public String name;

  public String version;

  public int page;

  public int size;

  public String sort;

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (this == obj) return true;
    if (obj.getClass() != obj.getClass()) return false;

    SearchResponseDto other = (SearchResponseDto) obj;

    if (!name.equals(other.name)) return false;
    if (!version.equals(other.version)) return false;
    if (page != other.page) return false;
    if (size != other.size) return false;
    if (!sort.equals(other.sort)) return false;

    return true;
  }
}
