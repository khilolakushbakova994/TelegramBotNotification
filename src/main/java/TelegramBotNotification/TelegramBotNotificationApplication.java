package TelegramBotNotification;


import TelegramBotNotification.service.TelegramBotUpdatesListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotNotificationApplication.class, args);
	}
	}

