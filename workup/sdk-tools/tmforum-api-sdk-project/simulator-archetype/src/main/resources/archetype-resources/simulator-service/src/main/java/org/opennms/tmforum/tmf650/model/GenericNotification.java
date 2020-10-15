#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.opennms.tmforum.tmf650.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The notification data structure
 */
@ApiModel(description = "The notification data structure")
public class GenericNotification {
    @JsonProperty("eventId")
    private String eventId = null;

    @JsonProperty("eventTime")
    private OffsetDateTime eventTime = null;

    @JsonProperty("eventType")
    private String eventType = null;

    @JsonProperty("fieldPath")
    private String fieldPath = null;

    @JsonProperty("resourcePath")
    private String resourcePath = null;

    @JsonProperty("event")
    private JsonNode event = null;

    /**
     * The identifier of the notification
     * 
     * @return eventId
     **/
    @JsonProperty("eventId")
    @ApiModelProperty(value = "The identifier of the notification")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Time of the event occurrence
     * 
     * @return eventTime
     **/
    @JsonProperty("eventTime")
    @ApiModelProperty(value = "Time of the event occurrence")
    public OffsetDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(OffsetDateTime eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * The type of the notification
     * 
     * @return eventType
     **/
    @JsonProperty("eventType")
    @ApiModelProperty(value = "The type of the notification")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * The path identifying the object field concerned by this notification
     * 
     * @return fieldPath
     **/
    @JsonProperty("fieldPath")
    @ApiModelProperty(value = "The path identifying the object field concerned by this notification")
    public String getFieldPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    /**
     * The path identifying the resource object concerned by this notification
     * 
     * @return resourcePath
     **/
    @JsonProperty("resourcePath")
    @ApiModelProperty(value = "The path identifying the resource object concerned by this notification")
    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * The event linked to the involved resource object
     * 
     * @return event
     **/
    @JsonProperty("event")
    @ApiModelProperty(value = "The event linked to the involved resource object")
    public JsonNode getEvent() {
        return event;
    }

    public void setEvent(JsonNode event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, eventId, eventTime, eventType, fieldPath, resourcePath);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenericNotification other = (GenericNotification) obj;
        return Objects.equals(event, other.event) && Objects.equals(eventId, other.eventId)
                && Objects.equals(eventTime, other.eventTime) && Objects.equals(eventType, other.eventType)
                && Objects.equals(fieldPath, other.fieldPath) && Objects.equals(resourcePath, other.resourcePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GenericNotification {${symbol_escape}n");

        sb.append("    eventId: ").append(toIndentedString(eventId)).append("${symbol_escape}n");
        sb.append("    eventTime: ").append(toIndentedString(eventTime)).append("${symbol_escape}n");
        sb.append("    eventType: ").append(toIndentedString(eventType)).append("${symbol_escape}n");
        sb.append("    fieldPath: ").append(toIndentedString(fieldPath)).append("${symbol_escape}n");
        sb.append("    resourcePath: ").append(toIndentedString(resourcePath)).append("${symbol_escape}n");
        sb.append("    event: ").append(toIndentedString(event)).append("${symbol_escape}n");
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
        return o.toString().replace("${symbol_escape}n", "${symbol_escape}n    ");
    }
}
