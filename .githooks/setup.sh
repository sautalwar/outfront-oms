#!/bin/bash
# Configure Git to use the .githooks/ directory for this repository.
git config core.hooksPath .githooks
echo "✅ Git hooks configured! Using .githooks/ directory."
