#!/usr/bin/env bash

mvn -pl wot-stat-tracker-domain clean package

echo 'Uploading wot-stat-tracker-domain...'
scp -i ~/.ssh/id-rsa-volia \
    ./wot-stat-tracker-domain/target/wot-stat-tracker-domain-0.0.1-SNAPSHOT.jar \
    admin@82.144.223.35:/opt/kerrrusha/wot-stat-tracker

echo 'Done.'
