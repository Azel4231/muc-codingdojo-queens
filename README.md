# muc-codingdojo-queens

A naive solver for the eight queens puzzle in Clojure.

Runs for several minutes for a 8x8 chess board.

# How it works

* Enumerates all combinations of queens where each queen already sits in their own row
* Filters out all combinations that contain queens in the same column and/or diagonals

## License

MIT