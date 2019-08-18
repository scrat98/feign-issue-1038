package github.scrat98.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequestDto {

  public SearchRequestDto(String name, String version) {
    this.name = name;
    this.version = version;
  }

  @JsonProperty("name")
  public String name;

  @JsonProperty("version")
  public String version;

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (this == obj) return true;
    if (obj.getClass() != obj.getClass()) return false;

    SearchRequestDto other = (SearchRequestDto) obj;

    if (!name.equals(other.name)) return false;
    if (!version.equals(other.version)) return false;

    return true;
  }
}
