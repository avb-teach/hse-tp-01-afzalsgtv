#!/bin/bash

if [[ -z "$1" || -z "$2" ]]; then
    echo "Usage: ./collect_files.sh /input/dir /output/dir"
    exit 1
fi

if [[ ! -f FileCollector.class ]]; then
    javac FileCollector.java || { echo "Ошибка компиляции Java"; exit 1; }
fi

java FileCollector "$1" "$2"
# Скрипт только запускает компилацию основного алгоритмa который реализован на java
# Команды и их функции я нашел на https://metanit.com/os/linux/12.8.php
