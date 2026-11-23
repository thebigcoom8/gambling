# Gambling

A text-based gambling game in Java. :3

You have $0.64, your mother is in a coma, and a dark web listing is selling a cure for cancer for $1,000,000,000. It comes down in 60 days. Take a loan from the casino, win big, and don't run out of time. Who is the man with the cure, why is it so expensive, and why is it on the black market? You'll have to wait and see...

## Games
Blackjack, Craps, Roulette, Slots, Coin Flip, and a number guessing game.

## Structure
```
src/gambling/Main.java          game loop (entry point)
src/gambling/Story.java         story scenes
src/gambling/games/             Blackjack, Card, CoinFlip, Craps, NumberGame, Roulette, Slots
src/gambling/util/              Betting, Input
```

## Requirements
JDK 17 or newer.

## Build & run
```
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin gambling.Main
```

## Status
Work in progress. The story is still being written.