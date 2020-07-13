import { Component } from "@angular/core";
import { ArrayEnhanced, EmitRxObservable } from "scala-module";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "FromScalaWithLove";

  constructor(arrayEnhanced: ArrayEnhanced, emitRx: EmitRxObservable) {
    const x = [1, 2, 3, 1, 2];

    console.log(arrayEnhanced.distinct(x));
    console.log(arrayEnhanced.distinctBy(x, (e) => e % 2));

    emitRx.naturalNumbers.forEach((e) => console.log(e));
  }
}
