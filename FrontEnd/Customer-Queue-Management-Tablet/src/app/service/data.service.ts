import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class DataService {

    private data = [];

    constructor(private httpService: HttpClient) {

    }
    setOption(option, value) {
        this.data[option] = value;
    }

    getOption() {
        return this.data;
    }

}
