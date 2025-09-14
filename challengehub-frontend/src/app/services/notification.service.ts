import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private baseUrl = 'http://localhost:8080/api/notifications';

  constructor(private http: HttpClient) {}

  // Fetch all notifications for a given user
  getNotifications(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${userId}`);
  }

  // Mark a single notification as read
  markAsRead(notificationId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/mark-read/${notificationId}`, {});
  }
}
