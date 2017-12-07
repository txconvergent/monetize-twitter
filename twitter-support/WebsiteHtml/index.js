$(document).ready(function(){
    $("#submit").click(function() {
        var inputHandle = document.getElementById("inputHandle").value;
        var inputQuestion = document.getElementById("inputQuestion").value;

        if (inputHandle.charAt(0) != '@')
            inputHandle = '@' + inputHandle;
        
        $("#displayHandle").html(inputHandle);
        $("#displayQuestion").html(inputQuestion);
        $("#inputHandle").val("");
        $("#inputQuestion").val("");
    });
});