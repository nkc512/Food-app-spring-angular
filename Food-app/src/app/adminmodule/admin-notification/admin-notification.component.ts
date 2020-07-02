import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-admin-notification',
  templateUrl: './admin-notification.component.html',
  styleUrls: ['./admin-notification.component.css']
})
export class AdminNotificationComponent implements OnInit {

  cafeTime = new FormGroup({
    start_time: new FormControl(''),
    close_time: new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }
  updateTime() {
    console.log(this.cafeTime.get('start_time').value, this.cafeTime.get('close_time').value);
  }

}
