package TelegramBotNotification.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification_task")


public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private String chatId;

    @Column(nullable = false)
    private String messageText;

    @Column(name = "notification_date_time", nullable = false)
    private LocalDateTime dateTime;


    public NotificationTask(LocalDateTime dateTime, String reminderText) {
    }
}
