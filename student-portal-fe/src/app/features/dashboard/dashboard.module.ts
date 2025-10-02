import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { OverviewComponent } from './pages/overview/overview.component';
import { UploadComponent } from './pages/upload/upload.component';
import { CertificationsComponent } from './pages/certifications/certifications.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    DashboardComponent,
    OverviewComponent,
    UploadComponent,
    CertificationsComponent,
    ProfileComponent,
    SettingsComponent,
  ],
  imports: [CommonModule, RouterModule, FormsModule, SharedModule, DashboardRoutingModule],
})
export class DashboardModule {}
