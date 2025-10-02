import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { AuthStateService } from '../services/auth-state.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private authState: AuthStateService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, _state: RouterStateSnapshot): boolean | UrlTree {
    const requiredRoles: string[] = route.data['roles'] ?? [];

    // Must be logged in
    if (!this.authState.token || !this.authState.user) {
      return this.router.createUrlTree(['/auth/login']);
    }

    // If no roles are specified, any logged-in user is allowed
    if (!requiredRoles.length) return true;

    // Check role intersection
    const ok = this.authState.hasAnyRole(requiredRoles);
    return ok ? true : this.router.createUrlTree(['/auth/login']);
  }
}
