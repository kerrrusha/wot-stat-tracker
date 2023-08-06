#!/usr/bin/env bash

mvn -pl wot-stat-tracker-web clean package

echo 'Uploading wot-stat-tracker-web...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-web/target/wot-stat-tracker-web-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Done.'
