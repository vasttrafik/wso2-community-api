package org.vasttrafik.wso2.carbon.community.api.beans;

import java.util.List;

import org.vasttrafik.wso2.carbon.common.api.beans.ErrorListItem;

public final class Error  {
  
  private String code = null;
  private String message = null;
  private String description = null;
  private String moreInfo = null;
  private List<ErrorListItem> items = null;

  /**
   * Error code
   **/
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Error message
   **/
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public String getMoreInfo() {
	return moreInfo;
  }

  public void setMoreInfo(String moreInfo) {
	this.moreInfo = moreInfo;
  }

  public List<ErrorListItem> getItems() {
	return items;
  }

  public void setItems(List<ErrorListItem> items) {
	this.items = items;
  }

@Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("  code: ").append(code).append("\n");
    sb.append("  message: ").append(message).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  moreInfo: ").append(moreInfo).append("\n");
    sb.append("  items: ").append(items).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
