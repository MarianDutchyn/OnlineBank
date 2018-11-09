import { Component, OnInit } from '@angular/core';
import { LoginService } from '../service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    loggedIn: boolean;
  constructor(private loginService: LoginService, private router: Router) {
    if(localStorage.getItem('AdminHasLoggedIn') == '') {
			this.loggedIn = false;
		} else {
			this.loggedIn = true;
		}
   }

  ngOnInit() {
  }

  logout(){
    this.loginService.logout().subscribe(
      res =>{
         localStorage.setItem('AdminHasLoggedIn', '');
      },
      error => {
        console.log(error);
      }
    );
    location.reload();
    this.router.navigate(['/login']);
  }


}
