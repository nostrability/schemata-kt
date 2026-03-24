# schemata-kt

[![Test](https://github.com/nostrability/schemata-kt/actions/workflows/test.yml/badge.svg)](https://github.com/nostrability/schemata-kt/actions/workflows/test.yml)
[![](https://jitpack.io/v/nostrability/schemata-kt.svg)](https://jitpack.io/#nostrability/schemata-kt)

Kotlin/JVM data package containing compiled [Nostr](https://nostr.com/) protocol JSON schemas from [nostrability/schemata](https://github.com/nostrability/schemata).

This is the Kotlin equivalent of [`schemata-rs`](https://github.com/nostrability/schemata-rs) — a vendored collection of 190 compiled JSON Schema (Draft-07) definitions covering Nostr event kinds, tags, protocol messages, and NIP-11.

## Related projects

| Project | Language | Role |
|---------|----------|------|
| [nostrability/schemata](https://github.com/nostrability/schemata) | JSON/JS | Canonical schema definitions |
| [schemata-rs](https://github.com/nostrability/schemata-rs) | Rust | Data crate (schemas + registry) |
| [schemata-validator-kt](https://github.com/nostrability/schemata-validator-kt) | Kotlin | Validator (this package + networknt) |
| [schemata-validator-rs](https://github.com/nostrability/schemata-validator-rs) | Rust | Validator (schemas + jsonschema) |

## Installation

### JitPack (recommended)

```kotlin
// settings.gradle.kts or build.gradle.kts
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.nostrability:schemata-kt:v0.1.1")
}
```

### Local composite build (for development)

```kotlin
// settings.gradle.kts
includeBuild("../schemata-kt")

// build.gradle.kts
dependencies {
    implementation("nostrability:schemata-kt")
}
```

### Look up schemas

```kotlin
import nostrability.schemata.Schemata

// Get a parsed schema by key
val schema = Schemata.get("kind1Schema")   // JsonElement?

// List all available keys
val keys = Schemata.keys()                 // Set<String>, 190 entries
```

### CI / test integration

This package is designed for CI and integration tests, not production hot paths. JSON Schema validation is slow by design.

```yaml
# .github/workflows/schema-check.yml
name: Schema Check
on: [push, pull_request]
jobs:
  validate:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17
      - run: ./gradlew test
```

## API

| Method | Description |
|--------|-------------|
| `Schemata.get(key)` | Returns parsed `JsonElement?` for the given schema key |
| `Schemata.keys()` | Returns `Set<String>` of all 190 registry keys |

Schemas are loaded lazily from classpath resources and cached after first access.

## Building

```bash
# One-time setup: generate Gradle wrapper (requires Gradle 8.10+ installed)
gradle wrapper

# Vendor schemas from sibling schemata checkout or /tmp/schemata-dist
./vendor-schemas.sh
# OR: ./gradlew vendorSchemas

# Build
./gradlew build
```

Requires JDK 17+. The `vendor-schemas.sh` script auto-detects the schemata dist directory
from `../schemata/dist` or `/tmp/schemata-dist`.

## License

GPL-3.0-or-later
