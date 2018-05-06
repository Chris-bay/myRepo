var locanswers;

function randomName(){

}

function getQuestion(id){
    $.getJSON( "/api/getCurrentQuestion/" + id, function( json ) {
        switch (json.type){
            case "MULTIPLECHOICE":
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(0)" id="answer1">\n<p>A:' + json.answers[0] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(1)" id="answer2">\n<p>B:' + json.answers[1] + '</p>\n</div>\n<div class="col-2"></div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(2)" id="answer3">\n<p>C:' + json.answers[2] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(3)" id="answer4">\n<p>D:' + json.answers[3] + '</p>\n</div>\n<div class="col-2"></div>\n</div>';
                break;
            case "GUESS":
                document.getElementById("RretAnswer").value = 0;
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-12"><input type="number" class="form-control" id="answer1" onchange="setText()"/>\n</div>\n</div>\n';
                break;
            case "STRING":
                document.getElementById("RretAnswer").value = 0;
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-12"><input type="text" class="form-control" id="answer1" onchange="setText()"/>\n</div>\n</div>\n';
                break;
            case "MMULTIPLECHOICE":
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(0)" id="answer1">\n<p>A:' + json.answers[0] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(1)" id="answer2">\n<p>B:' + json.answers[1] + '</p>\n</div>\n<div class="col-2"></div>\n</div>\n<div class="row">\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(2)" id="answer3">\n<p>:C' + json.answers[2] + '</p>\n</div>\n<div class="col-2"></div>\n<div class="col-3" onclick="selectAnswer(3)" id="answer4">\n<p>D:' + json.answers[3] + '</p>\n</div>\n<div class="col-2"></div>\n</div>';
                break;
            case "MGUESS":
                document.getElementById("RretAnswer").value = 0;
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-12"><input type="number" class="form-control" id="answer1" onchange="setText()"/>\n</div>\n</div>\n';
                break;
            case "MSTRING":
                document.getElementById("RretAnswer").value = 0;
                document.getElementById("QuestionContainer").innerHTML = '<div class="row">\n <div class="jumbotron" id="questionText">' + json.questionText + '</div>\n</div>\n<div class="row">\n<div class="col-12"><input type="text" class="form-control" id="answer1" onchange="setText()"/>\n</div>\n</div>\n';
                break;
        }
        document.getElementById("RType").value = json.type;
        locanswers = json.answers;
    });
}

function selectAnswer(number){
    document.getElementById("RretAnswer").value = number;
    console.log("set selected answer to: " + (number+1).toString());
    console.log("current answer: " + locanswers[number]);
    document.getElementById("RretAnswerStr").value = locanswers[number];
}

function changeText(){
    document.getElementById("RretAnswerStr").value = document.getElementById("anser1").value;
}