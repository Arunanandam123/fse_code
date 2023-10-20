import { Component, OnInit, Input } from '@angular/core';
import { SearchService } from '../search.service';


@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  searchResults: string[] = [];

  showPrice: boolean = true;
  constructor(private searchService: SearchService) { }
  ngOnInit() {
    this.searchService.searchQuery$.subscribe((query) => {
      if (query) {
        this.searchService.search(query).subscribe(
          (response) => {
            this.searchResults = response;
            if (query.includes('Menu')) {
              this.showPrice = true;
            } else {
              this.showPrice = false;
            }
            console.log('HTTP Response:', this.searchResults);
          },
          (error) => {
            console.error('Error:', error);
          }
        );
      }
    });
  }


}
