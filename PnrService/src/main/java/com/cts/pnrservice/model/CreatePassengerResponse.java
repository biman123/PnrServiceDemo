package com.cts.pnrservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.cts.pnrservice.model.AdvisoryMessage;
import javax.validation.Valid;

/**
 * CreatePassengerResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-02-02T07:00:42.313Z")

public class CreatePassengerResponse   {
  @JsonProperty("AdvisoryMessage")
  private AdvisoryMessage advisoryMessage = null;

  public CreatePassengerResponse advisoryMessage(AdvisoryMessage advisoryMessage) {
    this.advisoryMessage = advisoryMessage;
    return this;
  }

   /**
   * Get advisoryMessage
   * @return advisoryMessage
  **/
  @ApiModelProperty(value = "")

  @Valid

  public AdvisoryMessage getAdvisoryMessage() {
    return advisoryMessage;
  }

  public void setAdvisoryMessage(AdvisoryMessage advisoryMessage) {
    this.advisoryMessage = advisoryMessage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreatePassengerResponse createPassengerResponse = (CreatePassengerResponse) o;
    return Objects.equals(this.advisoryMessage, createPassengerResponse.advisoryMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(advisoryMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreatePassengerResponse {\n");
    
    sb.append("    advisoryMessage: ").append(toIndentedString(advisoryMessage)).append("\n");
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

