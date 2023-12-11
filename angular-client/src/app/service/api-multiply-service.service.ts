import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiMultiplyServiceService {
  httpClient: any;

  // for the sake of testing not to be used in production or development
  auth_token = "343C-ED0B-4137-B27E";
  constructor(private http: HttpClient) { }

  // API method to send operands via request body in Post call
  postOperands(data: any) {
    const endpoint = 'http://localhost:8082/api/calculator/multiply';
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.auth_token}`
    });

    return this.http.post(endpoint, JSON.stringify(data), { headers });
  }

}