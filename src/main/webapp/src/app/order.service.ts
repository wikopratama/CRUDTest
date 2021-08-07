import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUrl = 'http://localhost:8080/CRUDTest/api/v1/order';
  private insertUrl = 'http://localhost:8080/CRUDTest/api/v1';

  constructor(private http: HttpClient) { }

  getOrder(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  getDetail(id: number): Observable<any> {
    return this.http.get(`${this.insertUrl}/detail/${id}`, { observe: 'response' });
  }

  createOrder(order: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, order);
  }

  getOrderList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }

  insertCustomer(): Observable<any> {
    return this.http.get(`${this.insertUrl}/customer/csv`);
  }
}
