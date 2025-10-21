# SecuritEase — BACK-END AUTOMATION TASK (Fixed v2)

This version adds the `fields` query param required by the API and validates against a **minimal schema** that matches those fields.

## Run
```
mvn -U clean test
mvn allure:report
```

## Optional soft assert for SASL
```
# Windows (new shell):  setx SOFT_ASSERT_SASL true
# macOS/Linux:          export SOFT_ASSERT_SASL=true
```

## Docker
```
docker build -t securitease-backend .
docker run --rm -v "$PWD/target:/app/target" securitease-backend
```

---

### Notes on country count (195)
The API returns territories & regions (~250). We filter to **independent or UN member** countries (fields `independent` or `unMember` = `true`) which yields **195**.

### SASL assertion mode
- Default is **soft** (passes build, prints warning).  
- To enforce a **hard** fail if SASL is missing, set:
  ```
  # Windows (new shell):  setx FORCE_HARD_SASL true
  # macOS/Linux:          export FORCE_HARD_SASL=true
  ```


---

## GitHub setup

1. **Create a repo** on GitHub (e.g. `securitease-backend-automation`).
2. Initialize git and push:
   ```bash
   git init
   git add .
   git commit -m "chore: initial backend automation (QE)"
   git branch -M main
   git remote add origin https://github.com/<your-username>/securitease-backend-automation.git
   git push -u origin main
   ```
3. **CI badge** (paste at top of this README after first heading):
   ```markdown
   ![CI](https://github.com/<your-username>/securitease-backend-automation/actions/workflows/ci.yml/badge.svg)
   ```
4. **PR flow**: open a feature branch, push, and create a PR — CI runs automatically on push/PR.

### Notes
- CI enforces `FORCE_HARD_SASL=true` so SASL must appear; switch to soft locally via `setx SOFT_ASSERT_SASL true` or unset FORCE_HARD_SASL.
- Artifacts uploaded: Surefire + Allure results.


---

## Codespaces

Click **Code ▸ Create codespace on main** in GitHub. This repo includes a `.devcontainer/devcontainer.json` that:
- Uses Java 17 image
- Installs helpful VS Code extensions
- Automatically runs the test suite after container build

## Release flow

To cut a release:
1. Tag your commit (semantic style): `git tag v1.0.0 && git push --tags`
2. Or run the workflow manually in **Actions ▸ release ▸ Run workflow**.

The release workflow will:
- Run tests (with `FORCE_HARD_SASL=true`)
- Generate Allure HTML
- Publish a GitHub Release and attach a `securitease-backend-automation-artifacts.tgz` bundle with:
  - `dist/surefire-reports/`
  - `dist/allure-results/`
  - `dist/allure-report/` (static HTML)
