function setHeadtext(id, miniId){
    if (id == 0){
        document.getElementById("header").innerHTML = "something went wrong";
    }else{
        $.getJSON( "/api/getQuiz/" + id, function( json ) {
                title = "Quiz: " + json.title;
                console.log(title);
                document.getElementById("header").innerHTML = "<h1>" + title + "</h1>";
        });
    }
    document.getElementById("miniId").innerHTML = '<p style="font-size: 400%;">' + miniId + '</p>'
}

function checkForParticipants(){

    $.getJSON( "/api/getLiveQuiz/" + document.getElementById("liveQuizId").value, function( json ) {
        //console.log(json.participants);
        participants = json.participants
        tablerows = "";
        for(i = 0; i < participants.length; i++){
            tablerows = tablerows + "<tr><td>" + participants[i] + "</td></tr>\n";
        }
        document.getElementById("participant-table").innerHTML = "<th>Participants:</h1>\n" + tablerows;
    });
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

$( document ).ready(function() {
     setInterval(checkForParticipants, 1000);
});
