# gambling
a game have been working on in java :3

## Structure
```
src/gambling/Main.java          game loop (entry point)
src/gambling/games/             Blackjack, Card, CoinFlip, Craps, NumberGame, Roulette, Slots
src/gambling/util/              Betting, Input
```

## Build & run
```
javac -d bin $(find src -name "*.java")
java -cp bin gambling.Main
```
