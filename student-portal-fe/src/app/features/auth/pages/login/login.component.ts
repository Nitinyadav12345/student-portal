import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { User } from '../../models/auth.models';
import { Router } from '@angular/router';
import { AuthStateService } from '../../../../core/services/auth-state.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  model: Partial<User> & { email: string; password: string } = { email: '', password: '' };

  submitting = false;
  error = '';

  constructor(private auth: AuthService, private authState: AuthStateService, private router: Router) {}

  submit(f: NgForm): void {
    if (f.invalid) return;
    this.submitting = true;
    this.error = '';

    this.auth.login(this.model).subscribe({
      next: (res) => {
        this.authState.setSession(res);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        if (err?.status === 401 || err?.status === 403) {
          this.error = 'Invalid credentials';
        } else {
          this.error = 'Unable to sign in. Please try again.';
        }
        this.submitting = false;
      },
      complete: () => {
        this.submitting = false;
      }
    });
  }
}

