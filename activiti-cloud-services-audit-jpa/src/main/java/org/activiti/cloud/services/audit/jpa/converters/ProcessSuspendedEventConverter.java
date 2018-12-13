package org.activiti.cloud.services.audit.jpa.converters;

import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.cloud.api.model.shared.events.CloudRuntimeEvent;
import org.activiti.cloud.api.process.model.events.CloudProcessSuspendedEvent;
import org.activiti.cloud.api.process.model.impl.events.CloudProcessSuspendedEventImpl;
import org.activiti.cloud.services.audit.api.converters.EventToEntityConverter;
import org.activiti.cloud.services.audit.jpa.events.AuditEventEntity;
import org.activiti.cloud.services.audit.jpa.events.ProcessSuspendedAuditEventEntity;
import org.springframework.stereotype.Component;

@Component
public class ProcessSuspendedEventConverter implements EventToEntityConverter<AuditEventEntity> {

    @Override
    public String getSupportedEvent() {
        return ProcessRuntimeEvent.ProcessEvents.PROCESS_SUSPENDED.name();
    }

    @Override
    public AuditEventEntity convertToEntity(CloudRuntimeEvent cloudRuntimeEvent) {
        CloudProcessSuspendedEvent cloudProcessSuspended = (CloudProcessSuspendedEvent) cloudRuntimeEvent;
                 
        ProcessSuspendedAuditEventEntity eventEntity = new ProcessSuspendedAuditEventEntity(cloudProcessSuspended.getId(),
                                                                                            cloudProcessSuspended.getTimestamp(),
                                                                                            cloudProcessSuspended.getAppName(),
                                                                                            cloudProcessSuspended.getAppVersion(),
                                                                                            cloudProcessSuspended.getServiceName(),
                                                                                            cloudProcessSuspended.getServiceFullName(),
                                                                                            cloudProcessSuspended.getServiceType(),
                                                                                            cloudProcessSuspended.getServiceVersion(),
                                                                                            cloudProcessSuspended.getEntity());
        eventEntity.setBaseProcessData(cloudProcessSuspended.getEntityId(),
                                       cloudProcessSuspended.getProcessInstanceId(),
                                       cloudProcessSuspended.getProcessDefinitionId(),
                                       cloudProcessSuspended.getProcessDefinitionKey(),
                                       cloudProcessSuspended.getBusinessKey(),
                                       cloudProcessSuspended.getParentProcessInstanceId());
        

        return eventEntity;
    }

    @Override
    public CloudRuntimeEvent convertToAPI(AuditEventEntity auditEventEntity) {
        ProcessSuspendedAuditEventEntity processSuspendedAuditEventEntity = (ProcessSuspendedAuditEventEntity) auditEventEntity;
        CloudProcessSuspendedEventImpl cloudProcessSuspendedEvent = new CloudProcessSuspendedEventImpl(processSuspendedAuditEventEntity.getEventId(),
                                                                                                       processSuspendedAuditEventEntity.getTimestamp(),
                                                                                                       processSuspendedAuditEventEntity.getProcessInstance());
        cloudProcessSuspendedEvent.setAppName(processSuspendedAuditEventEntity.getAppName());
        cloudProcessSuspendedEvent.setAppVersion(processSuspendedAuditEventEntity.getAppVersion());
        cloudProcessSuspendedEvent.setServiceFullName(processSuspendedAuditEventEntity.getServiceFullName());
        cloudProcessSuspendedEvent.setServiceName(processSuspendedAuditEventEntity.getServiceName());
        cloudProcessSuspendedEvent.setServiceType(processSuspendedAuditEventEntity.getServiceType());
        cloudProcessSuspendedEvent.setServiceVersion(processSuspendedAuditEventEntity.getServiceVersion());

        return cloudProcessSuspendedEvent;
    }
}
