package TelegramBotNotification.service;

import TelegramBotNotification.model.Reminder;
import TelegramBotNotification.repository.ReminderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class ReminderService {
    private ReminderRepository reminderRepository;

    public void processReminderMessage(String message) {
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W]+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String dateTimeStr = matcher.group(1);
            String reminderText = matcher.group(3);

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            Reminder reminder = new Reminder(dateTime, reminderText);
            reminderRepository.save(reminder);
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkRemindersInCurrentMinute() {
        LocalDateTime currentMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Reminder> reminders = reminderRepository.findRemindersByDateTime(currentMinute);

        for (Reminder reminder : reminders) {
            System.out.println("Напомнить: " + reminder.getReminderText());

        }
    }
}
