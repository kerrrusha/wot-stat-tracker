function clock() {
    const clock = document.getElementById("clock");
    const serverTimezoneShift = 3;

    let date = new Date();
    let sec = date.getUTCSeconds();
    let min = date.getUTCMinutes();
    let hours = serverTimezoneShift + date.getUTCHours();

    clock.textContent = formatTime(hours, min, sec);
}

function formatTime(hours, minutes, seconds) {
    // Ensure two digits format for each value
    const formattedHours = String(hours).padStart(2, '0');
    const formattedMinutes = String(minutes).padStart(2, '0');
    const formattedSeconds = String(seconds).padStart(2, '0');

    // Combine the formatted values into a single string
    return `${formattedHours}:${formattedMinutes}:${formattedSeconds}`;
}
setInterval(clock,1000);