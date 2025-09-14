package org.nandani.challengehub.repository;

import org.nandani.challengehub.model.Challenge;
import org.nandani.challengehub.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChallengeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Challenge> rowMapper = (rs, rowNum) -> {
        Challenge ch = new Challenge();
        ch.setChallengeId(rs.getInt("challenge_id"));
        ch.setName(rs.getString("name"));
        ch.setDescription(rs.getString("description"));
        ch.setActiveFlag(rs.getBoolean("active_flag"));
        ch.setAddedZ(rs.getTimestamp("added_z").toLocalDateTime());
        ch.setModifiedZ(rs.getTimestamp("modified_z").toLocalDateTime());
        return ch;
    };

    public List<Challenge> getAllChallenges() {
        String sql = "SELECT * FROM challenges";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Task> getTasksForChallenge(int challengeId) {
        String sql = """
            SELECT t.task_id, t.name, t.description, t.active_flag, t.added_z, t.modified_z
            FROM challenge_tasks ct
            JOIN tasks t ON ct.task_id = t.task_id
            WHERE ct.challenge_id = ?
        """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Task.class), challengeId);
    }
}
