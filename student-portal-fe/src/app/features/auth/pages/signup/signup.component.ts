import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';
import { AuthStateService } from '../../../../core/services/auth-state.service';
import { User } from '../../models/auth.models';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent {

  model: User = {
    username: '',
    email: '',
    password: '',
    role: 'student'
  };

  submitting = false;
  error = '';

  constructor(
    private auth: AuthService,
    private authState: AuthStateService,
    private router: Router
  ) {}

  submit(f: NgForm): void {
    if (f.invalid) return;

    this.submitting = true;
    this.error = '';

    this.auth.signup(this.model).subscribe({
      next: (res) => {
        this.authState.setSession(res);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error(err);
        this.error = 'Unable to create your account. Please try again.';
      },
      complete: () => {
        this.submitting = false;
      }
    });
  }
}

