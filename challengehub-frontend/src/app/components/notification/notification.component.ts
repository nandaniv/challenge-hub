import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../services/notification.service';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css'],
  imports: [CommonModule],
  standalone: true
})
export class NotificationComponent implements OnInit {
  notifications: any[] = [];
  userId!: number;
  errorMessage: string | null = null;
  showDropdown = false;

  constructor(
    private notificationService: NotificationService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.auth.getUser();
    this.userId = user?.userId;
    if (this.userId) {
      this.fetchNotifications();
    }
  }

  fetchNotifications(): void {
    this.notificationService.getNotifications(this.userId).subscribe({
      next: (data) => (this.notifications = data),
      error: () => (this.errorMessage = 'Could not load notifications')
    });
  }

  markAsRead(notificationId: number): void {
    this.notificationService.markAsRead(notificationId).subscribe({
      next: () => {
        const notification = this.notifications.find(n => n.id === notificationId);
        if (notification) notification.is_read = true;
      }
    });
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }
get unreadCount(): number {
  return this.notifications.filter(n => !n.is_read).length;
}

get hasUnread(): boolean {
  return this.unreadCount > 0;
}

}
