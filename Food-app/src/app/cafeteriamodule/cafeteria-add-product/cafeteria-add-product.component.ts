import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Dish } from 'src/app/_classes/dish';
import { CafeteriaService } from 'src/app/_services/cafeteria.service';

@Component({
  selector: 'app-cafeteria-add-product',
  templateUrl: './cafeteria-add-product.component.html',
  styleUrls: ['./cafeteria-add-product.component.css']
})
export class CafeteriaAddProductComponent implements OnInit {

  dish: Dish;
  dishGroup = new FormGroup({
    dishName: new FormControl(''),
    price: new FormControl(''),
    category: new FormControl(''),
    vegNonveg: new FormControl(''),
    availability: new FormControl(''),
    description: new FormControl('')
  });
  constructor(private cafeteriaService: CafeteriaService) { }

  ngOnInit(): void {
  }
  addDish()
  {
    this.dish = new Dish();
    this.dish.dishName = this.dishGroup.get('dishName').value;
    this.dish.price = this.dishGroup.get('price').value;
    this.dish.category = this.dishGroup.get('category').value;
    this.dish.vegNonveg = this.dishGroup.get('vegNonveg').value;
    this.dish.availability = this.dishGroup.get('availability').value;
    this.dish.description = this.dishGroup.get('description').value;
    this.cafeteriaService.addDish(this.dish);
    console.log('add Dish called');
  }
}
