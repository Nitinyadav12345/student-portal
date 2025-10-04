import { Injectable } from '@angular/core';
import { User, AuthResponse } from '../../features/auth/models/auth.models';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  login(user: Partial<User> & { email: string; password: string }): Observable<AuthResponse> {
    const payload = {
      email: user.email,
      passwordHash: user.password,
    };
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, payload);
  }

  signup(user: User): Observable<AuthResponse> {
    // Send only necessary fields
    const payload = {
      username: user.username,
      email: user.email,
      passwordHash: user.password,
      role: user.role
    };
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, payload);
  }
}
