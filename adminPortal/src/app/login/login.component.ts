import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

 private loggedIn: boolean;
 private username: string;
 private password: string;

  constructor(private loginService: LoginService) {
    console.log(localStorage.getItem('AdminHasLoggedIn'));
    if(localStorage.getItem('AdminHasLoggedIn') == '' || localStorage.getItem('AdminHasLoggedIn') == null) {
      this.loggedIn = false;
    } else {
      this.loggedIn = true;
    }
   }
    

    onSubmit(){
    this.loginService.sendCredential(this.username, this.password).subscribe(
      res => {
        this.loggedIn =true;
        localStorage.setItem('AdminHasLoggedIn', 'true');
        location.reload();
      },
      error =>{
        console.log(error);
      }
      
    )
}


  ngOnInit() {
  }

}
