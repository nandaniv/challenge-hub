package org.nandani.challengehub.controller;

import org.nandani.challengehub.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new Notification(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("message"),
                rs.getBoolean("is_read"),
                rs.getTimestamp("created_at").toLocalDateTime()
        ));
    }

    @PostMapping("/mark-read/{id}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        String sql = "UPDATE notifications SET read = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
