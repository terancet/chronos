package com.syngenta.digital.lab.kyiv.chronos.service.validation.reporting;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.reporting.ReportingRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.ApplicationBaseException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ReportParameterValidationService {
    private static final int ERROR_CODE = 17;

    public void validate(ReportingRequest reportingRequest) {
        List<Long> userIds = reportingRequest.getUserIds();
        if (CollectionUtils.isEmpty(userIds)) {
            throw new ApplicationBaseException(ERROR_CODE, "The list of the user id cannot be empty for the report");
        }
        if (reportingRequest.getRange() == null) {
            throw new ApplicationBaseException(ERROR_CODE, "Both start and end date must be present to generate report");
        }
        if (reportingRequest.getRange().getStart() == null) {
            throw new ApplicationBaseException(ERROR_CODE, "Start  date must be present to generate report");
        }
        if (reportingRequest.getRange().getEnd() == null) {
            throw new ApplicationBaseException(ERROR_CODE, "End  date must be present to generate report");
        }
        if(reportingRequest.getRange().getStart().isAfter(reportingRequest.getRange().getEnd())) {
            throw new ApplicationBaseException(ERROR_CODE, "End date must be after start date to generate report");
        }
    }
}
