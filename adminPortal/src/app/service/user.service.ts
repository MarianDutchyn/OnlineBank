import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }


  getAllUsers(){
    let url = "http://localhost:8080/api/user/getAll";

    return this.http.get<Object[]>(url, {withCredentials: true});
  }

  getUserPrimaryTransactionList(username: string){
    let url = "http://localhost:8080/api/user/primaryTransaction?username="+username;
    
    return this.http.get<Object []>(url, {withCredentials: true});
  }

  getUserSavingsTransactionList(username: string){
    let url = "http://localhost:8080/api/user/savingsTransaction?username="+username;
    
    return this.http.get<Object []>(url, {withCredentials: true});
  }

  enableUser(username: string){
    let url = "http://localhost:8080/api/user/"+username+"/enable";

    return this.http.get(url, {withCredentials: true});
  }

  disableUser(username: string){
    let url = "http://localhost:8080/api/user/"+username+"/disable";

    return this.http.get(url, {withCredentials: true});
  }
}
