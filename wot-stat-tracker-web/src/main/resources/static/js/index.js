import {
    battlesXvmColorValue,
    wn8XvmColorValue,
    winrateXvmColorValue
} from "./module/XvmColorValue.js";

/////////////////////////////////////////////////////

const EU_FLAG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/320px-Flag_of_Europe.svg.png";
const NA_FLAG_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Flag_of_the_United_States.png/320px-Flag_of_the_United_States.png";

const EU_SERVER = "eu";
const NA_SERVER = "na";

function changeFlag() {
    const value = document.getElementById("region").value;
    const img = document.getElementById("region-img");
    img.src = getImageUrl(value);
}

function getImageUrl(value) {
    if (value === EU_SERVER) {
        return EU_FLAG_URL;
    }
    if (value === NA_SERVER) {
        return NA_FLAG_URL;
    }
}

function setXvmColors() {
    const battlesElements = document.querySelectorAll("#latest-stats-table [title=battles]");
    battlesElements.forEach(elem => elem.className = battlesXvmColorValue.getColorClassName(elem.textContent))

    const wn8Elements = document.querySelectorAll("#latest-stats-table [title=wn8]");
    wn8Elements.forEach(elem => elem.className = wn8XvmColorValue.getColorClassName(elem.textContent));

    const winrateElements = document.querySelectorAll("#latest-stats-table [title=winrate]");
    winrateElements.forEach(elem => elem.className = winrateXvmColorValue.getColorClassName(elem.getAttribute("data-alt")))
}

function setOnclickRows() {
    document.querySelectorAll("#latest-stats-table tr")
        .forEach(row => {
            const url = window.location + row.getAttribute("data-href");
            row.setAttribute("onclick", "window.location='"+url+"'");
        })
}

function setRegionFlags() {
    document.querySelectorAll("#latest-stats-table [title=region]")
        .forEach(region => {
            const imageUrl = getImageUrl(region.textContent.toLowerCase());
            if (imageUrl === undefined) {
                return;
            }

            const image = document.createElement("img");
            image.setAttribute("src", imageUrl);
            image.setAttribute("width", "30");

            region.innerHTML = "";
            region.appendChild(image);
        })
}

setOnclickRows();
setXvmColors();
setRegionFlags();
