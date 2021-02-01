import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GenerateTokenComponent } from './generate-token/generate-token.component';
import { PrintTokenComponent } from './print-token/print-token.component';
import { LoginComponent } from './login/login.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'generate-token', component: GenerateTokenComponent },
  { path: 'print-token', component: PrintTokenComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
