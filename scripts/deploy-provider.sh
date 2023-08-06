#!/usr/bin/env bash

mvn -pl wot-stat-tracker-provider clean package

echo 'Uploading wot-stat-tracker-provider...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-provider/target/wot-stat-tracker-provider-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Done.'
