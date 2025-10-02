import { Component } from '@angular/core';

@Component({
  selector: 'app-upload',
  template: `
    <div class="section">
      <h3>Upload Certificate (PDF)</h3>
      <p>Select a PDF file to upload.</p>
      <input type="file" accept="application/pdf" (change)="onFileSelected($event)" />
      <button [disabled]="!selectedFile" (click)="onUpload()">Upload</button>
      <p *ngIf="message">{{ message }}</p>
    </div>
  `,
  styles: [
    `.section{padding:1rem;color:var(--color-text)} h3{margin:0 0 .5rem} button{margin-top:.5rem}`
  ]
})
export class UploadComponent {
  selectedFile?: File;
  message = '';

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    const file = input.files && input.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
      this.message = `Selected: ${file.name}`;
    } else {
      this.selectedFile = undefined;
      this.message = 'Please select a valid PDF file.';
    }
  }

  onUpload() {
    if (!this.selectedFile) return;
    // TODO: integrate with backend upload API
    this.message = `Uploaded: ${this.selectedFile.name} (mock)`;
  }
}
