import { Moment } from 'moment';
import {Customer} from './customer';
import {Employee} from './employee';
import {Shipping} from './shipping';

export class Order {
    orderID: number;
    customer: Customer[];
    employee: Employee[];
    orderDate: Moment;
    orderNumber: string;
    shipDate: Moment;
    shipping: Shipping[];
    freightCharge: number;
    taxes: number;
    paymentReceived: string;
    comment: string;
}
