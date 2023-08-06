#!/usr/bin/env bash

mvn clean package

echo 'Uploading wot-stat-tracker-domain...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-domain/target/wot-stat-tracker-domain-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Uploading wot-stat-tracker-data...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-data/target/wot-stat-tracker-data-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Uploading wot-stat-tracker-provider...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-provider/target/wot-stat-tracker-provider-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Uploading wot-stat-tracker-web...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-web/target/wot-stat-tracker-web-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Done.'
