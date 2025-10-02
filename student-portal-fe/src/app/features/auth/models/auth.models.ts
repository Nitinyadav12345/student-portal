export interface LoginPayload {
  email: string;
  password: string;
}

export interface SignupPayload {
  name: string;
  email: string;
  password: string;
}

export type Role = 'student' | 'admin' | 'staff';

export interface User {
  id?: string;
  name?: string;
  email: string;
  roles: Role[];
}

export interface AuthResponse {
  token: string;
  user: User;
}
