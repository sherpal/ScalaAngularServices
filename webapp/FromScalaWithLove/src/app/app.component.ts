import { Component } from "@angular/core";
import { ArrayEnhanced } from "scala-module";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "FromScalaWithLove";

  constructor(arrayEnhanced: ArrayEnhanced) {
    const x = [1, 2, 3, 1, 2];

    console.log(arrayEnhanced.distinct(x));
    console.log(arrayEnhanced.distinctBy(x, (e) => e % 2));
  }
}
