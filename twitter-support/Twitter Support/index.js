$(document).ready(function(){
    $("#searchButton").click(function() {
        var inputHandle = document.getElementById("inputHandle");
        var inputQuestion = document.getElementById("inputQuestion");
        var inputRelpyNum = document.getElementById("inputRelpyNum");
        
        $("#pageName").val(inputHandle.value);
        
        window.location.href="http://google.com";
    });
});