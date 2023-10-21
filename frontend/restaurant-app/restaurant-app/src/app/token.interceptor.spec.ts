import { TestBed } from '@angular/core/testing';
import { HTTP_INTERCEPTORS, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { TokenInterceptor } from './token.interceptor';
import { AuthService } from './auth.service';

describe('TokenInterceptor', () => {
  let interceptor: TokenInterceptor;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    authService = jasmine.createSpyObj('AuthService', ['getToken']);
    TestBed.configureTestingModule({
      providers: [
        TokenInterceptor,
        { provide: AuthService, useValue: authService },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptor,
          multi: true,
        },
      ],
    });

    interceptor = TestBed.inject(TokenInterceptor);
  });

  it('should add an Authorization header with the token', () => {
    const token = 'mockTokenValue';
    authService.getToken.and.returnValue(token);

    const httpRequest = new HttpRequest('GET', '/api/data');

    interceptor.intercept(httpRequest, {
      handle: (req: HttpRequest<any>): Observable<HttpEvent<any>> => {
        expect(req.headers.get('Authorization')).toBe(`Bearer ${token}`);
        return of(null);
      },
    });
  });

  it('should not add an Authorization header when the token is not available', () => {
    authService.getToken.and.returnValue(null);

    const httpRequest = new HttpRequest('GET', '/api/data');

    interceptor.intercept(httpRequest, {
      handle: (req: HttpRequest<any>): Observable<HttpEvent<any>> => {
        expect(req.headers.has('Authorization')).toBe(false);
        return of(null);
      },
    });
  });
});
