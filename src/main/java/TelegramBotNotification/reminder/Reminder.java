package TelegramBotNotification.reminder;

import TelegramBotNotification.model.NotificationTask;
import TelegramBotNotification.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Reminder {


    private TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public Reminder(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }


    public void processReminderMessage(String message) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W]+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String dateTimeStr = matcher.group(1);
            String reminderText = matcher.group(3);

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            NotificationTask reminder = new NotificationTask(dateTime, reminderText);
            notificationTaskRepository.save(reminder);
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkRemindersInCurrentMinute() {
        LocalDateTime currentMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (notificationTaskRepository != null) {
            List<NotificationTask> reminders = notificationTaskRepository.findRemindersByDateTime(currentMinute);
            if (notificationTaskRepository != null) {

                for (NotificationTask reminder : reminders) {
                    System.out.println("Напомнить: " + reminder.getMessageText());
                }
            } else {
                System.out.println("Нет напоминаний в текущую минуту.");
            }
        }
    }
}

