import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from '../service/config.service';
@Injectable({
  providedIn: 'root'
})
export class TellerQueueService {
  constructor(private http: HttpClient, private configService: ConfigService) {
  }
  requestNewToken(finrequest: object): Observable<object> {
    return this.http.post(`${this.configService.getConfigData().default.requestNewTokenURL}`, finrequest);
  }

  serviceType(finrequestmodel: object): Observable<object> {
    return this.http.post(`${this.configService.getConfigData().default.serviceTypeURL}`, finrequestmodel);
  }

}
