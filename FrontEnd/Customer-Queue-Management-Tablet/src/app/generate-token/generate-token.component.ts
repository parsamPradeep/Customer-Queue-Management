import { Component, OnInit } from '@angular/core';
import { TellerQueueService } from '../api/teller-queue-api';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Customer } from '../model/customer';
import { Router } from '@angular/router';
import { DataService } from '../service/data.service';
import json from '../../assets/data/headerdata.json';
import keys from '../../assets/data/keys.json';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-generate-token',
  templateUrl: './generate-token.component.html',
  styleUrls: ['./generate-token.component.scss'],
})
export class GenerateTokenComponent implements OnInit {


  constructor(private tellerQueueService: TellerQueueService,
    private router: Router,
    private printdata: DataService, private http: HttpClient,
    private translate: TranslateService) {
    translate.setDefaultLang('en/generate-token');
  }
  customer: Customer = new Customer();
  submitted: boolean;
  tokengenerationform: FormGroup;
  headerModel: HeaderModel = json;
  date: Date = new Date();
  year: string = this.date.getFullYear().toString();
  month: string = ((this.date.getMonth() + 1) < 10 ? '0' : '') + (this.date.getMonth() + 1);
  day: string = (this.date.getDate() < 10 ? '0' : '') + this.date.getDate();
  finRequestModel: FinRequest = new FinRequest(null, null);
  jsonFormObject: any;
  serviceType: any;
  spinner: boolean;
  defaultServiceData: any;

  ngOnInit() {
    this.spinner = false;
    this.submitted = false;
    this.tokengenerationform = new FormGroup({
      servicetype: new FormControl('', [Validators.required])
    });
    this.finRequestModel.headerModel = this.headerModel;
    this.tellerQueueService.serviceType(this.finRequestModel).subscribe(data => {
      this.serviceType = data[keys.finData];
      console.log(this.serviceType);
    });


    this.tokengenerationform.get('servicetype').setValue(this.configService.getConfigData().default.defaultServiceTypeId);
  }


  generatetoken() {
    // tslint:disable-next-line:max-line-length
    this.defaultServiceData = this.serviceType.find(c => c.serviceTypeId == this.configService.getConfigData().default.defaultServiceTypeId);
    if (this.customer.serviceTypeId === undefined) {
      this.customer.serviceTypeId = this.defaultServiceData.serviceTypeId;
    }
    this.submitted = true;
    this.headerModel.applicationDate = this.year + '-' + this.month + '-' + this.day;
    this.finRequestModel.headerModel = this.headerModel;
    this.finRequestModel.finData = this.customer;
    if (this.tokengenerationform.invalid) {
      this.spinner = false;
      return;
    } else {
      this.save();
    }
  }

  save() {
    this.spinner = true;
    this.tellerQueueService
      .requestNewToken(this.finRequestModel)
      .subscribe(data => {
        console.log(data);
        if (data[keys.responseStatus] === 'ERROR') {
          this.spinner = false;
          alert(data[keys.finErrorDetails][0].code);
          return;
        }
        this.printdata.setOption(keys.tokenNumber, data[keys.finData].tokenNumber);
        this.printdata.setOption(keys.customerName, data[keys.finData].customerName);
        this.gotoPrint();
      });

  }

  gotoPrint() {
    this.spinner = false;
    this.router.navigate([keys.routeToPrint]);
  }

  public hasError = (controlName: string, errorName: string) => {
    return this.tokengenerationform.controls[controlName].hasError(errorName);
  }

}
