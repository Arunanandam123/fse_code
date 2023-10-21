import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { HttpClient } from '@angular/common/http';

describe('AuthService', () => {
  let authService: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    authService = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(authService).toBeTruthy();
  });

  it('should check if the user is authenticated', () => {
    authService.setToken('mockTokenValue');
    const isAuthenticated = authService.isAuthenticated();
    expect(isAuthenticated).toBe(true);
  });

  it('should check if the user is not authenticated', () => {
    authService.clearToken();
    const isAuthenticated = authService.isAuthenticated();
    expect(isAuthenticated).toBe(false);
  });

  it('should log in and return a token', () => {
    const mockCredentials = { username: 'testuser', password: 'testpassword' };
    const mockTokenResponse = { token: 'mockTokenValue' };

    authService.login(mockCredentials).subscribe(response => {
      expect(response.token).toBe(mockTokenResponse.token);
    });

    const req = httpTestingController.expectOne('http://localhost:8082/food/api/v1/user/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockTokenResponse);
  });

  it('should set and get a token', () => {
    authService.setToken('mockTokenValue');
    const token = authService.getToken();
    expect(token).toBe('mockTokenValue');
  });

  it('should clear the token', () => {
    authService.setToken('mockTokenValue');
    authService.clearToken();
    const token = authService.getToken();
    expect(token).toBe(null);
  });
});
