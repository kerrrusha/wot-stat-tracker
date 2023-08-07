class XvmColorValue {

    constructor(orangeValue, yellowValue, greenValue, blueValue, violetValue) {
        this.orangeValue = orangeValue;
        this.yellowValue = yellowValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
        this.violetValue = violetValue;
    }

    getColorClassName(value) {
        if (value === "-") {
            return;
        }
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

export const battlesXvmColorValue = new XvmColorValue(2000, 7000, 14000, 24000, 50000);
export const wgrXvmColorValue = new XvmColorValue(3304, 5211, 7256, 9521, 10953);
export const wn7XvmColorValue = new XvmColorValue(505, 865, 1225, 1635, 1995);
export const wn8XvmColorValue = new XvmColorValue(588, 1095, 1688, 2578, 3584);
export const winrateXvmColorValue = new XvmColorValue(0.4631, 0.4924, 0.526, 0.5785, 0.6355);
