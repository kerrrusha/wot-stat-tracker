#!/usr/bin/env bash

mvn -pl wot-stat-tracker-data clean package

echo 'Uploading wot-stat-tracker-data...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-data/target/wot-stat-tracker-data-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Done.'
