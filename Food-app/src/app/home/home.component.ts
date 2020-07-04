import { Component, OnInit } from '@angular/core';
import { Dish } from '../_classes/dish';
import { Router } from '@angular/router';
import { PublicService } from '../_services/public.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  inputSearch: string;
  dishes: Dish[];
  constructor(private router: Router, private publicService: PublicService) { }

  ngOnInit(): void {
    this.publicService.getAllDish().subscribe((dish: Dish[]) => {
      this.dishes = dish;
    });
  }
  searchFood()
  {
    this.publicService.getDish(this.inputSearch);
  }
  addToCart(dishId)
  {
    // TODO
  }
}
