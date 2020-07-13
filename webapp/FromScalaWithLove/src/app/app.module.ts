import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { ArrayEnhanced } from "scala-module";

import { AppComponent } from "./app.component";

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  providers: [ArrayEnhanced],
  bootstrap: [AppComponent],
})
export class AppModule {}
