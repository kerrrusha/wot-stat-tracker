import {
    battlesXvmColorValue,
    wgrXvmColorValue,
    wn7XvmColorValue,
    wn8XvmColorValue,
    winrateXvmColorValue
} from "./module/XvmColorValue.js";

/////////////////////////////////////////////////////

let statResponseDto = null;
let statDeltaResponseDto = null;

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
            console.log("All requests are finished, updating page data...");
            updatePageData();
            hideLoadingGif();
        });
}

function updatePageData() {
    processStatResponseDto();
    processStatDeltaResponseDto();
}

function requestStatResponseDtoJson() {
    const requestUrl = appendPartToCurrentUrl('current-stat');
    return axios.get(requestUrl)
        .then(response => statResponseDto = response.data)
        .catch(error => {
            console.error('#requestStatResponseDtoJson - ', error);
            let message = error.response.data.error;
            if (!message) {
                message = error.message;
            }
            showWarnAlert(message);
        });
}

function processStatResponseDto() {
    console.log(statResponseDto);
    if (!statResponseDto) {
        console.log("statDeltaResponseDto is null, stopping data updating.");
        return;
    }
    if (statResponseDto.error !== null && statResponseDto.error.length > 0) {
        console.error(statResponseDto.error);
        showWarnAlert(statResponseDto.error);
        return;
    }
    setCurrentStat();
}

function showWarnAlert(error) {
    document.getElementById("warn-alert").style.display = "block";
    document.getElementById("warn-alert-message").textContent = error;
}

function setCurrentStat() {
    setMainStat();
    setXvmStat();
    setProgressionsStat();
}

function setMainStat() {
    console.log("Setting main stats...");
    document.getElementById("battles").textContent = statResponseDto.battles;
    document.getElementById("avg-damage").textContent = statResponseDto.avgDamage;
    document.getElementById("rating").textContent = statResponseDto.globalRating;
    document.getElementById("winrate").textContent = statResponseDto.winrate;
    document.getElementById("avg-xp").textContent = statResponseDto.avgExperience;
    document.getElementById("last-battle-time").textContent = statResponseDto.lastBattleTime;
    document.getElementById("next-update-time").textContent = statResponseDto.nextDataUpdateTime;
    document.getElementById("current-snapshot-time").textContent = statResponseDto.createdAt;
    document.getElementById("wn7").textContent = statResponseDto.wn7;
    document.getElementById("trees-cut").textContent = statResponseDto.treesCut;
    document.getElementById("wn8").textContent = statResponseDto.wn8;
}

function setXvmStat() {
    console.log("Setting xvm stats...");
    let battlesXvm = document.getElementById("battles-xvm");
    battlesXvm.textContent = statResponseDto.battles;
    battlesXvm.className = battlesXvmColorValue.getColorClassName(statResponseDto.battles);

    let ratingXvm = document.getElementById("wgr-xvm");
    ratingXvm.textContent = statResponseDto.globalRating;
    ratingXvm.className = wgrXvmColorValue.getColorClassName(statResponseDto.globalRating);

    let wn7Xvm = document.getElementById("wn7-xvm");
    wn7Xvm.textContent = statResponseDto.wn7;
    wn7Xvm.className = wn7XvmColorValue.getColorClassName(statResponseDto.wn7);

    let wn8Xvm = document.getElementById("wn8-xvm");
    wn8Xvm.textContent = statResponseDto.wn8;
    wn8Xvm.className = wn8XvmColorValue.getColorClassName(statResponseDto.wn8);

    let winrateXvm = document.getElementById("winrate-xvm");
    winrateXvm.textContent = statResponseDto.winrate;
    winrateXvm.className = winrateXvmColorValue.getColorClassName(statResponseDto.winrateValue);
}

function setProgressionsStat() {
    console.log("Setting progression stats...");
    setConcreteProgressionStat(
        statResponseDto.wn8,
        statDeltaResponseDto.wn8Delta,
        statDeltaResponseDto.wn8DeltaFormatted,
        wn8XvmColorValue,
        document.getElementById("wn8-progression-bar"),
        document.getElementById("wn8-progression-bar-delta"),
        document.getElementById("wn8-start-rank-value"),
        document.getElementById("wn8-end-rank-value"),
        document.getElementById("wn8-progression-value-rel"),
        document.getElementById("wn8-progression-value-abs"),
        document.getElementById("wn8-progression-delta-rel"),
        document.getElementById("wn8-progression-delta-abs")
    )
    setConcreteProgressionStat(
        statResponseDto.wn7,
        statDeltaResponseDto.wn7Delta,
        statDeltaResponseDto.wn7DeltaFormatted,
        wn7XvmColorValue,
        document.getElementById("wn7-progression-bar"),
        document.getElementById("wn7-progression-bar-delta"),
        document.getElementById("wn7-start-rank-value"),
        document.getElementById("wn7-end-rank-value"),
        document.getElementById("wn7-progression-value-rel"),
        document.getElementById("wn7-progression-value-abs"),
        document.getElementById("wn7-progression-delta-rel"),
        document.getElementById("wn7-progression-delta-abs")
    )
    setConcreteProgressionStat(
        statResponseDto.winrateValue,
        statDeltaResponseDto.winrateDelta,
        statDeltaResponseDto.winrateDeltaFormatted,
        winrateXvmColorValue,
        document.getElementById("winrate-progression-bar"),
        document.getElementById("winrate-progression-bar-delta"),
        document.getElementById("winrate-start-rank-value"),
        document.getElementById("winrate-end-rank-value"),
        document.getElementById("winrate-progression-value-rel"),
        document.getElementById("winrate-progression-value-abs"),
        document.getElementById("winrate-progression-delta-rel"),
        document.getElementById("winrate-progression-delta-abs"),
        true
    )
    setConcreteProgressionStat(
        statResponseDto.globalRating,
        statDeltaResponseDto.ratingDelta,
        statDeltaResponseDto.ratingDeltaFormatted,
        wgrXvmColorValue,
        document.getElementById("wgr-progression-bar"),
        document.getElementById("wgr-progression-bar-delta"),
        document.getElementById("wgr-start-rank-value"),
        document.getElementById("wgr-end-rank-value"),
        document.getElementById("wgr-progression-value-rel"),
        document.getElementById("wgr-progression-value-abs"),
        document.getElementById("wgr-progression-delta-rel"),
        document.getElementById("wgr-progression-delta-abs")
    )
    setConcreteProgressionStat(
        statResponseDto.battles,
        statDeltaResponseDto.battlesDelta,
        statDeltaResponseDto.battlesDeltaFormatted,
        battlesXvmColorValue,
        document.getElementById("battles-progression-bar"),
        document.getElementById("battles-progression-bar-delta"),
        document.getElementById("battles-start-rank-value"),
        document.getElementById("battles-end-rank-value"),
        document.getElementById("battles-progression-value-rel"),
        document.getElementById("battles-progression-value-abs"),
        document.getElementById("battles-progression-delta-rel"),
        document.getElementById("battles-progression-delta-abs")
    )
}

function setConcreteProgressionStat(
    value, delta, deltaFormatted, xvmColorValue, progressBarElem, progressBarDeltaElem, startRankValueElem,
    endRankValueElem, valueRelElem, valueAbsElem, deltaRelElem, deltaAbsElem, isPercentValue
    ) {
    const startRankValue = xvmColorValue.getStartRankValue(value);
    const endRankValue = xvmColorValue.getEndRankValue(value);

    const progressPercents = (value - startRankValue) / (endRankValue - startRankValue);
    const deltaPercents = delta / (endRankValue - startRankValue);

    startRankValueElem.textContent = startRankValue;
    endRankValueElem.textContent = endRankValue;
    updateBgColor(progressBarElem, xvmColorValue.getBgColorClassName(value));
    valueAbsElem.textContent = isPercentValue ? formatPercents(value) : value;
    valueRelElem.textContent = formatPercents(progressPercents);

    if (delta !== undefined) {
        deltaAbsElem.textContent = deltaFormatted;
    }

    if (delta === undefined || delta === 0) {
        progressBarElem.style.width = formatPercents(progressPercents);
        progressBarDeltaElem.style.width = "0";

        deltaRelElem.textContent = "-";
        deltaRelElem.className = "delta-zero";
    } else if (delta > 0) {
        progressBarElem.style.width = formatPercents(progressPercents - Math.abs(deltaPercents));
        progressBarDeltaElem.style.width = formatPercents(deltaPercents);
        updateBgColor(progressBarDeltaElem, "bg-green")

        deltaRelElem.textContent = "+" + formatPercents(deltaPercents);
        deltaRelElem.className = "delta-plus";
    } else if (delta < 0) {
        progressBarElem.style.width = formatPercents(progressPercents);
        progressBarDeltaElem.style.width = formatPercents(deltaPercents);
        updateBgColor(progressBarDeltaElem, "bg-red")

        deltaRelElem.textContent = formatPercents(deltaPercents);
        deltaRelElem.className = "delta-minus";
    }

    // if player already got the max color rank
    if (endRankValue === -1) {
        endRankValueElem.textContent = "-";
        progressBarElem.style.width = "100%";
        valueRelElem.textContent = "-";
        deltaRelElem.textContent = "-";
        deltaRelElem.className = "delta-zero";
    }
}

function formatPercents(value) {
    return (value * 100).toFixed(2) + "%";
}

function updateBgColor(element, bgClassName) {
    const classNames = element.className.split(' ');

    classNames.pop();
    classNames.push(bgClassName);

    element.className = classNames.join(' ');
}

function requestStatDeltaResponseDtoJson() {
    const requestUrl = appendPartToCurrentUrl('stat-deltas');
    return axios.get(requestUrl)
        .then(response => statDeltaResponseDto = response.data)
        .catch(error => {
            if (error.response.status === 204) {
                console.log("No deltas exist.");
                return;
            }
            console.error('#requestStatDeltaResponseDtoJson - ', error);
            let message = error.response.data.error;
            if (!message) {
                message = error.message;
            }
            showWarnAlert(message);
        });
}

function appendPartToCurrentUrl(part) {
    const currentUrl = window.location.href.charAt(window.location.href - 1) !== '/'
        ? window.location.href + '/'
        : window.location.href;
    return  currentUrl + part;
}

function processStatDeltaResponseDto() {
    console.log(statDeltaResponseDto);
    if (!statDeltaResponseDto) {
        console.log("statDeltaResponseDto is null, stopping data updating.");
        return;
    }
    if (statDeltaResponseDto.error !== null && statDeltaResponseDto.error.length > 0) {
        console.error(statDeltaResponseDto.error);
        showWarnAlert(statDeltaResponseDto.error);
        return;
    }
    setStatDeltas();
}

function setStatDeltas() {
    setStatDeltaValueWithClassName("battles-delta", statDeltaResponseDto.battlesDeltaFormatted, "delta-zero");
    setStatDeltaValue("avg-damage-delta", statDeltaResponseDto.avgDamageDelta, statDeltaResponseDto.avgDamageDeltaFormatted);
    setStatDeltaValue("rating-delta", statDeltaResponseDto.ratingDelta, statDeltaResponseDto.ratingDeltaFormatted);
    setStatDeltaValue("winrate-delta", statDeltaResponseDto.winrateDelta, statDeltaResponseDto.winrateDeltaFormatted);
    setStatDeltaValue("avg-xp-delta", statDeltaResponseDto.avgExperienceDelta, statDeltaResponseDto.avgExperienceDeltaFormatted);
    setStatDeltaValue("wn7-delta", statDeltaResponseDto.wn7Delta, statDeltaResponseDto.wn7DeltaFormatted);
    setStatDeltaValue("trees-cut-delta", statDeltaResponseDto.treesCutDelta, statDeltaResponseDto.treesCutDeltaFormatted);
    setStatDeltaValue("wn8-delta", statDeltaResponseDto.wn8Delta, statDeltaResponseDto.wn8DeltaFormatted);
    document.getElementById("compared-to-snapshot-time").textContent = statDeltaResponseDto.previousStatCreationTimeFormatted;
    document.getElementById("trees-cut-delta").className = "delta-zero";
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
    element.textContent = valueFormatted;
    element.className = className;
}

function showLoadingGif() {
    document.getElementById("loading-div").style.visibility = "visible";
    document.getElementById("loading-text").textContent = "Updating your stat...";
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.visibility = "hidden";
}
