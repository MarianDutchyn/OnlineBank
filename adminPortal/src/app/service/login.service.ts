import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Http, Headers } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http1: HttpClient, private http: Http) { }

  sendCredential(username: string, password: string){
    let url = 'http://localhost:8080/index';
    let params = 'username='+username+'&password='+password;
    let headers = new Headers(
      {
        'Content-Type': 'application/x-www-form-urlencoded'
        // 'Access-Control-Allow-Credentials' : true
      });
    return this.http.post(url, params, {headers: headers, withCredentials: true})
  }

  logout(){
    let url = 'http://localhost:8080/logout';
    return this.http.get(url, {withCredentials : true});
  }
}
