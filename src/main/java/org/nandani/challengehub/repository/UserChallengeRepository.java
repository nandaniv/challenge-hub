package org.nandani.challengehub.repository;

import org.nandani.challengehub.dto.UserChallengeResponse;
import org.nandani.challengehub.dto.UserTaskProgressRequest;
import org.nandani.challengehub.model.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserChallengeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Challenge> findChallengesByUserId(Long userId) {
        String sql = """
            SELECT c.challenge_id, c.name, c.description
            FROM challenges c
            JOIN users_challenge uc ON c.challenge_id = uc.challenge_id
            WHERE uc.user_id = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Challenge challenge = new Challenge();
            challenge.setChallengeId(rs.getInt("challenge_id"));
            challenge.setName(rs.getString("name"));
            challenge.setDescription(rs.getString("description"));
            return challenge;
        });
    }

    public boolean insertUserTaskProgress(UserTaskProgressRequest req) {
        String insertSql = """
        INSERT INTO user_task_progress (user_id, challenge_id, task_id, progress_date, completed)
        VALUES (?, ?, ?, CURRENT_DATE, ?)
    """;

        try {
            jdbcTemplate.update(insertSql,
                    req.getUserId(),
                    req.getChallengeId(),
                    req.getTaskId(),
                    req.getCompleted() != null ? req.getCompleted() : false);

            // Check if all tasks for the challenge are completed today
            String totalTasksSql = """
            SELECT COUNT(*) FROM challenge_tasks WHERE challenge_id = ?
        """;
            Integer totalTasks = jdbcTemplate.queryForObject(totalTasksSql, Integer.class, req.getChallengeId());

            String completedTasksSql = """
            SELECT COUNT(*) FROM user_task_progress
            WHERE user_id = ? AND challenge_id = ? AND progress_date = CURRENT_DATE AND completed = true
        """;
            Integer completedTasks = jdbcTemplate.queryForObject(completedTasksSql, Integer.class,
                    req.getUserId(), req.getChallengeId());

            if (totalTasks != null && completedTasks != null && totalTasks.equals(completedTasks)) {
                // All tasks completed - update current_streak
                String updateStreakSql = """
                    UPDATE users_challenge
                    SET current_streak = CASE
                            WHEN last_completed_date = DATE_SUB(CURDATE(), INTERVAL 1 DAY) THEN current_streak + 1
                            ELSE 1
                         END,
                    last_completed_date = CURDATE()
                 WHERE user_id = ? AND challenge_id = ?
""";

                jdbcTemplate.update(updateStreakSql, req.getUserId(), req.getChallengeId());
            }

            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }


    public List<UserChallengeResponse> getUserChallengesWithStreaks(Long userId) {
        String sql = """
        SELECT c.challenge_id,c.name AS title, uc.current_streak, uc.last_completed_date, uc.completed
        FROM users_challenge uc
        JOIN challenges c ON uc.challenge_id = c.challenge_id
        WHERE uc.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            String status = rs.getBoolean("completed") ? "Completed" : "In Progress";
            return new UserChallengeResponse(
                    rs.getLong("challenge_id"),
                    rs.getString("title"),
                    rs.getInt("current_streak"),
                    rs.getDate("last_completed_date"),
                    status
            );
        });
    }

    public List<Map<String, Object>> getAllTaskStatusesForToday(Long userId, Long challengeId) {
        String sql = """
        SELECT ct.task_id,
               COALESCE(utp.completed, false) AS completed
        FROM challenge_tasks ct
        LEFT JOIN user_task_progress utp
          ON ct.challenge_id = utp.challenge_id
         AND ct.task_id = utp.task_id
         AND utp.user_id = ?
         AND utp.progress_date = CURDATE()
        WHERE ct.challenge_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId, challengeId}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", rs.getLong("task_id"));
            map.put("completed", rs.getBoolean("completed"));
            return map;
        });
    }
    public void activateChallengeForUser(Long userId, Long challengeId) {
        String sql = """
        INSERT INTO users_challenge (user_id, challenge_id, start_date)
        VALUES (?, ?, CURRENT_DATE)
        ON DUPLICATE KEY UPDATE start_date = start_date
    """;
        jdbcTemplate.update(sql, userId, challengeId);
    }

    public List<UserChallengeResponse> getAllChallengesWithUserStatus(Long userId) {
        String sql = """
        SELECT 
            c.challenge_id,
            c.name AS title,
            COALESCE(uc.current_streak, 0) AS current_streak,
            uc.last_completed_date,
            CASE 
                WHEN uc.completed IS NOT NULL THEN uc.completed 
                ELSE FALSE 
            END AS completed,
            CASE 
                WHEN uc.challenge_id IS NOT NULL THEN 'In Progress'
                ELSE 'Not Activated'
            END AS status
        FROM challenges c
        LEFT JOIN users_challenge uc 
            ON c.challenge_id = uc.challenge_id AND uc.user_id = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new UserChallengeResponse(
                rs.getLong("challenge_id"),
                rs.getString("title"),
                rs.getInt("current_streak"),
                rs.getDate("last_completed_date"),
                rs.getString("status")
        ));
    }



}
