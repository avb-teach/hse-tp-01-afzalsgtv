#!/bin/bash

if [[ -z "$1" || -z "$2" ]]; then
    echo "Usage: ./collect_files.sh /input/dir /output/dir"
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo "Java is not installed. Installing OpenJDK..."
    sudo apt-get update
    sudo apt-get install -y default-jdk
fi

if [[ ! -f FileCollector.class ]]; then
    javac FileCollector.java || { echo "Ошибк Java"; exit 1; }
fi

java FileCollector "$1" "$2"

# Источник команд Bash: https://metanit.com/os/linux/12.8.php
# Скрипт только запускает компилацию основного алгоритмa который реализован на java
# Команды и их функции я нашел на https://metanit.com/os/linux/12.8.php
