var maxprogress = 100;   // total to reach
var actualprogress = 0;  // current value
var itv = 0;  // id for setinterval
var load = 0;
var loadDots = 0;

function prog() {
    if (actualprogress >= maxprogress) {
        actualprogress = 0;
        $("#indicator").css("width", "0%");
    }
    actualprogress += 2;
    $("#indicator").css("width", actualprogress + "%");
}