import { Component, OnInit } from '@angular/core';
import { WebSocketService } from '../services/websocket.service';
import { ConfigService } from '../services/config.service';
import headerData from '../../assets/data/headerdata.json';
import { FinRequest } from '../common/FinRequestModel';
import keys from '../../assets/data/keys.json';
import { HeaderModel } from '../common/HeaderModel';
import { TranslateService } from '@ngx-translate/core';
import Speech from 'speak-tts';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import * as $ from 'jquery';
@Component({
  selector: 'app-signage',
  templateUrl: './signage.component.html',
  styleUrls: ['./signage.component.scss']
})
export class SignageComponent implements OnInit {
  tableData: [];
  tabledata: any = [];
  announcementData: [];
  tableHeader = ['TOKEN', 'COUNTER'];
  finRequestModel: FinRequest = new FinRequest(null, null);
  date = new Date().toDateString();
  time;
  hr: string;
  min: string;
  sec: string;
  timer: any;
  ticket: any;
  counterNumber: any;
  serviceType: any;
  campainurl: string[];
  headerData: HeaderModel = headerData;
  branchCodeCbs: any;
  empty: any = [];
  rem: any;

  constructor(private webSocketService: WebSocketService,
    private configService: ConfigService, private translate: TranslateService,
    slideConfig: NgbCarouselConfig) {
    console.log('nav speed', this.configService.getConfigData().navSpeed);
    slideConfig.interval = this.configService.getConfigData().navSpeed;
    slideConfig.wrap = false;
    slideConfig.keyboard = false;
    slideConfig.pauseOnHover = false;
    slideConfig.wrap = true;
    translate.setDefaultLang('en/signage');
    for (let emp = 0; emp <= 10; emp++) {
      this.empty.push({
        Counter: '',
        Ticket: ''
      });
    }

    this.headerData.branchCodeCbs = this.branchCodeCbs;
    this.finRequestModel.headerModel = this.headerData;
    this.webSocketService.getCampainURLForBranch(this.finRequestModel).subscribe(data => {
      this.campainurl = data;
      console.log(this.campainurl);
    });
  }

  ngOnInit() {
    this.timer = setInterval(() => {
      this.hr = new Date().getHours().toString();
      this.min = new Date().getMinutes().toString();
      this.sec = (new Date().getSeconds() < 10) ? ('0' + new Date().getSeconds().toString()) : (new Date().getSeconds().toString());
      this.time = this.hr + ':' + this.min + ':' + this.sec;
    }, 1000);
    this.webSocketService.serviceType(this.finRequestModel).subscribe(data => {
      this.serviceType = data.finData;
    });

    const stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame => {
      stompClient.subscribe(this.configService.getConfigData().tableDataTopic + '-' + this.branchCodeCbs, (tabledata: any): void => {
        this.tableData = JSON.parse(tabledata.body);
        tabledata = this.tableData;
        this.tabledata = [];
        for (const item of tabledata) {
          const tableData: any = {};
          if (item != null) {
            tableData.COUNTER = item.counterNumber;
            tableData.TOKEN = this.getToken(item.tokenNumber);
          }
          this.tabledata.push(tableData);
        }
        this.getNumberOfEmptyCells(this.tabledata);
        console.log('table Data', this.tabledata);
        if (this.tabledata.length > 9) {
          const ele = document.getElementById('table_scroll');
          if (setInterval(() => { ele.scrollTo(0, 900); }, 2500)) {
            setInterval(() => { ele.scrollTo(900, 0); }, 5000);
          }

        }

      });
      stompClient.subscribe(this.configService.getConfigData().announcementTopic + '-' + this.branchCodeCbs, (announcement: any): void => {
        this.announcementData = JSON.parse(announcement.body);
        this.ticket = this.announcementData[keys.tokenNumber];
        this.counterNumber = this.announcementData[keys.counterNumber];
        this.textToSpeech(this.ticket, this.counterNumber);
      });
    });
  }


  textToSpeech(ticket, counter) {

    let ticketname = "Ticket";
    this.translate.get(ticketname).subscribe((text: string) => {
      ticketname = text + ticket;
    });
    let text1 = "Please proceed to Counter";
    this.translate.get(text1).subscribe((text: string) => {
      text1 = text + counter;
    });
    let text2 = "Our Teller Representative will be pleased to serve you.";
    this.translate.get(text2).subscribe((text: string) => {
      text2 = text;
    });


    const speech = new Speech();
    speech.setLanguage('fr-FR');
    if (speech.hasBrowserSupport()) {
      console.log('speech synthesis supported');
      speech.init().then((data) => {
      }).catch(e => {
        console.error('An error occured while initializing : ', e);
      });
      speech.speak({
        text: ticketname + text1 + text2,
      }).then(() => {
        console.log('Success !');
      }).catch(e => {
        console.error('An error occurred :', e);
      });
    }
  }
  getNumberOfEmptyCells(tabledata: any) {
    this.empty = [];
    this.rem = 10 - tabledata.length;
    console.log('remainig data', this.rem);
    for (let emp = 0; emp <= this.rem; emp++) {
      this.empty.push({
        Counter: '',
        Ticket: ''
      });
    }
    console.log('emty data', this.empty);
  }
  getToken(tokenNumber: any) {
    if (tokenNumber < 10) {
      return tokenNumber = '000' + tokenNumber;
    }
    else if (tokenNumber < 100) {
      return tokenNumber = '00' + tokenNumber;
    } else {
      return tokenNumber = '0' + tokenNumber;
    }
  }


}
