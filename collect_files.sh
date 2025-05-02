#!/bin/bash


if ! command -v java &> /dev/null; then
    echo "Java не найдена. Устанавливаем..."
    sudo apt update
    sudo apt install default-jdk -y
else
    echo "Java уже установлена"
fi
# Скрипт только запускает компилацию основного алгоритмa который реализован на java
# Команды и их функции я нашел на https://metanit.com/os/linux/12.8.php
