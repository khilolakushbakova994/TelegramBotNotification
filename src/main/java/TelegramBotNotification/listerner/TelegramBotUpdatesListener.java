package TelegramBotNotification.listerner;

import TelegramBotNotification.model.NotificationTask;
import TelegramBotNotification.service.NotificationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    private final TelegramBot telegramBot;

    @Autowired
    private final NotificationService notificationService;

    private final Pattern PATTERN = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W]+)");

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<com.pengrad.telegrambot.model.Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            String text = update.message().text();
            String chatId = update.message().chat().id().toString();
            Matcher matcher = PATTERN.matcher(text);

            if (update.message() != null && update.message().text() != null && update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(chatId, "Привет! Я бот-напоминалка :), рад приветствовать тебя!"
                        + update.message().chat().username()
                        + "Шаблон для наисания напоминалки :  01.01.2024  15:45  Визит к врачу "));

            } else if (matcher.matches()) {
                String reminderText = matcher.group(3);
                LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                NotificationTask reminder = new NotificationTask();
                reminder.setChatId(chatId);
                reminder.setMessageText(reminderText);
                reminder.setDateTime(dateTime);
                notificationService.save(reminder);

                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Напоминание успешно добавлено!"));
                if (!response.isOk()) {
                    log.error("Error during sending massage: {}", response.description());
                }

            } else {
                telegramBot.execute(new SendMessage(chatId, "Напоминание не было добавлено!"
                        + "  \"Шаблон для наисания напоминалки :  01.01.2024  15:45  Визит к врачу \""));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}

