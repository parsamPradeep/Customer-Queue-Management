import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GenerateTokenComponent } from './generate-token/generate-token.component';
import { PrintTokenComponent } from './print-token/print-token.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'generate-token', component: GenerateTokenComponent },
  { path: 'print-token', component: PrintTokenComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
