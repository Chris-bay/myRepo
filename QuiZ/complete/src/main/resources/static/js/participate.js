function randomName(){

}
function getQuestion(id){
    $.getJSON( "/api/getCurrentQuestion/" + id, function( json ) {
        switch (json.type){
            case "MULTIPLECHOICE":
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(0) id="answer1"">\n<p>' + json.answers[0] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(1) id="answer2"">\n<p>' + json.answers[1] + '</p>\n</div>\n<div class="col-2"></div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(2) id="answer3"">\n<p>' + json.answers[2] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(3) id="answer4"">\n<p>' + json.answers[3] + '</p>\n</div>\n<div class="col-2"></div>\n</div>'
                break;
            case "":
                break;
            case "":
                break;
        }
        document.getElementById("");
    });
}

function selectAnswer(number){
    switch (number){
        case 0:

            break;
        case 1:

            break;
        case 2:

            break;
        case 3:

            break;
    }
}