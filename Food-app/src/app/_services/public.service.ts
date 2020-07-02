import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  startTime = '';
  closeTime = '';
  announcements = '';
  constructor() { }
}
