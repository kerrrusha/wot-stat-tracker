#!/usr/bin/env bash

mvn clean package

echo 'Uploading wot-stat-tracker-data...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-data/target/wot-stat-tracker-data-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Restarting wot-stat-tracker-data module...'
ssh -i ~/.ssh/id-rsa-volia admin@82.144.223.35 << EOF
pgrep wot-stat-tracker-data | xargs kill -9
nohup java -jar wot-stat-tracker-data-0.0.1-SNAPSHOT.jar > logs/data.log &
EOF

echo 'Done.'
