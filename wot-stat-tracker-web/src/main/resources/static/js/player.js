const PLAYER_NOT_EXISTS_IN_GAME = "Such player does not exists in WoT in-game server.";

let PROVIDER_PATH = "provider/";
let RELATIVE_PATH = "{region}/player/";
const IS_VALID_ENDPOINT = "/is-valid";
const EXISTS_IN_DB_ENDPOINT = "/exists-in-db";
const EXISTS_IN_GAME_ENDPOINT = "/exists-in-game";
const FETCH_PLAYER_ENDPOINT = "/fetch-player";

function submitForm(event) {
    event.preventDefault();
    hideWarnAlert();

    const region = document.getElementById("region").value;
    RELATIVE_PATH = RELATIVE_PATH.replace("{region}", region);

    const nickname = document.getElementById("nickname").value;

    showLoadingGif("Checking validity of " + nickname + " nickname...");
    const isValidRequestUrl = appendPartToCurrentUrl(RELATIVE_PATH + nickname + IS_VALID_ENDPOINT);
    axios.get(isValidRequestUrl)
        .then(response => processIsValidResponse(response.data, response.status, nickname))
        .catch(error => {
            console.error('Get '+isValidRequestUrl+' - ', error);
            hideLoadingGif();
            showWarnAlert(error);
        });
}

function showWarnAlert(error) {
    let message;
    try {
        if (!message) message = error.response.data;
    } catch (ignored) {}
    try {
        if (!message) message = error.message;
    } catch (ignored) {}
    if (!message) {
        message = error;
    }

    document.getElementById("warn-alert").style.display = "block";
    document.getElementById("warn-alert-message").textContent = message;
}

function hideWarnAlert() {
    document.getElementById("warn-alert").style.display = "none";
}

function processIsValidResponse(data, status, nickname) {
    hideLoadingGif();
    if (data !== true || status !== 200) {
        console.warn(data);
        console.warn(status);
        return;
    }

    showLoadingGif("Checking if " + nickname + " exists in our database...");
    const existsRequestUrl = appendPartToCurrentUrl(RELATIVE_PATH + nickname + EXISTS_IN_DB_ENDPOINT);
    axios.get(existsRequestUrl)
        .then(response => processExistsInDbResponse(response.data, response.status, nickname))
        .catch(error => {
            hideLoadingGif();
            console.error('Get '+existsRequestUrl+' - ', error);
            showWarnAlert(error);
        });
}

function processExistsInDbResponse(exists, status, nickname) {
    hideLoadingGif();
    if (status !== 200) {
        console.warn(exists);
        console.warn(status);
        return;
    }
    if (!exists) {
        showLoadingGif("Checking if " + nickname + " exists at in-game server...");
        let existsRequestUrl = appendPartToCurrentUrl(PROVIDER_PATH + RELATIVE_PATH + nickname + EXISTS_IN_GAME_ENDPOINT);
        existsRequestUrl = changePortIfPresent(existsRequestUrl, 8080, 8081);
        axios.get(existsRequestUrl)
            .then(response => processExistsInGameResponse(response.data, response.status, nickname))
            .catch(error => {
                hideLoadingGif();
                console.error('Get '+existsRequestUrl+' - ', error);
                showWarnAlert(error);
            });
    } else {
        openPlayerStatPage(nickname);
    }
}

function processExistsInGameResponse(exists, status, nickname) {
    hideLoadingGif();
    if (status !== 200) {
        console.warn(exists);
        console.warn(status);
        return;
    }
    if (exists) {
        showLoadingGif("Gathering " + nickname + " data...");
        let fetchPlayerRequestUrl = appendPartToCurrentUrl(PROVIDER_PATH + RELATIVE_PATH + nickname + FETCH_PLAYER_ENDPOINT);
        fetchPlayerRequestUrl = changePortIfPresent(fetchPlayerRequestUrl, 8080, 8081);
        axios.get(fetchPlayerRequestUrl)
            .then(response => processCollectPlayerResponse(response.data, response.status, nickname))
            .catch(error => {
                hideLoadingGif();
                console.error('Get '+fetchPlayerRequestUrl+' - ', error);
                showWarnAlert(error);
            });
    } else {
        showWarnAlert(PLAYER_NOT_EXISTS_IN_GAME);
    }
}

function processCollectPlayerResponse(data, status, nickname) {
    hideLoadingGif();
    if (status !== 200) {
        console.warn(data);
        console.warn(status);
        showWarnAlert(data.error);
        return;
    }
    openPlayerStatPage(nickname);
}

function openPlayerStatPage(nickname) {
    window.location.href = appendPartToCurrentUrl(RELATIVE_PATH + nickname);
}

function appendPartToCurrentUrl(part) {
    let currentUrl = window.location.href;
    currentUrl = currentUrl.charAt(currentUrl.length - 1) !== '/'
        ? currentUrl + '/'
        : currentUrl;
    return currentUrl + part;
}

function changePortIfPresent(url, portToReplace, wantedPort) {
    portToReplace = ":"+portToReplace+"/";
    wantedPort = ":"+wantedPort+"/";

    if (url.includes(portToReplace)) {
        return url.replace(portToReplace, wantedPort);
    }
    return url;
}

function showLoadingGif(message) {
    document.getElementById("loading-div").style.visibility = "visible";
    document.getElementById("loading-text").textContent = message;
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.visibility = "hidden";
}
