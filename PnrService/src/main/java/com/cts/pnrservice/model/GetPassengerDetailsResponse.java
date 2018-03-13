package com.cts.pnrservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.cts.pnrservice.model.PassengerInfo;
import javax.validation.Valid;

/**
 * GetPassengerDetailsResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-02-02T07:00:42.313Z")

public class GetPassengerDetailsResponse   {
  @JsonProperty("PassengerInfo")
  private PassengerInfo passengerInfo = null;

  public GetPassengerDetailsResponse passengerInfo(PassengerInfo passengerInfo) {
    this.passengerInfo = passengerInfo;
    return this;
  }

   /**
   * Get passengerInfo
   * @return passengerInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public PassengerInfo getPassengerInfo() {
    return passengerInfo;
  }

  public void setPassengerInfo(PassengerInfo passengerInfo) {
    this.passengerInfo = passengerInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetPassengerDetailsResponse getPassengerDetailsResponse = (GetPassengerDetailsResponse) o;
    return Objects.equals(this.passengerInfo, getPassengerDetailsResponse.passengerInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(passengerInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetPassengerDetailsResponse {\n");
    
    sb.append("    passengerInfo: ").append(toIndentedString(passengerInfo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

