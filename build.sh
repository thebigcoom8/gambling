#!/usr/bin/env bash
# build.sh - compile, package and zip the game for Linux / Windows / BSD.
# Usage: ./build.sh [version]     e.g. ./build.sh 0.1.0
set -euo pipefail
cd "$(dirname "$0")"

NAME=gambling
MAIN=gambling.Main
JAVA_RELEASE=17
VERSION="${1:-dev}"
OUT="dist/$NAME"

command -v javac >/dev/null || { echo "javac not found - install JDK $JAVA_RELEASE+"; exit 1; }
command -v jar   >/dev/null || { echo "jar not found - install a full JDK"; exit 1; }

echo "==> Cleaning"
rm -rf bin dist
mkdir -p bin "$OUT"

echo "==> Compiling (targeting Java $JAVA_RELEASE)"
javac --release "$JAVA_RELEASE" -encoding UTF-8 -d bin $(find src -name '*.java')

# The game reads this at startup (Updater.currentVersion) to know what it is.
echo "==> Stamping version: $VERSION"
printf '%s\n' "$VERSION" > bin/version.txt

echo "==> Building JAR"
jar cfe "$OUT/$NAME.jar" "$MAIN" -C bin .

echo "==> Writing launchers"
# Linux / BSD / macOS. If the updater staged gambling.jar.new, swap it in
# BEFORE java starts (a running jar can't be replaced on Windows).
cat > "$OUT/run.sh" <<'EOF'
#!/bin/sh
cd "$(dirname "$0")"
if [ -f gambling.jar.new ]; then
  mv -f gambling.jar.new gambling.jar
fi
exec java -jar gambling.jar "$@"
EOF
chmod +x "$OUT/run.sh"

# Windows: UTF-8 console for emojis, CRLF line endings, window stays open on exit
printf '%s\r\n' \
  '@echo off' \
  'chcp 65001 >nul' \
  'cd /d "%~dp0"' \
  'if exist gambling.jar.new move /y gambling.jar.new gambling.jar >nul' \
  'java -jar gambling.jar %*' \
  'pause' > "$OUT/run.bat"

cat > "$OUT/README.txt" <<EOF
Gambling $VERSION
Requires Java $JAVA_RELEASE or newer.

Windows:        double-click run.bat (use Windows Terminal for emojis)
Linux/BSD/Mac:  ./run.sh   (or: sh run.sh)
Any OS:         java -jar gambling.jar

Updates: the game checks for new releases on startup. Always start it with
run.sh / run.bat so a downloaded update is applied.
EOF

echo "==> Zipping"
ZIP="$NAME-$VERSION.zip"
if command -v zip >/dev/null; then
  (cd dist && zip -qr "$ZIP" "$NAME")
else
  python3 - "$ZIP" "$NAME" <<'EOF'
import os, sys, zipfile
zip_name, folder = sys.argv[1], sys.argv[2]
os.chdir("dist")
with zipfile.ZipFile(zip_name, "w", zipfile.ZIP_DEFLATED) as z:
    for root, _, files in os.walk(folder):
        for f in files:
            p = os.path.join(root, f)
            info = zipfile.ZipInfo.from_file(p)   # keeps the executable bit
            with open(p, "rb") as fh:
                z.writestr(info, fh.read(), zipfile.ZIP_DEFLATED)
EOF
fi

echo "==> Checking"
major=$(javap -v -cp bin "$MAIN" 2>/dev/null | awk '/major version/{print $3}')
echo "    class version: $major (61 = Java 17)"
[ "$major" = "61" ] || { echo "ERROR: wrong class version"; exit 1; }
jar tf "$OUT/$NAME.jar" | grep -qx 'version.txt' || { echo "ERROR: version.txt missing from jar"; exit 1; }
unzip -l "dist/$ZIP"

# keep build output out of git
touch .gitignore
for p in '*.jar'; do grep -qxF "$p" .gitignore || echo "$p" >> .gitignore; done

echo
echo "Done: dist/$ZIP"