package TelegramBotNotification.repository;

import TelegramBotNotification.model.NotificationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
}
