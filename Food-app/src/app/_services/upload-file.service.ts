import { Injectable } from '@angular/core';
import { HttpEvent, HttpRequest, HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class UploadFileService {
  head: HttpHeaders;
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
    this.head = new HttpHeaders().set('Access-Control-Allow-Origin', this.baseUrl);
  }




  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    // console.log(file.name);

    const req = new HttpRequest('POST', this.baseUrl + '/upload', formData, {
      headers: this.head
    });

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get(`${this.baseUrl}/files`);
  }
}
