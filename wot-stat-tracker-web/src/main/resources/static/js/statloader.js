showLoadingGif();
updateData();

/////////////////////////////////////////////////////

function updateData() {
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
    const requestUrl = appendPartToCurrentUrl('currentStat');
    return axios.get(requestUrl)
        .then(response => processStatResponseDto(response.data))
        .catch(error => {
            console.error('#requestStatResponseDtoJson - ', error);
            showWarnAlert(error);
        });
}

function processStatResponseDto(statResponseDto) {
    console.log('Result of GET statResponseDtoJson:');
    console.log(statResponseDto);

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

}

function requestStatDeltaResponseDtoJson() {
    const requestUrl = appendPartToCurrentUrl('statDeltas');
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

}

function showLoadingGif() {
    document.getElementById("loading-div").style.display = "block";
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.display = "none";
}
