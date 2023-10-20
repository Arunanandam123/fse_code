import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    const credentials = { username: this.username, password: this.password };

    this.authService.login(credentials).subscribe(      
      (response) => {
        console.log(response);
        this.authService.setToken(response.token);
        this.router.navigate(['/home']); 
      },
      (error) => {
        console.error('Login failed:', error);
      }
    );
  }
}
