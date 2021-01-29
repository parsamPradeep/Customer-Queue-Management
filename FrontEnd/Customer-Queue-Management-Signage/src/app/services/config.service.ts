import { Injectable } from '@angular/core';
import * as data from '../../assets/data/config.json';
import { SessionService } from 'finflowz-ui-framework-services';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private static configData: any = null;


  constructor(private sessionService: SessionService) {
    if (!ConfigService.configData) {
      ConfigService.configData = data.default;
    }
  }
  public getConfigData(): any {

    if (ConfigService.configData === null) {
      ConfigService.configData = data;
    }
    return ConfigService.configData;
  }

}

