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

echo "==> Building JAR"
jar cfe "$OUT/$NAME.jar" "$MAIN" -C bin .

echo "==> Writing launchers"
# Linux / BSD / macOS
cat > "$OUT/run.sh" <<'EOF'
#!/bin/sh
cd "$(dirname "$0")"
exec java -jar gambling.jar "$@"
EOF
chmod +x "$OUT/run.sh"

# Windows: UTF-8 console for emojis, CRLF line endings, window stays open on exit
printf '%s\r\n' \
  '@echo off' \
  'chcp 65001 >nul' \
  'cd /d "%~dp0"' \
  'java -jar gambling.jar %*' \
  'pause' > "$OUT/run.bat"

cat > "$OUT/README.txt" <<EOF
Gambling $VERSION
Requires Java $JAVA_RELEASE or newer.

Windows:        double-click run.bat (use Windows Terminal for emojis)
Linux/BSD/Mac:  ./run.sh   (or: sh run.sh)
Any OS:         java -jar gambling.jar
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
unzip -l "dist/$ZIP"

# keep build output out of git
echo
echo "Done: dist/$ZIP"
