import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-primary-transaction',
  templateUrl: './primary-transaction.component.html',
  styleUrls: ['./primary-transaction.component.css']
})
export class PrimaryTransactionComponent implements OnInit {

  username: string;
  primaryTransactionList: Object[];
  
  constructor(private route: ActivatedRoute, private userService: UserService) { 
    this.route.params.forEach((params: Params) => {
      this.username = params['username'];
    });
    this.getPrimaryTransactionLIst();
  }

  getPrimaryTransactionLIst(){
    this.userService.getUserPrimaryTransactionList(this.username).subscribe(
      res => {
        this.primaryTransactionList = res;
      },
      error => {
        console.log(error);
      }
    );
  }

  ngOnInit() {
  }

}
