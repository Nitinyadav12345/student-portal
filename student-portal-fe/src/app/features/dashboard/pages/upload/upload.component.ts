import { Component } from '@angular/core';
import { HttpEventType } from '@angular/common/http';
import { UploadService } from '../../../../core/services/upload.service';
import { AuthStateService } from '../../../../core/services/auth-state.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent {
  selectedFile?: File;
  message = '';
  isDragging = false;
  uploading = false;
  progress = 0;

  constructor(private uploadService: UploadService, private authState: AuthStateService) {}

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
    const userId = this.authState.user?.id;
    if (!userId) {
      this.message = 'You must be logged in to upload.';
      return;
    }

    this.uploading = true;
    this.message = '';
    this.progress = 0;
    this.uploadService.uploadPdfWithProgress(userId, this.selectedFile).subscribe({
      next: (event) => {
        if (event.type === HttpEventType.UploadProgress && event.total) {
          this.progress = Math.round((100 * event.loaded) / event.total);
        } else if (event.type === HttpEventType.Response) {
          this.message = `Uploaded: ${this.selectedFile!.name}`;
          this.selectedFile = undefined;
        }
      },
      error: (err) => {
        this.message = err?.error?.message || 'Upload failed. Please try again.';
        this.uploading = false;
      },
      complete: () => {
        this.uploading = false;
        this.progress = 0;
      }
    });
  }

  clearSelection() {
    this.selectedFile = undefined;
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
  }

  onDrop(event: DragEvent) {
    event.preventDefault();
    this.isDragging = false;
    const file = event.dataTransfer?.files && event.dataTransfer.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
      this.message = `Selected: ${file.name}`;
    } else {
      this.selectedFile = undefined;
      this.message = 'Please select a valid PDF file.';
    }
  }
}
