package com.example.odyssey.mappers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.notifications.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public NotificationDTOMapper(ModelMapper mapper) {
        NotificationDTOMapper.mapper = mapper;
    }

    public static Notification fromDTOtoNotification(NotificationDTO dto) {
        return mapper.map(dto, Notification.class);
    }

    public static NotificationDTO fromNotificationToDTO(Notification notification) {
        return new NotificationDTO(notification);
    }
}
