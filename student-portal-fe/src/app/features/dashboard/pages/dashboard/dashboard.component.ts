import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStateService } from '../../../../core/services/auth-state.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  collapsed = false;
  constructor(private authState: AuthStateService, private router: Router) {}

  toggleSidebar(): void {
    this.collapsed = !this.collapsed;
  }

  async logout(): Promise<void> {
    this.authState.clear();
    await this.router.navigate(['/auth/login']);
  }
}
