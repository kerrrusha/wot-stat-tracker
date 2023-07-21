updateData();

/////////////////////////////////////////////////////

function updateData() {
    showLoadingGif();

    let requestStatResponsePromise = requestStatResponseDtoJson();
    let requestStatDeltaResponsePromise = requestStatDeltaResponseDtoJson();

    waitForAllRequestsFinished([requestStatResponsePromise, requestStatDeltaResponsePromise]);
}

function waitForAllRequestsFinished(requestPromises) {
    Promise.all(requestPromises)
        .then(() => {
            console.log("All requests are finished");
            hideLoadingGif();
        });
}

function requestStatResponseDtoJson() {
    const requestUrl = appendPartToCurrentUrl('current-stat');
    return axios.get(requestUrl)
        .then(response => processStatResponseDto(response.data))
        .catch(error => {
            console.error('#requestStatResponseDtoJson - ', error);
            showWarnAlert(error);
        });
}

function processStatResponseDto(statResponseDto) {
    if (statResponseDto.error !== null && statResponseDto.error.length > 0) {
        console.error(statResponseDto.error);
        showWarnAlert(statResponseDto.error);
        return;
    }
    setCurrentStat(statResponseDto);
}

function showWarnAlert(error) {
    document.getElementById("warn-alert").style.display = "block";
    document.getElementById("warn-alert-message").innerHTML = error;
}

function setCurrentStat(statResponseDto) {
    document.getElementById("battles").innerHTML = statResponseDto.battles;
    document.getElementById("avg-damage").innerHTML = statResponseDto.avgDamage;
    document.getElementById("rating").innerHTML = statResponseDto.globalRating;
    document.getElementById("winrate").innerHTML = statResponseDto.winrate;
    document.getElementById("avg-xp").innerHTML = statResponseDto.avgExperience;
    document.getElementById("last-battle-time").innerHTML = statResponseDto.lastBattleTime;
    document.getElementById("next-update-time").innerHTML = statResponseDto.nextDataUpdateTime;
    document.getElementById("current-snapshot-time").innerHTML = statResponseDto.createdAt;
    document.getElementById("battles-xvm").innerHTML = statResponseDto.battles;
    document.getElementById("wgr-xvm").innerHTML = statResponseDto.globalRating;
    document.getElementById("wn7-xvm").innerHTML = statResponseDto.wn7;
    document.getElementById("wn8-xvm").innerHTML = statResponseDto.wn8;
    document.getElementById("winrate-xvm").innerHTML = statResponseDto.winrate;
}

function requestStatDeltaResponseDtoJson() {
    const requestUrl = appendPartToCurrentUrl('stat-deltas');
    return axios.get(requestUrl)
        .then(response => processStatDeltaResponseDto(response.data))
        .catch(error => {
            console.error('#requestStatDeltaResponseDtoJson - ', error);
            showWarnAlert(error);
        });
}

function appendPartToCurrentUrl(part) {
    const currentUrl = window.location.href.charAt(window.location.href - 1) !== '/'
        ? window.location.href + '/'
        : window.location.href;
    return  currentUrl + part;
}

function processStatDeltaResponseDto(statDeltaResponseDto) {
    if (statDeltaResponseDto.error !== null && statDeltaResponseDto.error.length > 0) {
        console.error(statDeltaResponseDto.error);
        showWarnAlert(statDeltaResponseDto.error);
        return;
    }
    setStatDeltas(statDeltaResponseDto);
}

function setStatDeltas(statDeltaResponseDto) {
    setStatDeltaValueWithClassName("battles-delta", statDeltaResponseDto.battlesDeltaFormatted, "delta-zero");
    setStatDeltaValue("avg-damage-delta", statDeltaResponseDto.avgDamageDelta, statDeltaResponseDto.avgDamageDeltaFormatted);
    setStatDeltaValue("rating-delta", statDeltaResponseDto.ratingDelta, statDeltaResponseDto.ratingDeltaFormatted);
    setStatDeltaValue("winrate-delta", statDeltaResponseDto.winrateDelta, statDeltaResponseDto.winrateDeltaFormatted);
    setStatDeltaValue("avg-xp-delta", statDeltaResponseDto.avgExperienceDelta, statDeltaResponseDto.avgExperienceDeltaFormatted);
    setStatDeltaValue("wn7-delta", statDeltaResponseDto.wn7Delta, statDeltaResponseDto.wn7DeltaFormatted);
    setStatDeltaValue("trees-cut-delta", statDeltaResponseDto.treesCutDelta, statDeltaResponseDto.treesCutDeltaFormatted);
    setStatDeltaValue("wn8-delta", statDeltaResponseDto.wn8Delta, statDeltaResponseDto.wn8DeltaFormatted);
    document.getElementById("compared-to-snapshot-time").innerHTML = statDeltaResponseDto.previousStatCreationTimeFormatted;
}

function setStatDeltaValue(elementId, value, valueFormatted) {
    let className = "delta-zero";
    if (value > 0) {
        className = "delta-plus";
    } else if (value < 0) {
        className = "delta-minus";
    }
    setStatDeltaValueWithClassName(elementId, valueFormatted, className);
}

function setStatDeltaValueWithClassName(elementId, valueFormatted, className) {
    let element = document.getElementById(elementId);
    element.innerHTML = valueFormatted;
    element.className = className;
}

function showLoadingGif() {
    document.getElementById("loading-div").style.display = "block";
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.display = "none";
}
