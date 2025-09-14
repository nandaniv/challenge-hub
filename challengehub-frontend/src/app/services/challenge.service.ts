import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChallengeService {
  private baseUrl = 'http://localhost:8080/api/challenges';

  constructor(private http: HttpClient) {}

  getChallenges() {
    return this.http.get(this.baseUrl);
  }
}
