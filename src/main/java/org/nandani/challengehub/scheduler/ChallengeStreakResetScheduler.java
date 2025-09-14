package org.nandani.challengehub.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChallengeStreakResetScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ChallengeStreakResetScheduler.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Runs every day at 11:55 PM
     */
    @Scheduled(cron = "0 55 23 * * *") // every day at 11:55 PM
    public void resetStreaksForInactiveUsers() {
        logger.info("Running streak reset job...");

        // Reset current_streak to 0 where no task was completed today
        String updateSql = """
        UPDATE users_challenge uc
        SET current_streak = 0
        WHERE NOT EXISTS (
            SELECT 1 FROM user_task_progress utp
            WHERE utp.user_id = uc.user_id
              AND utp.challenge_id = uc.challenge_id
              AND utp.progress_date = CURRENT_DATE
              AND utp.completed = true
        )
    """;
        jdbcTemplate.update(updateSql);

        // Insert notifications for affected users
        String notifySql = """
        INSERT INTO notifications (user_id, message)
        SELECT uc.user_id,
               'Your streak for challenge "' || c.name || '" was reset due to no activity today.'
        FROM users_challenge uc
        JOIN challenges c ON uc.challenge_id = c.challenge_id
        WHERE NOT EXISTS (
            SELECT 1 FROM user_task_progress utp
            WHERE utp.user_id = uc.user_id
              AND utp.challenge_id = uc.challenge_id
              AND utp.progress_date = CURRENT_DATE
              AND utp.completed = true
        )
    """;
        jdbcTemplate.update(notifySql);
    }

}

