#!/bin/bash

sudo apt update
sudo apt install -y default-jdk


if [[ $# -lt 2 ]]; then
    echo "Usage: ./collect_files.sh /input/dir /output/dir [--max_depth N]"
    exit 1
fi


if [[ ! -f FileCollector.class ]]; then
    javac FileCollector.java || { echo "Ошибка компиляции Java"; exit 1; }
fi
java FileCollector "$@"
# Скрипт только запускает компилацию основного алгоритмa который реализован на java
# Команды и их функции я нашел на https://metanit.com/os/linux/12.8.php
