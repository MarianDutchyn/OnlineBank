import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) { 
    this.getUsers();
  }

  userList: Object[];

  getUsers(){
    this.userService.getAllUsers().subscribe(
     res => {
       this.userList = res;
       console.log(res);
     },
     error => {
       console.log(error);
           }
    );
  }

  onSelectPrimary(username: string){
    this.router.navigate(["/primaryTransaction", username]);
  }

  onSelectSavings(username: string){
    this.router.navigate(["/savingsTransaction", username]);
  }

  enableUser(username: string){
    this.userService.enableUser(username).subscribe();
    location.reload();
  }

  disableUser(username: string){
    this.userService.disableUser(username).subscribe();
    location.reload();
  }



  ngOnInit() {
  
  }

}
