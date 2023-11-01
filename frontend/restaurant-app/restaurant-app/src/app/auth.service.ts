import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8082/food/api/v1/user';  
  
  constructor(private http: HttpClient) { }


  private authToken: string | null = null;
  

  isAuthenticated(): boolean {
    return !!this.authToken; 
  }

  login(credentials: { username: string; password: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials);
  }

  setToken(token: string): void {    
    this.authToken = token;
    sessionStorage.setItem('token', token);
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  clearToken(): void {
    this.authToken = null;
    sessionStorage.removeItem('token');
  }

  logout() {
    this.clearToken();
  }

}
