#!/usr/bin/env bash

mvn clean package

echo 'Uploading wot-stat-tracker-web...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-web/target/wot-stat-tracker-web-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Restarting wot-stat-tracker-web module...'
ssh -i ~/.ssh/id-rsa-volia admin@82.144.223.35 << EOF
pgrep wot-stat-tracker-web | xargs kill -9
nohup java -jar wot-stat-tracker-web-0.0.1-SNAPSHOT.jar > logs/web.log &
EOF

echo 'Done.'
