import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { SignupComponent } from './components/signup/signup';
import { ChallengesComponent } from './components/challenges/challenges';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ChallengeTasksComponent } from './components/challenge-tasks/challenge-tasks.component';
import { NotificationComponent } from './components/notification/notification.component'; // adjust path if needed

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'challenges', component: ChallengesComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'challenge/:challengeId/tasks', component: ChallengeTasksComponent }
];
