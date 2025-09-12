#!/bin/bash

# Dừng khi có lỗi
set -e

# Đường dẫn tới file testng.xml (cùng cấp với pom.xml)
TESTNG_XML="testng.xml"

# Kiểm tra file testng.xml tồn tại không
if [ ! -f "$TESTNG_XML" ]; then
  echo "❌ Không tìm thấy $TESTNG_XML trong thư mục hiện tại!"
  exit 1
fi

echo "🚀 Bắt đầu chạy TestNG suite: $TESTNG_XML ..."

# Clean và run Maven test với testng.xml
mvn clean test -DsuiteXmlFile="$TESTNG_XML"

echo "✅ Hoàn thành chạy test!"