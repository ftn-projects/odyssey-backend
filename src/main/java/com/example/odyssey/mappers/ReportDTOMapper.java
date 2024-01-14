package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.UserReport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReportDTOMapper(ModelMapper mapper) {
        ReportDTOMapper.mapper = mapper;
    }

    public static UserReport fromDTOtoUserReport(UserReportDTO dto) {
        return mapper.map(dto, UserReport.class);
    }
}
