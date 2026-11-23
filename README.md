# Gambling

A text-based gambling game in Java. :3

You have $0.64, your mother is in a coma, and a dark web listing is selling a cure for cancer for $1,000,000,000. It comes down in 60 days. Take a loan from the casino, win big, and don't run out of time. Who is the man with the cure, why is it so expensive, and why is it on the black market? You'll have to wait and see...

## Games
Blackjack, Craps, Roulette, Slots, Coin Flip, and a number guessing game.

## Play

You need **Java 17 or newer** (a JRE is enough to play).

1. Download the latest `gambling-<version>.zip` from the [Releases](../../releases) page and unzip it.
2. Run the launcher for your system:

| System | How to run |
|---|---|
| Windows | Double-click `run.bat` |
| Linux / BSD / macOS | `./run.sh` (or `sh run.sh`) |
| Any | `java -jar gambling.jar` |

### Installing Java
- **Windows:** install Temurin 17+ from [adoptium.net](https://adoptium.net)
- **Debian / Ubuntu:** `sudo apt install openjdk-17-jre`
- **FreeBSD:** `pkg install openjdk17`

### Emojis
The game uses emojis. On Windows, run it in **Windows Terminal** (the default on Windows 11); the old `cmd.exe` window may show boxes or garbled characters. On Linux and BSD, use a UTF-8 locale and a terminal with an emoji font such as Noto Color Emoji.

## Build from source

Requires JDK 17 or newer (the full JDK, not just the JRE) and a bash shell.

```
./build.sh 0.1.0
```

This compiles the game for Java 17, builds the JAR, writes the launchers, and produces `dist/gambling-0.1.0.zip` ready to upload as a release. Build output (`bin/`, `dist/`, `*.jar`) is git-ignored.

To compile and run directly without packaging:
```
mkdir -p bin
javac --release 17 -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin gambling.Main
```

## Structure
```
src/gambling/Main.java          game loop (entry point)
src/gambling/Story.java         story scenes
src/gambling/games/             Blackjack, Card, CoinFlip, Craps, NumberGame, Roulette, Slots
src/gambling/util/              Betting, Input
build.sh                        builds the JAR and the release zip
```

## Status
Work in progress. The story is still being written.