# Branch Protection Recommended Settings

Due to API schema restrictions on this repository, branch protection must be configured manually through the GitHub UI.

## Recommended Configuration for `master` Branch

### API Call (if supported in future)
```bash
gh api repos/sautalwar/outfront-oms/branches/master/protection \
  --method PUT \
  --input - << 'EOF'
{
  "required_status_checks": {
    "strict": true,
    "contexts": [
      "build",
      "api-tests",
      "dependency-check",
      "spotbugs-sast",
      "zap-dast",
      "CodeQL"
    ]
  },
  "enforce_admins": false,
  "required_pull_request_reviews": {
    "required_approving_review_count": 1
  },
  "restrictions": null
}
EOF
```

### Manual GitHub UI Steps

1. **Navigate to Repository Settings**
   - Go to https://github.com/sautalwar/outfront-oms/settings/branches
   - Click "Add rule" under "Branch protection rules"

2. **Basic Configuration**
   - Branch name pattern: `master`
   - Require a pull request before merging: ✅ Check
   - Require approvals: ✅ Check (set to 1)
   - Require status checks to pass before merging: ✅ Check
   - Require branches to be up to date before merging: ✅ Check (strict mode)

3. **Required Status Checks**
   - Select the following checks:
     - `build` — Maven compile, test, package
     - `api-tests` — Integration test suite
     - `dependency-check` — OWASP dependency scanning
     - `spotbugs-sast` — Static analysis (SpotBugs + FindSecBugs)
     - `zap-dast` — Dynamic application security testing
     - `CodeQL` — GitHub's security code scanning

4. **Additional Protections**
   - Dismiss stale pull request approvals when new commits are pushed: ✅ Check
   - Require approval of the most recent review: ✅ Check
   - Include administrators: ☐ Uncheck (allows admins to bypass)

5. **Save Changes**
   - Click "Create" or "Update" to apply

## Verification

After applying settings manually, verify via CLI:
```bash
gh api repos/sautalwar/outfront-oms/branches/master/protection
```

This command will return the protection configuration if applied successfully.

## References

- [GitHub REST API: Update branch protection](https://docs.github.com/en/rest/branches/branch-protection)
- [GitHub: Protecting important branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/managing-a-branch-protection-rule)
