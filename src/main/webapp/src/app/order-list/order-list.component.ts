import { OrderDetailsComponent } from '../order-details/order-details.component';
import { Observable } from 'rxjs';
import { OrderService } from '../order.service';
import { Order } from '../order';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  orders: Observable<Order[]>;

  constructor(private orderService: OrderService,
              private router: Router) {}

  // tslint:disable-next-line:typedef
  ngOnInit() {
    this.reloadData();
  }

  // tslint:disable-next-line:typedef
  reloadData() {
    this.orders = this.orderService.getOrderList();

  }

  // tslint:disable-next-line:typedef
  orderDetails(id: number){
    this.router.navigate(['details', id]);
  }
}
