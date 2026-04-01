#!/usr/bin/env bash
#
# Verifies sqlite-android-inspection: assemble, debug classpath, AAR SPI/classes.
# Usage (from repo root): ./scripts/verify-sqlite-android-inspection.sh
# Exit non-zero on any failure.

set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

echo "==> sqlite-android-inspection verification (repo: $ROOT)"
echo

echo "==> [1/5] Gradle assembleDebug"
./gradlew :sqlite-android-inspection:assembleDebug --no-daemon -q

echo "==> [2/5] Gradle assembleRelease"
./gradlew :sqlite-android-inspection:assembleRelease --no-daemon -q

echo "==> [3/5] debugCompileClasspath includes androidx.inspection:inspection"
if ! ./gradlew :sqlite-android-inspection:dependencies --configuration debugCompileClasspath --no-daemon -q 2>/dev/null | grep -q 'androidx.inspection:inspection'; then
  echo "ERROR: androidx.inspection:inspection not found on debugCompileClasspath (check snapshot repo / androidx.inspection.snapshot.buildId)" >&2
  exit 1
fi

AAR_DIR="$ROOT/sqlite-android-inspection/build/outputs/aar"
AAR=""
if [[ -d "$AAR_DIR" ]]; then
  AAR=$(ls -1 "$AAR_DIR"/*-debug.aar 2>/dev/null | head -1 || true)
fi

if [[ -z "${AAR:-}" || ! -f "$AAR" ]]; then
  echo "ERROR: expected debug AAR under $AAR_DIR" >&2
  exit 1
fi
echo "==> [4/5] AAR: $AAR"

TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

unzip -q -o "$AAR" -d "$TMP"

JAR="$TMP/classes.jar"
if [[ ! -f "$JAR" ]]; then
  echo "ERROR: classes.jar missing in AAR" >&2
  exit 1
fi

SPI_REL="META-INF/services/androidx.inspection.InspectorFactory"
if ! jar tf "$JAR" | grep -q "^${SPI_REL}\$"; then
  echo "ERROR: missing $SPI_REL in classes.jar" >&2
  exit 1
fi
SPI_CONTENT="$(unzip -p "$JAR" "$SPI_REL")"
if ! grep -q 'androidx\.sqlite\.inspection\.SqliteInspectorFactory' <<<"$SPI_CONTENT"; then
  echo "ERROR: SPI factory line not found in $SPI_REL" >&2
  printf '%s\n' "$SPI_CONTENT" >&2
  exit 1
fi
echo "    SPI OK: $(echo "$SPI_CONTENT" | tr -d '\n')"

for class in \
  'androidx/sqlite/inspection/SqliteInspector.class' \
  'androidx/sqlite/inspection/SqliteInspectorFactory.class'; do
  if ! jar tf "$JAR" | grep -q "^$class\$"; then
    echo "ERROR: missing $class in classes.jar" >&2
    exit 1
  fi
  echo "    class OK: $class"
done

echo
echo "==> [5/5] All checks passed. E2E: :sqlite-android-inspection:connectedDebugAndroidTest (see VENDOR.txt)."
