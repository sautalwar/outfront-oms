#!/usr/bin/env bash
set -e

echo "========================================="
echo " OutFront OMS — Dev Container Setup"
echo "========================================="

# Pre-build and validate the Maven project
echo ""
echo "▶ Running Maven build (mvn clean verify)..."
mvn clean verify -B -q
echo "✅ Maven build succeeded."

# Install MCP server dependencies if present
if [ -f "mcp-server/package.json" ]; then
  echo ""
  echo "▶ Installing MCP server dependencies..."
  cd mcp-server && npm install && cd ..
  echo "✅ MCP server dependencies installed."
fi

echo ""
echo "========================================="
echo " ✅ Dev Container is ready!"
echo ""
echo " Useful commands:"
echo "   mvn spring-boot:run        — Start the app on http://localhost:8080"
echo "   mvn test                   — Run tests"
echo "   Swagger UI                 — http://localhost:8080/swagger-ui.html"
echo "   H2 Console                 — http://localhost:8080/h2-console"
echo "========================================="
