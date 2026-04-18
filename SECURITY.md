# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.x     | :white_check_mark: |

## Reporting a Vulnerability

If you discover a security vulnerability, please report it responsibly:

1. **Do NOT** open a public GitHub issue
2. Email: security@outfrontmedia.com
3. Include:
   - Description of the vulnerability
   - Steps to reproduce
   - Expected vs actual behavior
   - Impact assessment

We will acknowledge receipt within 48 hours and provide a detailed response within 5 business days.

## Security Measures

This project employs the following security measures:

### Automated Security Scanning
- **CodeQL** — Static analysis for Java/Kotlin on every PR and weekly schedule
- **OWASP Dependency Check** — SCA scanning for vulnerable dependencies (CVSS >= 7.0 threshold)
- **SpotBugs + FindSecBugs** — SAST analysis targeting OWASP Top 10
- **OWASP ZAP** — DAST baseline scanning against running application
- **Dependabot** — Automated dependency updates for Maven, Docker, and GitHub Actions

### Branch Protection
- All changes require PR review
- CI build and tests must pass
- CodeQL security scan must pass
- API integration tests must pass

### OWASP Top 10 (2021) Coverage

| Category | Automated Coverage |
|----------|-------------------|
| A01 Broken Access Control | ZAP (DAST) |
| A02 Cryptographic Failures | FindSecBugs (SAST) |
| A03 Injection | FindSecBugs (SAST) + ZAP (DAST) |
| A04 Insecure Design | Manual review |
| A05 Security Misconfiguration | ZAP (DAST) + Dependency Check |
| A06 Vulnerable Components | Dependency Check (SCA) + Dependabot |
| A07 Authentication Failures | ZAP (DAST) |
| A08 Software Integrity | FindSecBugs (SAST) |
| A09 Logging Failures | Manual review |
| A10 SSRF | FindSecBugs (SAST) |

## Dependencies

Security updates are managed via Dependabot with weekly scanning across:
- Maven dependencies
- Docker base images
- GitHub Actions versions
