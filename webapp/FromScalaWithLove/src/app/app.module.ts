import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import {
  ArrayEnhanced,
  EmitRxObservable,
  UserService,
  ZIOService,
} from "scala-module";

import { AppComponent } from "./app.component";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  providers: [ArrayEnhanced, EmitRxObservable, UserService, ZIOService],
  bootstrap: [AppComponent],
})
export class AppModule {}
