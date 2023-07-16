const ctx = document.getElementById('wn8Chart');

const labels = ['11.07', '12.07', '13.07', '14.07', '15.07']
const datapoints = [1301.3, 1306.3, 1304.3, 1311.3, 1321.3];

const milestoneDatapoints = [1330, 1330, 1330, 1330, 1330]

const data = {
    labels: labels,
    datasets: [
        {
            data: datapoints,
            fill: false,
            cubicInterpolationMode: 'monotone',
            borderColor: 'rgb(248, 244, 3)',
            tension: 0.4
        },
        {
            label: 'Next rank value',
            fill: false,
            borderColor: 'rgb(96, 255, 0)',
            borderDash: [5, 5],
            data: milestoneDatapoints,
        }
    ]
};

const config = {
    type: 'line',
    data: data,
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: 'WN8'
            },
            legend: {
                display: false
            }
        },
        interaction: {
            intersect: false,
        }
    },
};

new Chart(ctx, config);