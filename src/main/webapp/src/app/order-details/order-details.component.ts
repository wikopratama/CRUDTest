import { Order } from '../order';
import { Component, OnInit} from '@angular/core';
import { OrderService } from '../order.service';
import { Router, ActivatedRoute } from '@angular/router';
import {Detail} from '../detail';
import {Observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  id: number;
  order: Order;
  detail: Detail;
  objects: Observable<Object[]>;
  constructor(private route: ActivatedRoute, private router: Router,
              private orderService: OrderService) { }

  // tslint:disable-next-line:typedef
  ngOnInit() {
    this.order = new Order();
    this.detail = new Detail();
    this.id = this.route.snapshot.params.id;
    this.orderService.getDetail(this.id).subscribe((res: HttpResponse<Detail>) => (this.detail = res.body || null));
  }

  // tslint:disable-next-line:typedef
  list(){
    this.router.navigate(['order']);
  }
}
