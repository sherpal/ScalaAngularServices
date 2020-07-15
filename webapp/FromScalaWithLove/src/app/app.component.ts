import { Component } from "@angular/core";
import { ZIOService } from "scala-module";
import { of } from "rxjs";
import { delay, tap } from "rxjs/operators";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "FromScalaWithLove";

  constructor(zioService: ZIOService) {}
}
