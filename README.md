# Chess Fetch

A premium Android app for searching Chess.com player profiles, ratings, and recent games.

**Package:** `com.rohan.chessfetch`

---

## Stack

- Kotlin + Jetpack Compose
- Material Design 3 — dark gold theme
- Hilt for dependency injection
- Retrofit + OkHttp for Chess.com API
- Navigation Compose
- Coil for async image loading

## Screens

| Screen | Description |
|--------|-------------|
| Home | Search any Chess.com username |
| Player | Profile, title, ratings (Bullet/Blitz/Rapid/Daily), tactics, followers |
| Games | Recent games list with W/L/D indicator, ratings, time control |

## API

Uses the public [Chess.com API](https://www.chess.com/news/view/published-data-api) — no authentication required.

## Building locally

```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

## CI/CD — GitHub Actions

Every push to `main`, `dev`, or `release/**` branches:

1. Builds both Debug and Release APKs
2. Bumps version from Git tags (defaults to `1.0.0`)
3. Renames APKs: `ChessFetch-v{version}-build.{n}-release.apk`
4. Creates a GitHub Release with both APKs attached

### Optional — Production signing

Add these repository secrets to use a real keystore:

| Secret | Value |
|--------|-------|
| `KEYSTORE_BASE64` | Base64-encoded `.jks` file |
| `SIGNING_STORE_PASSWORD` | Keystore password |
| `SIGNING_KEY_ALIAS` | Key alias |
| `SIGNING_KEY_PASSWORD` | Key password |

Without these, the CI generates an ephemeral keystore per build (suitable for sideloading, not Play Store).

## Version bumping

Create a Git tag to bump the version:

```bash
git tag v1.2.0
git push origin v1.2.0
```

The next CI build will pick up `1.2.0` as the version name.
