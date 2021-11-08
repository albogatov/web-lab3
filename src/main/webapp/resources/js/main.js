// $(function () {

$(function () {

    const GRAPH_WIDTH = 360;
    const INDENT = 30;

    let graph = document.getElementById("graph-svg");
    let rVal;
    let xVal;
    let yVal;
    let prevR;
    let size = document.getElementById("graph-svg").getBoundingClientRect().width;
    let animationSnippet = (size - size / 6) / 2;
    let detectionSnippet = (size - size / 12) / 2;
    let svgns = "http://www.w3.org/2000/svg", container = document.getElementById('cont');

    function validateNumber(number) {
        return !isNaN(parseFloat(number)) && isFinite(parseFloat(number));
    }

    function validateX() {
        if ($("input[name=\"input-form:X_field_input\"]").attr("aria-valuenow")) {
            xVal = $("input[name=\"input-form:X_field_input\"]").val();
            return true;
        } else {
            $("#error-info").text("Select an X value!")
            return false;
        }
    }

    function validateY() {

        const Y_MIN = -3;
        const Y_MAX = 3;

        let y = $('.y-field').val().replace(',', '.');
        if (!y.isEmptyObject && validateNumber(y) && (y > Y_MIN) && (y < Y_MAX)) {
            yVal = y;
            return true;
        } else if ($("#y-hid").val() && $("#y-hid").val() > Y_MIN && $("#y-hid").val() < Y_MAX) {
            return true;
        } else {
            $("#error-info").text("Enter a valid Y value in the range from -3 to 3")
            return false;
        }
    }

    function validateR() {
        if (validateNumber(rVal)) {
            return true;
        } else {
            $("#error-info").text("Select one R value!")
            return false;
        }
    }

    function validateForm() {
        return validateR() && validateX() && validateY();
    }

    $("button[name=\"input-form:send\"]").on("click", function (event) {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            drawResult(xVal, yVal, rVal);
            $("input[name=\"input-form:true-r\"]").val(rVal);
        }
    });


    $("#graph-svg").on("click", function (event) {
        $("input[name=\"input-form:true-r\"]").val(rVal);
        if (!validateR()) return;
        let curR = rVal;
        let canvasX = (event.offsetX - detectionSnippet) / detectionSnippet * curR;
        canvasX = parseFloat(canvasX.toString().substring(0, 5))
        let canvasY = (detectionSnippet - event.offsetY) / detectionSnippet * curR;
        xVal = canvasX;
        yVal = canvasY;
        $("input[name=\"input-form:y\"]").val(canvasY.toString().substring(0, 5));
        $("input[name=\"input-form:X_field_input\"]").val(canvasX);
        $("button[name=\"input-form:send\"]").click();
    });

    $('.r-button').click(function () {
        if(prevR) {
            prevR.classList.toggle("r-button-chosen");
            prevR.classList.toggle("r-button");
        }
        rVal = $(this).html();
        prevR = $(this);
        $(this).removeClass("r-button");
        $(this).addClass("r-button-chosen");
        $("input[name=\"input-form:true-r\"]").val(rVal);
        let pointers = $(".pointer");
        let curR = rVal;
        let initX;
        let initY;
        let moveX;
        let moveY;
        for (let i = 0; i < pointers.length; i++) {
            initX = pointers[i].dataset.x;
            initY = pointers[i].dataset.y;
            moveX = size / 2 + animationSnippet * initX / Math.abs(curR);
            moveY = size / 2 - animationSnippet * initY / Math.abs(curR);
            if (calculateHit(initX, initY, curR)) {
                pointers[i].style.fill = "#a4cc84";
            } else pointers[i].style.fill = "#cca484";
            $(pointers[i]).animate({
                cx: moveX,
                cy: moveY
            }, {duration: 500, queue: false});
        }
    });

    $('#j_idt29\\:j_idt30').on("click", function (event) {
        $(".pointer").remove();
    });

    function calculateHit(x, y, r) {
        return calculateSectionOne(x, y, r) || calculateSectionTwo(x, y, r) || calculateSectionThree(x, y, r);
    }

    function calculateSectionOne(x, y, r) {
        return x <= 0 && y >= 0 && Math.abs(x) <= r / 2 && y <= r;
    }

    function calculateSectionTwo(x, y, r) {
        return x <= 0 && y <= 0 && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= r;
    }

    function calculateSectionThree(x, y, r) {
        return x >= 0 && y <= 0 && x <= r / 2 && y <= r / 2;
    }

    function drawAllResults() {
        let data = Array();
        $(".result-table tr").each(function (i, v) {
            data[i] = Array();
            $(this).children('td').each(function (ii, vv) {
                data[i][ii] = $(this).text();
            });
        })
        for (let i = 1; i < data.length; i++) {
            if (data[i][0] !== "No records found" && data[i][0])
                drawResult(data[i][0], data[i][1], rVal);
        }
        $('#input-form\\:j_idt20').click();
    }

    function drawResult(x, y, r) {
        let circle = document.createElementNS(svgns, 'circle');
        circle.setAttributeNS(null, 'cx', GRAPH_WIDTH / 2 + (GRAPH_WIDTH / 2 - INDENT) * x / Math.abs(r));
        circle.setAttributeNS(null, 'cy', GRAPH_WIDTH / 2 - (GRAPH_WIDTH / 2 - INDENT) * y / Math.abs(r));
        circle.setAttributeNS(null, 'r', 5);
        circle.setAttribute('data-x', x);
        circle.setAttribute('data-y', y);
        circle.classList.add("pointer");
        graph.appendChild(circle);
    }

    drawAllResults();
});
