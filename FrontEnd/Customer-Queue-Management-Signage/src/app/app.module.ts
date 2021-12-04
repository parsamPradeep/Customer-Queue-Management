import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { SignageComponent } from './signage/signage.component';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { NotifierModule } from 'angular-notifier';
import { DatePipe } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from './shared/material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatCarouselModule } from '@ngmodule/material-carousel';
export const createTranslateLoader = (http: HttpClient) => {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
};

@NgModule({
  declarations: [
    AppComponent,
    SignageComponent
  ],
  imports: [
    NgbModule,
    HttpClientModule,
    MaterialModule,
    BrowserModule,
    NotifierModule,
    FormsModule, ReactiveFormsModule,
    MatCarouselModule.forRoot(),
    RouterModule.forRoot([
      { path: 'signage', component: SignageComponent }
    ]),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    BrowserAnimationsModule
  ],
  providers: [HttpClient, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }