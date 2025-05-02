#!/bin/bash
INPUT_DIR="$1"
OUTPUT_DIR="$2"

if [[ -z "$INPUT_DIR" || -z "$OUTPUT_DIR" ]]; then
  echo "Использование: ./collect_files.sh /путь/к/входной /путь/к/выходной"
  exit 1
fi

mkdir -p "$OUTPUT_DIR"

find "$INPUT_DIR" -type f | while read -r FILE_PATH; do
  FILE_NAME=$(basename "$FILE_PATH")

  DEST_PATH="$OUTPUT_DIR/$FILE_NAME"

  if [[ -e "$DEST_PATH" ]]; then
    BASE="${FILE_NAME%.*}"
    EXT="${FILE_NAME##*.}"

    COUNT=1

    while [[ -e "$OUTPUT_DIR/${BASE}_$COUNT.$EXT" ]]; do
      ((COUNT++))
    done
    DEST_PATH="$OUTPUT_DIR/${BASE}_$COUNT.$EXT"
  fi

  cp "$FILE_PATH" "$DEST_PATH"

  echo "Скопирован: $FILE_PATH -> $DEST_PATH"
done
# Очень много времени убил на исправление кода с Java, к сожалению так и не сработал, поэтому вот баш
# Скрипт только запускает компилацию основного алгоритмa который реализован на java
# Команды и их функции я нашел на https://metanit.com/os/linux/12.8.php, https://habr.com/ru/companies/ruvds/articles/325928/, 
#https://www.gnu.org/software/bash/manual/html_node/Bash-Conditional-Expressions.html

