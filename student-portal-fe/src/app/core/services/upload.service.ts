import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthStateService } from './auth-state.service';

@Injectable({ providedIn: 'root' })
export class UploadService {
    private apiUrl = 'http://localhost:8080/api/pdf';

    constructor(private http: HttpClient, private authState: AuthStateService) { }

    // Upload PDF with userId as multipart/form-data
    uploadPdf(userId: number, file: File): Observable<any> {
        const formData = new FormData();
        formData.append('userId', String(userId));
        formData.append('file', file);

        const token = this.authState.token;
        const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;

        // Simple POST (no progress events required for now)
        return this.http.post<any>(`${this.apiUrl}/upload`, formData, { headers });
    }

    // If you later want progress, you can switch to this version:
    uploadPdfWithProgress(userId: number, file: File): Observable<HttpEvent<any>> {
        const formData = new FormData();
        formData.append('userId', String(userId));
        formData.append('file', file);

        const token = this.authState.token;
        const headers = token ? new HttpHeaders({ Authorization: `Bearer ${token}` }) : undefined;

        const req = new HttpRequest('POST', `${this.apiUrl}/upload`, formData, {
            reportProgress: true,
            headers
        });
        return this.http.request(req);
    }
}
