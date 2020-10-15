
package org.opennms.tmforum.tmf656.simulator.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Convert;

import org.opennms.tmforum.jpa.json.ObjectConverter;
import org.opennms.tmforum.jpa.json.ObjectListConverter;
import org.opennms.tmforum.jpa.tmf656.json.ServiceProblemRefListConverter;
import org.opennms.tmforum.swagger.tmf656.swagger.model.Characteristic;
import org.opennms.tmforum.swagger.tmf656.swagger.model.EventRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ImpactPattern;
import org.opennms.tmforum.swagger.tmf656.swagger.model.Note;
import org.opennms.tmforum.swagger.tmf656.swagger.model.Place;
import org.opennms.tmforum.swagger.tmf656.swagger.model.RelatedEntityRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.RelatedParty;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ResourceAlarmRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ResourceRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.SLAViolationRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceProblemRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.ServiceRef;
import org.opennms.tmforum.swagger.tmf656.swagger.model.TrackingRecord;
import org.opennms.tmforum.swagger.tmf656.swagger.model.TroubleTicketRef;

@Entity
public class ServiceProblemEntity {


    private Long id = null;

    private String href = null;

    private Integer affectedNumberOfServices = null;

    private String category = null;

    private String correlationId = null;

    private String description = null;

    private String impactImportanceFactor = null;

    private String originatingSystem = null;

    private Integer priority = null;

    private String problemEscalation = null;

    private String reason = null;

    private OffsetDateTime resolutionDate = null;

    private String status = null;

    private OffsetDateTime statusChangeDate = null;

    private String statusChangeReason = null;

    private OffsetDateTime timeChanged = null;

    private OffsetDateTime timeRaised = null;

    private List<Place> affectedLocation = null;

    private List<ResourceRef> affectedResource = null;

    private List<ServiceRef> affectedService = null;

    private List<SLAViolationRef> associatedSLAViolation = null;

    private List<TroubleTicketRef> associatedTroubleTicket = null;

    private List<Note> comment = null;

    private List<Characteristic> extensionInfo = null;

    private RelatedEntityRef firstAlert = null;

    private ImpactPattern impactPatterns = null;

    private RelatedParty originatorParty = null;

    private List<ServiceProblemRef> parentProblem = null;

    private List<EventRef> relatedEvent = null;

    private List<RelatedEntityRef> relatedObject = null;

    private List<RelatedParty> relatedParty = null;

    private RelatedParty responsibleParty = null;

    private List<ResourceRef> rootCauseResource = null;

    private List<ServiceRef> rootCauseService = null;

    private List<TrackingRecord> trackingRecord = null;

    private List<ResourceAlarmRef> underlyingAlarm = null;

    private List<ServiceProblemRef> underlyingProblem = null;

    private String baseType = null;

    private String schemaLocation = null;

    private String type = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getAffectedNumberOfServices() {
        return affectedNumberOfServices;
    }

    public void setAffectedNumberOfServices(Integer affectedNumberOfServices) {
        this.affectedNumberOfServices = affectedNumberOfServices;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImpactImportanceFactor() {
        return impactImportanceFactor;
    }

    public void setImpactImportanceFactor(String impactImportanceFactor) {
        this.impactImportanceFactor = impactImportanceFactor;
    }

    public String getOriginatingSystem() {
        return originatingSystem;
    }

    public void setOriginatingSystem(String originatingSystem) {
        this.originatingSystem = originatingSystem;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getProblemEscalation() {
        return problemEscalation;
    }

    public void setProblemEscalation(String problemEscalation) {
        this.problemEscalation = problemEscalation;
    }

    @Column(length = 1000)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public OffsetDateTime getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(OffsetDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(OffsetDateTime statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    @Column(length = 1000)
    public String getStatusChangeReason() {
        return statusChangeReason;
    }

    public void setStatusChangeReason(String statusChangeReason) {
        this.statusChangeReason = statusChangeReason;
    }

    public OffsetDateTime getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(OffsetDateTime timeChanged) {
        this.timeChanged = timeChanged;
    }

    public OffsetDateTime getTimeRaised() {
        return timeRaised;
    }

    public void setTimeRaised(OffsetDateTime timeRaised) {
        this.timeRaised = timeRaised;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<Place> getAffectedLocation() {
        return affectedLocation;
    }

    public void setAffectedLocation(List<Place> affectedLocation) {
        this.affectedLocation = affectedLocation;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<ResourceRef> getAffectedResource() {
        return affectedResource;
    }

    public void setAffectedResource(List<ResourceRef> affectedResource) {
        this.affectedResource = affectedResource;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<ServiceRef> getAffectedService() {
        return affectedService;
    }

    public void setAffectedService(List<ServiceRef> affectedService) {
        this.affectedService = affectedService;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<SLAViolationRef> getAssociatedSLAViolation() {
        return associatedSLAViolation;
    }

    public void setAssociatedSLAViolation(List<SLAViolationRef> associatedSLAViolation) {
        this.associatedSLAViolation = associatedSLAViolation;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<TroubleTicketRef> getAssociatedTroubleTicket() {
        return associatedTroubleTicket;
    }

    public void setAssociatedTroubleTicket(List<TroubleTicketRef> associatedTroubleTicket) {
        this.associatedTroubleTicket = associatedTroubleTicket;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<Note> getComment() {
        return comment;
    }

    public void setComment(List<Note> comment) {
        this.comment = comment;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<Characteristic> getExtensionInfo() {
        return extensionInfo;
    }

    public void setExtensionInfo(List<Characteristic> extensionInfo) {
        this.extensionInfo = extensionInfo;
    }

    @Convert(converter = ObjectConverter.class)
    @Column(length = 1000)
    public RelatedEntityRef getFirstAlert() {
        return firstAlert;
    }

    public void setFirstAlert(RelatedEntityRef firstAlert) {
        this.firstAlert = firstAlert;
    }

    @Convert(converter = ObjectConverter.class)
    @Column(length = 1000)
    public ImpactPattern getImpactPatterns() {
        return impactPatterns;
    }

    public void setImpactPatterns(ImpactPattern impactPatterns) {
        this.impactPatterns = impactPatterns;
    }

    @Convert(converter = ObjectConverter.class)
    @Column(length = 1000)
    public RelatedParty getOriginatorParty() {
        return originatorParty;
    }

    public void setOriginatorParty(RelatedParty originatorParty) {
        this.originatorParty = originatorParty;
    }

    @Convert(converter = ServiceProblemRefListConverter.class)
    @Column(length = 1000)
    public List<ServiceProblemRef> getParentProblem() {
        return parentProblem;
    }

    public void setParentProblem(List<ServiceProblemRef> parentProblem) {
        this.parentProblem = parentProblem;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<EventRef> getRelatedEvent() {
        return relatedEvent;
    }

    public void setRelatedEvent(List<EventRef> relatedEvent) {
        this.relatedEvent = relatedEvent;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<RelatedEntityRef> getRelatedObject() {
        return relatedObject;
    }

    public void setRelatedObject(List<RelatedEntityRef> relatedObject) {
        this.relatedObject = relatedObject;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<RelatedParty> getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
    }

    @Convert(converter = ObjectConverter.class)
    @Column(length = 1000)
    public RelatedParty getResponsibleParty() {
        return responsibleParty;
    }

    public void setResponsibleParty(RelatedParty responsibleParty) {
        this.responsibleParty = responsibleParty;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<ResourceRef> getRootCauseResource() {
        return rootCauseResource;
    }

    public void setRootCauseResource(List<ResourceRef> rootCauseResource) {
        this.rootCauseResource = rootCauseResource;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<ServiceRef> getRootCauseService() {
        return rootCauseService;
    }

    public void setRootCauseService(List<ServiceRef> rootCauseService) {
        this.rootCauseService = rootCauseService;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<TrackingRecord> getTrackingRecord() {
        return trackingRecord;
    }

    public void setTrackingRecord(List<TrackingRecord> trackingRecord) {
        this.trackingRecord = trackingRecord;
    }

    @Convert(converter = ObjectListConverter.class)
    @Column(length = 1000)
    public List<ResourceAlarmRef> getUnderlyingAlarm() {
        return underlyingAlarm;
    }

    public void setUnderlyingAlarm(List<ResourceAlarmRef> underlyingAlarm) {
        this.underlyingAlarm = underlyingAlarm;
    }

    @Convert(converter = ServiceProblemRefListConverter.class)
    @Column(length = 1000)
    public List<ServiceProblemRef> getUnderlyingProblem() {
        return underlyingProblem;
    }

    public void setUnderlyingProblem(List<ServiceProblemRef> underlyingProblem) {
        this.underlyingProblem = underlyingProblem;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(affectedLocation, affectedNumberOfServices, affectedResource, affectedService,
                associatedSLAViolation, associatedTroubleTicket, baseType, category, comment, correlationId,
                description, extensionInfo, firstAlert, href, id, impactImportanceFactor, impactPatterns,
                originatingSystem, originatorParty, parentProblem, priority, problemEscalation, reason, relatedEvent,
                relatedObject, relatedParty, resolutionDate, responsibleParty, rootCauseResource, rootCauseService,
                schemaLocation, status, statusChangeDate, statusChangeReason, timeChanged, timeRaised, trackingRecord,
                type, underlyingAlarm, underlyingProblem);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceProblemEntity other = (ServiceProblemEntity) obj;
        return Objects.equals(affectedLocation, other.affectedLocation)
                && Objects.equals(affectedNumberOfServices, other.affectedNumberOfServices)
                && Objects.equals(affectedResource, other.affectedResource)
                && Objects.equals(affectedService, other.affectedService)
                && Objects.equals(associatedSLAViolation, other.associatedSLAViolation)
                && Objects.equals(associatedTroubleTicket, other.associatedTroubleTicket)
                && Objects.equals(baseType, other.baseType) && Objects.equals(category, other.category)
                && Objects.equals(comment, other.comment) && Objects.equals(correlationId, other.correlationId)
                && Objects.equals(description, other.description) && Objects.equals(extensionInfo, other.extensionInfo)
                && Objects.equals(firstAlert, other.firstAlert) && Objects.equals(href, other.href)
                && Objects.equals(id, other.id) && Objects.equals(impactImportanceFactor, other.impactImportanceFactor)
                && Objects.equals(impactPatterns, other.impactPatterns)
                && Objects.equals(originatingSystem, other.originatingSystem)
                && Objects.equals(originatorParty, other.originatorParty)
                && Objects.equals(parentProblem, other.parentProblem) && Objects.equals(priority, other.priority)
                && Objects.equals(problemEscalation, other.problemEscalation) && Objects.equals(reason, other.reason)
                && Objects.equals(relatedEvent, other.relatedEvent)
                && Objects.equals(relatedObject, other.relatedObject)
                && Objects.equals(relatedParty, other.relatedParty)
                && Objects.equals(resolutionDate, other.resolutionDate)
                && Objects.equals(responsibleParty, other.responsibleParty)
                && Objects.equals(rootCauseResource, other.rootCauseResource)
                && Objects.equals(rootCauseService, other.rootCauseService)
                && Objects.equals(schemaLocation, other.schemaLocation) && Objects.equals(status, other.status)
                && Objects.equals(statusChangeDate, other.statusChangeDate)
                && Objects.equals(statusChangeReason, other.statusChangeReason)
                && Objects.equals(timeChanged, other.timeChanged) && Objects.equals(timeRaised, other.timeRaised)
                && Objects.equals(trackingRecord, other.trackingRecord) && Objects.equals(type, other.type)
                && Objects.equals(underlyingAlarm, other.underlyingAlarm)
                && Objects.equals(underlyingProblem, other.underlyingProblem);
    }

    @Override
    public String toString() {
        return "ServiceProblemEntity [id=" + id + ", href=" + href + ", affectedNumberOfServices="
                + affectedNumberOfServices + ", category=" + category + ", correlationId=" + correlationId
                + ", description=" + description + ", impactImportanceFactor=" + impactImportanceFactor
                + ", originatingSystem=" + originatingSystem + ", priority=" + priority + ", problemEscalation="
                + problemEscalation + ", reason=" + reason + ", resolutionDate=" + resolutionDate + ", status=" + status
                + ", statusChangeDate=" + statusChangeDate + ", statusChangeReason=" + statusChangeReason
                + ", timeChanged=" + timeChanged + ", timeRaised=" + timeRaised + ", affectedLocation="
                + affectedLocation + ", affectedResource=" + affectedResource + ", affectedService=" + affectedService
                + ", associatedSLAViolation=" + associatedSLAViolation + ", associatedTroubleTicket="
                + associatedTroubleTicket + ", comment=" + comment + ", extensionInfo=" + extensionInfo
                + ", firstAlert=" + firstAlert + ", impactPatterns=" + impactPatterns + ", originatorParty="
                + originatorParty + ", parentProblem=" + parentProblem + ", relatedEvent=" + relatedEvent
                + ", relatedObject=" + relatedObject + ", relatedParty=" + relatedParty + ", responsibleParty="
                + responsibleParty + ", rootCauseResource=" + rootCauseResource + ", rootCauseService="
                + rootCauseService + ", trackingRecord=" + trackingRecord + ", underlyingAlarm=" + underlyingAlarm
                + ", underlyingProblem=" + underlyingProblem + ", baseType=" + baseType + ", schemaLocation="
                + schemaLocation + ", type=" + type + "]";
    }



}
