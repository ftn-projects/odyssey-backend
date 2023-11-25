package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserReportDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public UserReportDTOMapper(ModelMapper mapper) {
        UserReportDTOMapper.mapper = mapper;
    }

    public static UserReport fromDTOtoUserReport(UserReportDTO dto) {
        return mapper.map(dto, UserReport.class);
    }

    public static UserReportDTO fromUserReportToDTO(UserReport model) {
        return mapper.map(model, UserReportDTO.class);
    }
}
