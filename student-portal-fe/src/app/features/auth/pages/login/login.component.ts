import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth.service';
import { LoginPayload } from '../../models/auth.models';
import { Router } from '@angular/router';
import { AuthStateService } from '../../../../core/services/auth-state.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  model: LoginPayload = { email: '', password: '' };

  submitting = false;
  error = '';

  constructor(private auth: AuthService, private authState: AuthStateService, private router: Router) {}

  async submit(f: NgForm): Promise<void> {
    if (f.invalid) return;
    this.submitting = true;
    this.error = '';
    try {
      const res = await this.auth.login(this.model);
      this.authState.setSession(res);
      await this.router.navigate(['/dashboard']);
    } catch (e) {
      this.error = 'Unable to sign in. Please try again.';
    } finally {
      this.submitting = false;
    }
  }
}
