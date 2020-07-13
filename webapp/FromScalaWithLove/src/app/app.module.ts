import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ArrayEnhanced, EmitRxObservable } from "scala-module";

import { AppComponent } from "./app.component";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  providers: [ArrayEnhanced, EmitRxObservable],
  bootstrap: [AppComponent],
})
export class AppModule {}
