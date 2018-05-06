function getQuestion(id){
    $.getJSON( "/api/getCurrentQuestion/" + id, function( json ) {
        var correctanswer = json.answer;
        console.log(correctanswer);
        if(document.getElementById("RretAnswerStr").value == correctanswer){
            document.getElementById("QuestionContainer").innerHTML = "<p>Correct Answer!</p>\n"
        }else{
            document.getElementById("QuestionContainer").innerHTML = '<p>Wrong Answer :(</p>\n<p>Your answer: ' + document.getElementById("RretAnswerStr").value + ' But the correct answer was: ' + correctanswer + '</p>\n';
        }
    });
}