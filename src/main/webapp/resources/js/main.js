// $(function () {

$(function () {

    let r;

    function validateNumber(number) {
        return !isNaN(parseFloat(number)) && isFinite(parseFloat(number));
    }

    function validateX() {
        alert($("input[name=\"newEntryForm:X_field_input\"]").attr("aria-valuenow"));
        if ($("input[name=\"newEntryForm:X_field_input\"]").attr("aria-valuenow") || $("#x-hid").val()) {
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
            return true;
        } else if ($("#y-hid").val() && $("#y-hid").val() > Y_MIN && $("#y-hid").val() < Y_MAX) {
            return true;
        } else {
            $("#error-info").text("Enter a valid Y value in the range from -3 to 3")
            return false;
        }
    }

    function validateR() {
        if (r) {
            return true;
        } else {
            $("#error-info").text("Select one R value!")
            return false;
        }
    }

    function validateForm() {
        return validateR() && validateX() && validateY();
    }

    $("#send").on("click", function (event) {
        if (!validateX()) {
            event.preventDefault();
        } else {
            alert("true");
            $("#true-r").val(r);
        }
    });

    $("#clean-button").on("click", function (event) {
        $("#c-id").val("true");
    });

    $("#graph-svg").on("click", function (event) {
        $("#true-r").val(r);
        if (!validateR()) return;
        let size = document.getElementById("graph-svg").getBoundingClientRect().width;
        let snippet = (size - size / 12) / 2;
        let curR = r;
        let canvasX = (event.offsetX - snippet) / snippet * curR;
        let canvasY = (snippet - event.offsetY) / snippet * curR;
        $("#x-hid").val(canvasX);
        $("#y-hid").val(canvasY);
        $("#send").click();
    });

    $('.r-button').click(function () {
        r = $(this).html();
        // let pointers = $("[name='pointer']");
        let curR = r;
        let initX;
        let initY;
        let moveX;
        let moveY;
        let initR;
        let hit;
        let size = document.getElementById("graph-svg").getBoundingClientRect().width;
        let snippet = (size - size / 6) / 2;
        // for (let i = 0; i < pointers.length; i++) {
        //     initX = pointers[i].dataset.x;
        //     initY = pointers[i].dataset.y;
        //     initR = pointers[i].dataset.r;
        //     hit = pointers[i].dataset.hit;
        //     moveX = size / 2 + snippet * initX / Math.abs(curR);
        //     moveY = size / 2 - snippet * initY / Math.abs(curR);
        //     if (calculateHit(initX, initY, curR)) {
        //         pointers[i].style.fill = "#a4cc84";
        //     } else pointers[i].style.fill = "#cca484";
        //     $(pointers[i]).animate({
        //         cx: moveX,
        //         cy: moveY
        //     }, {duration: 500, queue: false});
        // }
    });

    function calculateHit(x, y, r) {
        return calculateSectionOne(x, y, r) || calculateSectionTwo(x, y, r) || calculateSectionThree(x, y, r);
    }

    function calculateSectionOne(x, y, r) {
        return y >= 0 && x >= 0 && x <= r && Math.sqrt(x * x + y * y) <= r;
    }

    function calculateSectionTwo(x, y, r) {
        return y >= 0 && x <= 0 && Math.abs(x) <= r && y <= (parseFloat(x) + parseFloat(r)) && y <= r;
    }

    function calculateSectionThree(x, y, r) {
        return y <= 0 && x <= 0 && Math.abs(x) <= r && Math.abs(y) <= r;
    }

});
