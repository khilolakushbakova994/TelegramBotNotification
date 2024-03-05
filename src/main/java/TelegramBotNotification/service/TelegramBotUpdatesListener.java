package TelegramBotNotification.service;

import TelegramBotNotification.model.NotificationTask;
import TelegramBotNotification.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;
@Component
@Service
@Slf4j
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskRepository taskRepository;

    @Override
    public int process(List<com.pengrad.telegrambot.model.Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            if (update.message() != null && update.message().text() != null && update.message().text().equals("/start")) {
                long chatId = update.message().chat().id();
                SendMessage message = new SendMessage(chatId, "Привет! Я бот-напоминалка :), рад приветствовать тебя!");
                telegramBot.execute(message);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendNotifications() {
        List<NotificationTask> tasks = taskRepository.findAll();
        for (NotificationTask task : tasks) {
            if (task != null ) {
                SendMessage message = new SendMessage(task.getChatId(), task.getMessageText());
                SendResponse response = telegramBot.execute(message);

            }
        }
    }

}
