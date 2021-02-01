import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GenerateTokenComponent } from './generate-token/generate-token.component';
import { PrintTokenComponent } from './print-token/print-token.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './shared/material.module';
import { MaterialDesignFrameworkModule } from 'angular6-json-schema-form';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { MatProgressSpinnerModule } from '@angular/material';
import { LoginComponent } from './login/login.component';
import { DatePipe } from '@angular/common';
import { NotifierModule } from 'angular-notifier';
export const createTranslateLoader = (http: HttpClient) => {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}



@NgModule({
  declarations: [AppComponent, GenerateTokenComponent, PrintTokenComponent, LoginComponent],
  imports: [
    MatProgressSpinnerModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    MaterialDesignFrameworkModule,
    NotifierModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    })
  ],
  providers: [HttpClient, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
