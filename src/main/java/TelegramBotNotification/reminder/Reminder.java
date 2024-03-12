package TelegramBotNotification.reminder;


import TelegramBotNotification.service.NotificationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Component
public class Reminder {


    private final TelegramBot telegramBot;
    private final NotificationService notificationService;

    public Reminder(TelegramBot telegramBot, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.telegramBot = telegramBot;
    }



    @Scheduled(cron = "0 0/1 * * * *")
    public void checkRemindersInCurrentMinute() {
        notificationService.findRemindersByDateTime(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .forEach(notificationTask -> {
                    if (notificationService != null) {
                        telegramBot.execute(new SendMessage(notificationTask.getChatId(),
                                notificationTask.getMessageText()));
                        notificationService.delete(notificationTask);
                    }
            });

        }
    }


