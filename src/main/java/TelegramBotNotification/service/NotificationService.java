package TelegramBotNotification.service;

import TelegramBotNotification.model.NotificationTask;
import TelegramBotNotification.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class NotificationService {
    @Autowired
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public List<NotificationTask> findRemindersByDateTime(LocalDateTime localDateTime){
        return notificationTaskRepository.findRemindersByDateTime(localDateTime);
    }
    public NotificationTask save (NotificationTask notificationTask){
        return notificationTaskRepository.save(notificationTask);

    }
    public void delete (NotificationTask notificationTask){
        notificationTaskRepository.delete(notificationTask);
    }
    public SendMessage sendMessage (long chatId, String text){
        return  new SendMessage(chatId, text);
    }
}
