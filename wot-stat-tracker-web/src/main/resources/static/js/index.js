const EU_FLAG = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/320px-Flag_of_Europe.svg.png";
const NA_FLAG = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Flag_of_the_United_States.png/320px-Flag_of_the_United_States.png";

const EU_SERVER = "eu";
const NA_SERVER = "na";

function changeFlag() {
    const value = document.getElementById("server").value;
    const img = document.getElementById("server-img");
    if (value === EU_SERVER) {
        img.src = EU_FLAG;
    }
    if (value === NA_SERVER) {
        img.src = NA_FLAG;
    }
}