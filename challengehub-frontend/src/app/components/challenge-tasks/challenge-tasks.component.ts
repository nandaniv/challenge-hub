import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-challenge-tasks',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './challenge-tasks.component.html',
  styleUrls: ['./challenge-tasks.component.css']
})
export class ChallengeTasksComponent implements OnInit {
  challengeId!: number;
  tasks: any[] = [];
  errorMessage: string | null = null;
  userId!: number;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const user = JSON.parse(localStorage.getItem('user') || '{}');
      this.userId = user?.userId;
    }

    if (!this.userId) {
      this.errorMessage = 'You must be logged in.';
      return;
    }

    this.challengeId = Number(this.route.snapshot.paramMap.get('challengeId'));
    this.fetchTasksAndStatuses();
  }

  fetchTasksAndStatuses(): void {
    this.http.get<any[]>(`http://localhost:8080/api/challenges/${this.challengeId}/tasks`).subscribe({
      next: (taskList) => {
        this.tasks = taskList;

        this.http.get<any[]>(`http://localhost:8080/api/users/${this.userId}/challenges/${this.challengeId}/tasks/status`)
          .subscribe({
            next: (statusList) => {
              const statusMap = new Map<number, boolean>();
              for (const status of statusList) {
                statusMap.set(status.taskId, status.completed);
              }

              this.tasks = this.tasks.map(task => ({
                ...task,
                completedToday: statusMap.get(task.taskId) || false
              }));
            },
            error: (err) => {
              console.error('Error fetching task statuses:', err);
              this.errorMessage = 'Failed to fetch task statuses.';
            }
          });
      },
      error: (err) => {
        console.error('Error fetching tasks:', err);
        this.errorMessage = 'Failed to load tasks.';
      }
    });
  }

  markTaskCompleted(taskId: number): void {
    const payload = {
      userId: this.userId,
      challengeId: this.challengeId,
      taskId: taskId,
      completed: true
    };

    this.http.post('http://localhost:8080/api/users/task-progress', payload).subscribe({
      next: () => {
        alert(`Marked task ${taskId} as completed.`);
        this.fetchTasksAndStatuses(); // Refresh UI
      },
      error: (err) => {
        if (err.status === 409) {
          alert('You already marked this task as completed for today.');
        } else {
          console.error('Error recording task progress:', err);
          alert('Failed to record task completion.');
        }
      }
    });
  }
}
