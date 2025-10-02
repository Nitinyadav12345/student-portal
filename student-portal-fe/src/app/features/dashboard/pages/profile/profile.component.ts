import { Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  model = {
    name: 'John Doe',
    email: 'john.doe@example.com',
    phone: ''
  };
  message = '';

  save() {
    // TODO: integrate API to persist profile changes
    this.message = 'Profile saved (mock)';
  }
}
