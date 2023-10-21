import { TestBed, inject } from '@angular/core/testing';
import { Router, RouterStateSnapshot } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { AuthService } from './auth.service';
import { of } from 'rxjs';

describe('AuthGuard', () => {
  let authGuard: AuthGuard;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    authService = jasmine.createSpyObj('AuthService', ['isAuthenticated']);
    router = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      providers: [
        AuthGuard,
        { provide: AuthService, useValue: authService },
        { provide: Router, useValue: router }
      ]
    });

    authGuard = TestBed.inject(AuthGuard);
  });

  it('should allow activation when user is authenticated', () => {
    authService.isAuthenticated.and.returnValue(true);

    const canActivate = authGuard.canActivate(null, { url: '/protected' } as RouterStateSnapshot);
    
    expect(canActivate).toBe(true);
    expect(router.navigate).not.toHaveBeenCalled();
  });

  it('should prevent activation and navigate to login when user is not authenticated', () => {
    authService.isAuthenticated.and.returnValue(false);

    const canActivate = authGuard.canActivate(null, { url: '/protected' } as RouterStateSnapshot);
    
    expect(canActivate).toBe(false);
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });
});
