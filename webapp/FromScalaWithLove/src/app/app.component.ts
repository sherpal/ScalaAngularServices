import { Component } from "@angular/core";
import { ZIOService, withzio } from "scala-module";
import { of, Observable, Subject } from "rxjs";
import { delay, tap, flatMap } from "rxjs/operators";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "FromScalaWithLove";

  public readonly completionStates$: Subject<
    withzio.CompletionState<number>
  > = new Subject();
  public readonly progress$: Observable<number>;
  public readonly result$: Observable<Array<number>>;

  public cancel: () => void = () => {};

  constructor(private zioService: ZIOService) {
    this.progress$ = this.completionStates$.pipe(flatMap((cs) => cs.progress));
    this.result$ = this.completionStates$.pipe(flatMap((cs) => cs.result));
  }

  public start(): void {
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

    const completionState = this.zioService.foreachParN(
      [...new Array(30).keys()],
      program,
      2,
      3
    );

    this.cancel = completionState.cancel;

    this.completionStates$.next(completionState);
  }
}
