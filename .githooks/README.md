# Git Hooks for OutFront OMS

Lightweight pre-commit hooks to enforce coding standards — no external tools required.

## Hooks Included

| Hook | What it does |
|------|-------------|
| `pre-commit` | Checks staged Java files for `System.out.println` and field `@Autowired`, then runs `mvn test` |
| `commit-msg` | Validates commit message format: `type(scope): description` |

## Setup

```bash
bash .githooks/setup.sh
```

This runs `git config core.hooksPath .githooks` so Git uses these hooks automatically.

## Skipping Hooks

To bypass hooks for a single commit:

```bash
git commit --no-verify -m "chore(wip): work in progress"
```
