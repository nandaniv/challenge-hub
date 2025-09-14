import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserChallengeService } from '../../services/user-challenge.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  challenges: any[] = [];
  errorMessage: string | null = null;

  constructor(
    private userChallengeService: UserChallengeService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.auth.getUser();
    const userId = user?.userId;

    if (userId) {
      this.userChallengeService.getChallengesForUser(userId).subscribe({
        next: (data) => this.challenges = data,
        error: (err) => {
          console.error('Error fetching user challenges:', err);
          this.errorMessage = 'Failed to load challenges.';
        }
      });
    } else {
      console.warn('No user is logged in.');
      this.errorMessage = 'Please log in to view your challenges.';
    }
  }

activateChallenge(challengeId: number): void {
  const user = this.auth.getUser();
  if (!user?.userId) return;

  this.userChallengeService.activateChallengeForUser(user.userId, challengeId).subscribe({
    next: () => {
      alert('Challenge activated!');
      this.ngOnInit(); // reload challenges
    },
    error: (err) => {
      console.error('Activation failed:', err);
      alert('Failed to activate challenge.');
    }
  });
}

}
