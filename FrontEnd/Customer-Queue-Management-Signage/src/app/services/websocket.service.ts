import { Injectable } from '@angular/core';
import SockJs from 'sockjs-client';
import Stomp from 'stompjs';
import { ConfigService } from '../services/config.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class WebSocketService {
  constructor(private configService: ConfigService, private http: HttpClient) {
  }

  connect() {
    const socket = new SockJs(this.configService.getConfigData().baseURL);

    const stompClient = Stomp.over(socket);
    return stompClient;
  }
  serviceType(finrequest: object): Observable<any> {
    return this.http.post(this.configService.getConfigData().serviceTypeURL, finrequest);
  }
  getCampainURLForBranch(finrequest: object): Observable<any> {
    return this.http.post(this.configService.getConfigData().campainImage, finrequest);
  }
}
