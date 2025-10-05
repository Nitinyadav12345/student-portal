import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthResponse, User } from '../../features/auth/models/auth.models';

const TOKEN_KEY = 'auth.token';
const USER_KEY = 'auth.user';
@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private userSubject = new BehaviorSubject<User | null>(null);
  readonly user$ = this.userSubject.asObservable();

  constructor() {
    this.restore();
  }

  get user(): User | null {
    return this.userSubject.value;
  }

  get token(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  // Accept either a full AuthResponse (with token, user) or just a User object
  setSession(input: AuthResponse | User): void {
    const maybeRes = input as AuthResponse as any;
    const user: User = (maybeRes && maybeRes.user) ? (maybeRes.user as User) : (input as User);
    const token: string | undefined = maybeRes && typeof maybeRes.token === 'string' ? maybeRes.token : undefined;

    if (token) {
      localStorage.setItem(TOKEN_KEY, token);
    }
    // Always store the full user object
    localStorage.setItem(USER_KEY, JSON.stringify(user));
    this.userSubject.next(user);
  }

  clear(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.userSubject.next(null);
  }

  private restore(): void {
    const token = localStorage.getItem(TOKEN_KEY);
    const rawUser = localStorage.getItem(USER_KEY);
    if (token && rawUser) {
      try {
        const user = JSON.parse(rawUser) as User;
        this.userSubject.next(user);
      } catch {
        this.clear();
      }
    }
  }
}
