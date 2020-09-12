import { Component, OnInit } from '@angular/core';
import { PublicService } from '../_services/public.service';
import { ContactUs } from '../_classes/contact-us';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {
  contactUs: ContactUs;
  contactForm = new FormGroup({
    name: new FormControl(''),
    email: new FormControl(''),
    comment: new FormControl('')}
  );
  constructor(public publicService: PublicService) { }

  ngOnInit(): void {
  }

  contactUsFunction(): void {
    this.publicService.postContactUs(this.contactForm.value).subscribe(
      res => {
        console.log(res.message);
        
      },
      err => {
        console.log(err);
        
      },
      () => {
        console.log('Function completed');
        
      }
    )

  }
}
