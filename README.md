# gambling
a game i've been working on in java :3. there is a story now, you need $1B within 60 days or your mother dies, who is this man with the cure and why is it so expensive, and why is it on the black market... you have to wait and see...

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
