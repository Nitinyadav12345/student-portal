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

  setSession(res: AuthResponse): void {
    localStorage.setItem(TOKEN_KEY, res.token);
    // Build a safe user object even if backend omits `user`
    const apiUser = (res as any)?.user ?? {};
    const rawRole = String(apiUser.role ?? '').toLowerCase();
    let mappedRole: User['role'] = 'student';
    if (rawRole.includes('admin')) mappedRole = 'admin';
    else if (rawRole.includes('student')) mappedRole = 'student';

    const normalizedUser: User = {
      id: apiUser.id,
      username: apiUser.username ?? '',
      email: apiUser.email ?? '',
      role: mappedRole,
    };
    localStorage.setItem(USER_KEY, JSON.stringify(normalizedUser));
    this.userSubject.next(normalizedUser);
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
