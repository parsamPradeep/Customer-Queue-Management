import { Injectable } from '@angular/core';
import * as data from '../../assets/data/config.json';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private static configData: any = null;


  constructor() {
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

