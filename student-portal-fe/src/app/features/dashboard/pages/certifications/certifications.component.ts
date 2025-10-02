import { Component } from '@angular/core';

@Component({
  selector: 'app-certifications',
  template: `
    <div class="section">
      <h3>Certifications</h3>
      <p>List of your certifications will appear here.</p>
      <!-- TODO: Replace with a data-driven list/table -->
    </div>
  `,
  styles: [
    `.section{padding:1rem;color:var(--color-text)} h3{margin:0 0 .5rem}`
  ]
})
export class CertificationsComponent {}
