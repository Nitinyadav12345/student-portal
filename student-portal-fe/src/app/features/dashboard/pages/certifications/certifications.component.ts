import { Component } from '@angular/core';
import { TableColumn } from '../../../../shared/components/table/table.component';

@Component({
  selector: 'app-certifications',
  templateUrl: './certifications.component.html',
  styleUrls: ['./certifications.component.scss']
})
export class CertificationsComponent {
  columns: TableColumn[] = [
    { key: 'name', header: 'Certificate' },
    { key: 'provider', header: 'Provider' },
    { key: 'date', header: 'Date' },
    { key: 'status', header: 'Status' }
  ];

  rows = [
    { name: 'Angular Fundamentals', provider: 'Coursera', date: '2024-11-10', status: 'Verified' },
    { name: 'TypeScript Advanced', provider: 'Udemy', date: '2025-01-22', status: 'Pending' }
  ];
}
