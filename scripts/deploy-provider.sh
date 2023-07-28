#!/usr/bin/env bash

mvn -pl wot-stat-tracker-provider clean package

echo 'Uploading wot-stat-tracker-provider...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-provider/target/wot-stat-tracker-provider-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Restarting wot-stat-tracker-provider module...'
ssh -i ~/.ssh/id-rsa-volia admin@82.144.223.35 << EOF
pgrep wot-stat-tracker-provider | xargs kill -9
nohup java -jar wot-stat-tracker-provider-0.0.1-SNAPSHOT.jar > logs/provider.log &
EOF

echo 'Done.'
