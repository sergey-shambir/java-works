#!/usr/bin/env bash

filepaths=$(find . -type f \( -name "*.java" \))
for filepath in $filepaths; do
    echo "Formatting ${filepath}..."
    clang-format -style=file -i "${filepath}"
done
