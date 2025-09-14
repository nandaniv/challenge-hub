import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common'; //Required for *ngIf
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router'; // Import Router

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink], // Add CommonModule here
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  isLoggedIn = signal(false); // default to false

    constructor(private auth: AuthService, private router: Router) {}

    ngOnInit() {
      if (this.auth.isUserLoggedIn()) {
        this.isLoggedIn.set(true);
      }
    }

    logout() {
      this.auth.logout();
      this.isLoggedIn.set(false);
      this.router.navigate(['/login']);
    }
}
