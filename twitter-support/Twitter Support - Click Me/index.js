$(document).ready(function(){
    $("#searchButton").click(function() {
        var inputHandle = document.getElementById("inputHandle");
        var inputQuestion = document.getElementById("inputQuestion");
        var inputRelpyNum = document.getElementById("inputRelpyNum");

        window.location.href="search.html";
        $("#pageName").html(inputHandle.value);
        $("#question").html(inputQuestion.value);
    });
});