import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChallengeService } from '../../services/challenge.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-challenges',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './challenges.html',
  styleUrl: './challenges.css'
})
export class ChallengesComponent implements OnInit {
  challenges: any[] = [];

  constructor(
    private challengeService: ChallengeService,
    public auth: AuthService
  ) {}

  ngOnInit() {
    this.challengeService.getChallenges().subscribe((data: any) => {
                                           this.challenges = data;
                                         });
  }

  logout() {
    this.auth.logout();
  }
}
