package com.example.odyssey.dtos.notifications;

import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String text;
    private User receiver;

    public NotificationDTO(Notification notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.receiver = notification.getReceiver();
    }
}
