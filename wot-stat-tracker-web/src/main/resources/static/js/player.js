const PLAYER_NOT_EXISTS_IN_GAME = "Such player does not exists in WoT EU server.";

const PATH = "player/";
const IS_VALID_ENDPOINT = "/is-valid";
const EXISTS_IN_DB_ENDPOINT = "/exists-in-db";
const EXISTS_IN_GAME_ENDPOINT = "/exists-in-game";
const GET_PLAYER_ENDPOINT = "/info";

function submitForm(event) {
    event.preventDefault();
    let nickname = document.getElementById("nickname").value;

    const isValidRequestUrl = appendPartToCurrentUrl(PATH + nickname + IS_VALID_ENDPOINT);
    axios.get(isValidRequestUrl)
        .then(response => processIsValidResponse(response.data, response.status, nickname))
        .catch(error => {
            console.error('#submitForm - ', error);
            let message = error.response.data;
            if (!message) {
                message = error.message;
            }
            showWarnAlert(message);
        });
}

function showWarnAlert(error) {
    document.getElementById("warn-alert").style.display = "block";
    document.getElementById("warn-alert-message").innerHTML = error;
}

function appendPartToCurrentUrl(part) {
    let currentUrl = window.location.href;
    currentUrl = currentUrl.charAt(currentUrl.length - 1) !== '/'
        ? currentUrl + '/'
        : currentUrl;
    return currentUrl + part;
}

function processIsValidResponse(data, status, nickname) {
    if (data !== true || status !== 200) {
        console.warn(data);
        console.warn(status);
        return;
    }

    const existsRequestUrl = appendPartToCurrentUrl(PATH + nickname + EXISTS_IN_DB_ENDPOINT);
    axios.get(existsRequestUrl)
        .then(response => processExistsInDbResponse(response.data, response.status, nickname))
        .catch(error => {
            console.error('#processIsValidResponse - ', error);
            showWarnAlert(error.response.data);
        });
}

function processExistsInDbResponse(exists, status, nickname) {
    if (status !== 200) {
        console.warn(exists);
        console.warn(status);
        return;
    }
    if (!exists) {
        const existsRequestUrl = appendPartToCurrentUrl(PATH + nickname + EXISTS_IN_GAME_ENDPOINT);
        axios.get(existsRequestUrl)
            .then(response => processExistsInGameResponse(response.data, response.status, nickname))
            .catch(error => {
                console.error('#processExistsInDbResponse - ', error);
                showWarnAlert(error.response.data);
            });
    } else {
        openPlayerStatPage(nickname);
    }
}

function processExistsInGameResponse(exists, status, nickname) {
    if (status !== 200) {
        console.warn(exists);
        console.warn(status);
        return;
    }
    if (exists) {
        showLoadingGif("Gathering " + nickname + " data...");
        const getPlayerRequestUrl = appendPartToCurrentUrl(PATH + nickname + GET_PLAYER_ENDPOINT);
        axios.get(getPlayerRequestUrl)
            .then(response => processCollectPlayerResponse(response.data, response.status, nickname))
            .catch(error => {
                hideLoadingGif();
                console.error('#processExistsInGameResponse - ', error);
                showWarnAlert(error.response.data);
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
    window.location.href = appendPartToCurrentUrl(PATH + nickname);
}

function showLoadingGif(message) {
    document.getElementById("loading-div").style.visibility = "visible";
    document.getElementById("loading-text").innerHTML = message;
}

function hideLoadingGif() {
    document.getElementById("loading-div").style.visibility = "hidden";
}
