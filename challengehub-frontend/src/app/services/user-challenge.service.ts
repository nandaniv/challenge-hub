import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserChallengeService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getChallengesForUser(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}/challenges`);
  }
activateChallengeForUser(userId: number, challengeId: number): Observable<any> {
  return this.http.post(`${this.baseUrl}/${userId}/activate/${challengeId}`, {});
}

}
