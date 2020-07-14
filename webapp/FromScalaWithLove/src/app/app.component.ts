import { Component } from "@angular/core";
import {
  ArrayEnhanced,
  EmitRxObservable,
  User,
  UserService,
} from "scala-module";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "FromScalaWithLove";

  constructor(
    arrayEnhanced: ArrayEnhanced,
    emitRx: EmitRxObservable,
    userService: UserService
  ) {
    const x = [1, 2, 3, 1, 2];

    console.log(arrayEnhanced.distinct(x));
    console.log(arrayEnhanced.distinctBy(x, (e) => e % 2));

    emitRx.naturalNumbers.forEach((e) => console.log(e));

    console.log(
      userService.usersByDateOfBirth([
        new User(
          "id1",
          "Alice",
          new Date("1990-01-05"),
          new Date("2020-07-15")
        ),
        new User("id2", "Bob", null, new Date("2020-07-14")),
        new User(
          "id3",
          "Charlie",
          new Date("1990-07-04"),
          new Date("2020-06-14")
        ),
        new User("id4", "Dean", new Date("1991-09-18"), new Date("2020-07-16")),
      ])
    );
  }
}
