package TelegramBotNotification.service;

import TelegramBotNotification.model.NotificationTask;
import TelegramBotNotification.repository.NotificationTaskRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.List;
@Component
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskRepository taskRepository;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<com.pengrad.telegrambot.model.Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message() != null && update.message().text() != null && update.message().text().equals("/start")) {
                long chatId = update.message().chat().id();
                telegramBot.execute(new SendMessage(chatId, "Привет! Я бот-напоминалка :), рад приветствовать тебя!"));
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
