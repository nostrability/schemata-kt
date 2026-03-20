#!/bin/bash
# Vendor schemas from schemata dist into resources.
# Usage: ./vendor-schemas.sh [dist-dir]
# Default: looks for ../schemata/dist, then /tmp/schemata-dist
set -euo pipefail

if [ -n "${1:-}" ]; then
    SCHEMATA_DIST="$1"
elif [ -d "../schemata/dist" ]; then
    SCHEMATA_DIST="../schemata/dist"
elif [ -d "/tmp/schemata-dist" ]; then
    SCHEMATA_DIST="/tmp/schemata-dist"
else
    echo "ERROR: No schemata dist found. Provide path as argument." >&2
    echo "  ./vendor-schemas.sh /path/to/schemata/dist" >&2
    exit 1
fi

RESOURCES_DIR="src/main/resources/schemas"

echo "Vendoring schemas from ${SCHEMATA_DIST} ..."

rm -rf "${RESOURCES_DIR}/nips" "${RESOURCES_DIR}/mips" "${RESOURCES_DIR}/@"
mkdir -p "${RESOURCES_DIR}"

# Copy each directory if it exists in the source
for dir in nips mips @; do
    if [ -d "${SCHEMATA_DIST}/${dir}" ]; then
        cp -r "${SCHEMATA_DIST}/${dir}" "${RESOURCES_DIR}/${dir}"
        echo "  Copied ${dir}/"
    else
        echo "  Skipped ${dir}/ (not found in source)"
    fi
done

echo "Vendored $(find "${RESOURCES_DIR}" -name '*.json' | wc -l) schema files."
