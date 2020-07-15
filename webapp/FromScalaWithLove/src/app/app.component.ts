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

  constructor(zioService: ZIOService) {
    const program = (j: number) =>
      of(j).pipe(
        delay(1000),
        tap((j) => {
          if (Math.random() > 0.9) {
            console.warn(`boom ${j}`);
            throw new Error(`${j} kaboom`);
          }
        }),
        tap((e) => console.log(e))
      );

    const completionState = zioService.foreachParN(
      [...new Array(30).keys()],
      program,
      2,
      3
    );

    completionState.progress.forEach((p) => console.log("progress", p));
    completionState.result.forEach((r) => console.log("result", r));

    // setTimeout(() => {
    //   completionState.cancel();
    // }, 1500);
  }
}
