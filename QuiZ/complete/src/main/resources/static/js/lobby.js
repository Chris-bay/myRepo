$( document ).ready(function() {
     setInterval(checkForReady, 1000);
});

function checkForReady(){
    $.getJSON( "/api/getLiveQuiz/" + document.getElementById("liveQuizId").value, function( json ) {
        //console.log(json.participants);
        ready = json.ready
        if(ready == true){
            document.getElementById("submitButton").innerHTML = '<button type="submit">Enter the Game</button>';
        }
    });
}