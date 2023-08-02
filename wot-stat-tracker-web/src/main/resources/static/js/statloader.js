class XvmColorValue {

    constructor(orangeValue, yellowValue, greenValue, blueValue, violetValue) {
        this.orangeValue = orangeValue;
        this.yellowValue = yellowValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
        this.violetValue = violetValue;
    }

    getColorClassName(value) {
        if (value < this.orangeValue) {
            return "xvm-red";
        }
        if (value < this.yellowValue) {
            return "xvm-orange";
        }
        if (value < this.greenValue) {
            return "xvm-yellow";
        }
        if (value < this.blueValue) {
            return "xvm-green";
        }
        if (value < this.violetValue) {
            return "xvm-blue";
        }
        return "xvm-violet";
    }

    getBgColorClassName(value) {
        if (value < this.orangeValue) {
            return "bg-xvm-red";
        }
        if (value < this.yellowValue) {
            return "bg-xvm-orange";
        }
        if (value < this.greenValue) {
            return "bg-xvm-yellow";
        }
        if (value < this.blueValue) {
            return "bg-xvm-green";
        }
        if (value < this.violetValue) {
            return "bg-xvm-blue";
        }
        return "bg-xvm-violet";
    }

    getStartRankValue(value) {
        if (value < this.orangeValue) {
            return 0;
        }
        if (value < this.yellowValue) {
            return this.orangeValue;
        }
        if (value < this.greenValue) {
            return this.yellowValue;
        }
        if (value < this.blueValue) {
            return this.greenValue;
        }
        if (value < this.violetValue) {
            return this.blueValue;
        }
        return this.violetValue;
    }

    getEndRankValue(value) {
        if (value < this.orangeValue) {
            return this.orangeValue;
        }
        if (value < this.yellowValue) {
            return this.yellowValue;
        }
        if (value < this.greenValue) {
            return this.greenValue;
        }
        if (value < this.blueValue) {
            return this.blueValue;
        }
        if (value < this.violetValue) {
            return this.violetValue;
        }
        return -1;
    }
}

/////////////////////////////////////////////////////

const battlesXvmColorValue = new XvmColorValue(2000, 7000, 14000, 24000, 50000);
const wgrXvmColorValue = new XvmColorValue(3304, 5211, 7256, 9521, 10953);
const wn7XvmColorValue = new XvmColorValue(505, 865, 1225, 1635, 1995);
const wn8XvmColorValue = new XvmColorValue(588, 1095, 1688, 2578, 3584);
const winrateXvmColorValue = new XvmColorValue(0.4631, 0.4924, 0.526, 0.5785, 0.6355);

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
            showWarnAlert(error);
        });
}

function processStatResponseDto() {
    console.log(statResponseDto);
    if (!statDeltaResponseDto) {
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
    document.getElementById("warn-alert-message").innerHTML = error;
}

function setCurrentStat() {
    setMainStat();
    setXvmStat();
    setProgressionsStat();
}

function setMainStat() {
    document.getElementById("battles").innerHTML = statResponseDto.battles;
    document.getElementById("avg-damage").innerHTML = statResponseDto.avgDamage;
    document.getElementById("rating").innerHTML = statResponseDto.globalRating;
    document.getElementById("winrate").innerHTML = statResponseDto.winrate;
    document.getElementById("avg-xp").innerHTML = statResponseDto.avgExperience;
    document.getElementById("last-battle-time").innerHTML = statResponseDto.lastBattleTime;
    document.getElementById("next-update-time").innerHTML = statResponseDto.nextDataUpdateTime;
    document.getElementById("current-snapshot-time").innerHTML = statResponseDto.createdAt;
}

function setXvmStat() {
    let battlesXvm = document.getElementById("battles-xvm");
    battlesXvm.innerHTML = statResponseDto.battles;
    battlesXvm.className = battlesXvmColorValue.getColorClassName(statResponseDto.battles);

    let ratingXvm = document.getElementById("wgr-xvm");
    ratingXvm.innerHTML = statResponseDto.globalRating;
    ratingXvm.className = wgrXvmColorValue.getColorClassName(statResponseDto.globalRating);

    let wn7Xvm = document.getElementById("wn7-xvm");
    wn7Xvm.innerHTML = statResponseDto.wn7;
    wn7Xvm.className = wn7XvmColorValue.getColorClassName(statResponseDto.wn7);

    let wn8Xvm = document.getElementById("wn8-xvm");
    wn8Xvm.innerHTML = statResponseDto.wn8;
    wn8Xvm.className = wn8XvmColorValue.getColorClassName(statResponseDto.wn8);

    let winrateXvm = document.getElementById("winrate-xvm");
    winrateXvm.innerHTML = statResponseDto.winrate;
    winrateXvm.className = winrateXvmColorValue.getColorClassName(statResponseDto.winrateValue);
}

function setProgressionsStat() {
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

    startRankValueElem.innerHTML = startRankValue;
    endRankValueElem.innerHTML = endRankValue;
    updateBgColor(progressBarElem, xvmColorValue.getBgColorClassName(value));
    valueAbsElem.innerHTML = isPercentValue ? formatPercents(value) : value;
    valueRelElem.innerHTML = formatPercents(progressPercents);

    if (delta !== undefined) {
        deltaAbsElem.innerHTML = deltaFormatted;
        if (delta > 0) {
            progressBarElem.style.width = formatPercents(progressPercents - Math.abs(deltaPercents));
            progressBarDeltaElem.style.width = formatPercents(deltaPercents);
            updateBgColor(progressBarDeltaElem, "bg-green")

            deltaRelElem.innerHTML = "+" + formatPercents(deltaPercents);
            deltaRelElem.className = "delta-plus";
        } else if (delta < 0) {
            progressBarElem.style.width = formatPercents(progressPercents);
            progressBarDeltaElem.style.width = formatPercents(deltaPercents);
            updateBgColor(progressBarDeltaElem, "bg-red")

            deltaRelElem.innerHTML = formatPercents(deltaPercents);
            deltaRelElem.className = "delta-minus";
        } else {
            progressBarElem.style.width = formatPercents(progressPercents);
            progressBarDeltaElem.style.width = "0";

            deltaRelElem.innerHTML = "-";
            deltaRelElem.className = "delta-zero";
        }
    }

    // if player already got the max color rank
    if (endRankValue === -1) {
        progressBarElem.style.width = "100%";
        valueRelElem.innerHTML = "-";
        deltaRelElem.innerHTML = "-";
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
            showWarnAlert(error);
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
    document.getElementById("compared-to-snapshot-time").innerHTML = statDeltaResponseDto.previousStatCreationTimeFormatted;
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
    element.innerHTML = valueFormatted;
    element.className = className;
}

function showLoadingGif() {
    document.getElementById("loading-div").style.visibility = "visible";
    document.getElementById("loading-text").innerHTML = "Updating your stat...";
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.visibility = "hidden";
}
