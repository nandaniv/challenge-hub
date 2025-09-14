import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  signup(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, credentials);
  }

  saveUser(user: any): void {
    if (this.isBrowser()) {
      localStorage.setItem('user', JSON.stringify(user));
    }
  }

  isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  getUser(): any {
    if (!this.isBrowser()) return null;

    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  isUserLoggedIn(): boolean {
    return !!this.getUser();
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('user');

    }
  }
}
