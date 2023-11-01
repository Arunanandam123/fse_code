import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = 'http://localhost:8082/food/api/v1/user/search/';    

  constructor(private http: HttpClient) { }

  private searchQuerySubject = new BehaviorSubject<string>('');
  searchQuery$ = this.searchQuerySubject.asObservable();

  setSearchQuery(query: string) {
    this.searchQuerySubject.next(query);
  }

  httpOptions = {
    headers: new HttpHeaders({ "content-Type": "application/json" })
  }

  search(searchQuery: string): Observable<any[]> {
    const url = `${this.apiUrl}`+searchQuery;    
    return this.http.get<any[]>(url).pipe(
      catchError(this.handleError<any[]>("Error while performing searches", [])))

  }

  private handleError<T>(operation = "operation", result?:T){
      return (error : any) : Observable<T> =>{
        console.log(error);
        return of(result as T);
      }
  }
}
