import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Dish } from 'src/app/_classes/dish';
import { CafeteriaService } from 'src/app/_services/cafeteria.service';

import * as _ from "lodash"

import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { HttpEventType, HttpResponse } from '@angular/common/http';
import { UploadFileService} from '../../_services/upload-file.service';
import { Observable } from 'rxjs';
import { DishService } from "../../_services/dish.service"

@Component({
  selector: 'app-cafeteria-add-product',
  templateUrl: './cafeteria-add-product.component.html',
  styleUrls: ['./cafeteria-add-product.component.css']
})
export class CafeteriaAddProductComponent implements OnInit {

  // dish: Dish;
  // dishGroup = new FormGroup({
  //   dishName: new FormControl(''),
  //   price: new FormControl(''),
  //   category: new FormControl(''),
  //   vegNonveg: new FormControl(''),
  //   availability: new FormControl(''),
  //   description: new FormControl('')
  // });
  // constructor(private cafeteriaService: CafeteriaService) { }

  // ngOnInit(): void {
  // }
  // addDish()
  // {
  //   this.dish = new Dish();
  //   this.dish.dishName = this.dishGroup.get('dishName').value;
  //   this.dish.price = this.dishGroup.get('price').value;
  //   this.dish.category = this.dishGroup.get('category').value;
  //   this.dish.vegNonveg = this.dishGroup.get('vegNonveg').value;
  //   this.dish.availability = this.dishGroup.get('availability').value;
  //   this.dish.description = this.dishGroup.get('description').value;
  //   this.cafeteriaService.addDish(this.dish);
  //   console.log('add Dish called');
  // }

  newdish: Dish = new Dish();
  updateDish: Dish= new Dish();


  disharray: Dish[]=[];

  categorySort: any;

  selectedFiles: FileList;

  currentFile: File;
  progress = 0;
  message = '';
  errMsg="";


  successAlertClosed: boolean;
  failAlertClose:boolean;
  showUpdate: boolean=false;
  successmsg: string;


  constructor(private http: HttpClient,private uploadService: UploadFileService, private dishsrvice: DishService) {
    this.newdish.description="";
    this.newdish.availability=false;
    this.successAlertClosed=false;
    this.failAlertClose=false;
    this.currentFile=undefined;
    this.showUpdate=false;
   }

  ngOnInit() {
    this.callGetAllDishes();
  }

  catSort(){
    this.categorySort = this.disharray.reduce(function (r, a) {
      r[a.category] = r[a.category] || [];
      r[a.category].push(a);
      return r;
  }, Object.create(null));
  console.log(this.categorySort);

  }

  get key(){
    return Object.keys(this.categorySort);
  }
 
  submitAddDish(form: NgForm): void {
    this.createDish(this.newdish);
  }



  createDish(dishObj: Dish)
 {
    dishObj.dishName=dishObj.dishName.toLowerCase();
    dishObj.restaurantName=dishObj.restaurantName.toLowerCase();
    dishObj.category=dishObj.category.toLowerCase();
    this.renameFile(this.newdish.dishName+this.newdish.restaurantName);
    dishObj.imgName=this.currentFile.name;
    // var element = document.getElementById("currentFileName").value;
    console.log(dishObj.imgName);
    this.dishsrvice.createDishReq(dishObj)
      .subscribe(
        res => {
          console.log(res);
          this.disharray.unshift(res)
        },
        err => {
          console.log(err);
          this.newdish = new Dish();
          this.newdish.description='';
          this.newdish.availability=false;
          //this.errMsg=err.error['message'];
          this.errMsg="dish Already exist."
          this.failAlertClose =true;
          setTimeout(() => {this.failAlertClose = false;this.errMsg='';}, 2000);
        },
        () => {console.log('HTTP request completed.');
              //  this.disharray.unshift(this.newdish);
               this.upload(this.newdish.dishName + this.newdish.restaurantName);
               this.newdish = new Dish();
               this.newdish.description='';
               this.newdish.availability=false;
               this.catSort();
              }              
      );
      
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles.item(0).name);
    this.currentFile = this.selectedFiles.item(0);
  }

  upload(filename : string) {
    this.progress = 0;  
    this.currentFile = this.selectedFiles.item(0);
    this.renameFile(filename);
    this.uploadService.upload(this.currentFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          // this.fileInfos = this.uploadService.getFiles();
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;

        this.errMsg=err.error['message'];
          this.failAlertClose =true;
          setTimeout(() => {this.failAlertClose = false;this.errMsg='';}, 5000);
      },
      ()=>{
        this.successAlertClosed =true;
        this.successmsg="Added Successfully !!!"
        setTimeout(() => {this.successmsg="";this.successAlertClosed = false;}, 5000);
        this.currentFile=undefined;
      }
      );
  
    this.selectedFiles = undefined;
  }

  renameFile(name: string): void {
    name=name+'.'+this.currentFile.type.split('/')[1];
    const oldFileItem: File = this.currentFile;
    this.currentFile = new File([this.currentFile], name.replace(" ",""), {type: oldFileItem.type});
  }



callGetAllDishes(){
  this.dishsrvice.getAllDishes().subscribe(
  response => {
      console.log(response);
      this.disharray=response;
  },
  err => {
    console.log(err);
  },
  () => {
    this.catSort();
  }
);
}



availableToggle( dishData: Dish){
   console.log(dishData);
    this.dishsrvice.updateDish(dishData)
    .subscribe(
      res=>{
        this.disharray = this.disharray.filter((item) => item.id !== dishData.id);
        this.catSort();

        this.disharray.unshift(dishData);
        this.catSort();

        console.log(res);
      },
      err =>{
        console.log(err);
      },
      ()=>{
        this.successAlertClosed =true;
        this.successmsg="Updated Successfully !!!"
        setTimeout(() => {this.successmsg="";this.successAlertClosed = false;}, 5000);
        this.currentFile=undefined;
        this.updateDish= new Dish();
        this.closeUpdateDish();
      }
    );
} 

openUpdateDish(dish:Dish){
  this.updateDish=dish;
  this.showUpdate=true;
}

closeUpdateDish(){
  if(this.showUpdate==true)
  {this.showUpdate=!this.showUpdate;}
  this.updateDish=new Dish();
}

deleteDish(dish:Dish){
  console.log(dish.dishName);
  this.dishsrvice.deletDish(dish)
  .subscribe(
    res=>{
      console.log(res);
    },
    err =>{
      console.log(err);
    },
    ()=>{
      this.disharray = this.disharray.filter((item) => item.id !== dish.id);
      this.catSort();
      
      this.successAlertClosed =true;
      this.successmsg="deleted Successfully !!!"
      setTimeout(() => {this.successmsg="";this.successAlertClosed = false;}, 5000);
      this.currentFile=undefined;
    }
  );
}


}
