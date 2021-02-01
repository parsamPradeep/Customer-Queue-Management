import { Component, OnInit } from '@angular/core';
import { DataService } from '../service/data.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-print-token',
  templateUrl: './print-token.component.html',
  styleUrls: ['./print-token.component.scss']
})
export class PrintTokenComponent implements OnInit {
  public data: any;

  public customername: any;
  public tokenNumber: any;
  date = new Date().toDateString();
  time = new Date().getTime().toString();
  day: string;
  month: string;
  year: string;
  constructor(printdata: DataService, private router: Router, private translate: TranslateService) {
    this.data = printdata.getOption();
    if (this.data != null) {
      this.customername = this.data.customerName;
      this.tokenNumber = this.data.tokenNumber;
    }
    translate.setDefaultLang('en/print-token');
  }

  submitted: boolean;
  ngOnInit() {
    this.submitted = false;
	this.autoPrintToken();
  }
  
  padWithZero(tokeno:any) {
    var mystring = ''+tokeno;
    while(mystring.length < 4) {
      
      mystring = '0'+mystring;
    }
    return mystring;
  }

  printDate() {
    var d = new Date();
    var months = ["01","02","03","04","05","06","07","08","09","10","11","12"];
    var days = ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"]
    var mins = ["00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59"];
    var todayDate = days[d.getDate()]+"-"+months[d.getMonth()]+"-"+d.getFullYear();
    var todayTime = d.getHours()+":"+mins[d.getMinutes()];
    var today = todayDate+" "+todayTime;
    return today;
  }
    autoPrintToken() {  
    var mywindow = window.open('', 'PRINT', 'height=10,width=10');
    mywindow.document.write('<div style="font-family: Calibri, Helvetica, Arial, sans-serif;">');
    mywindow.document.write('<h1 style="font-size :100px;">' + this.padWithZero(this.tokenNumber)  + '</h1>');
    mywindow.document.write('<hr/>')
    mywindow.document.write('<p style="font-size: 19px;text-align: right;"><i>'+this.printDate()+'</i></p>');
    mywindow.document.write('</div>');
    mywindow.print();
    mywindow.close();
    history.back();

    return true;
   }
}
