
function  ready(quizId){
    var dt = $('#questionTable').DataTable({
            ajax: {
                url: '/api/getQuestionsOfQuiz/' + quizId,
                dataSrc: ''
            },
            "columns": [
                {"data":"questionText"},
                {
                    "data": "type",
                    "render": function (data, type, row, meta) {
                        var label = "";

                        /*
                            MULTIPLECHOICE(0),
                            GUESS(1),
                            STRING(2),
                            MMULTIPLECHOICE(3),
                            MGUESS(4),
                            MSTRING(5);
                        */

                        if(row.type == "MULTIPLECHOICE")
                        {
                            label = "multiple choice";
                        }
                        if(row.type == "GUESS")
                        {
                            label = "guess";
                        }
                        if(row.type == "STRING")
                        {
                            label = "word/name";
                        }
                        if(row.type == "MMULTIPLECHOICE")
                        {
                            label = "multiple choice (media)";
                        }
                        if(row.type == "MGUESS")
                        {
                            label = "guess (media)";
                        }
                        if(row.type == "MSTRING")
                        {
                            label = "word/name (media)";
                        }
                        return label;
                    }
                },
                {
                    "data": "answers",
                    "render": function (data, answers, row, meta) {
                        var ca = row.answers[0];
                        return ca;
                    }
                },
                {
                    "data": "answers",
                    "render": function (data, answers, row, meta) {
                        var ca = row.answers[1];
                        return ca;
                    }
                },
                {
                    "data": "answers",
                    "render": function (data, answers, row, meta) {
                        var ca = row.answers[2];
                        return ca;
                    }
                },
                {
                    "data": "answers",
                    "render": function (data, answers, row, meta) {
                        var ca = row.answers[3];
                        return ca;
                    }
                },
                {
                    "data": "answer",
                    "render": function (data, answer, row, meta) {
                        var ca = row.answer;
                        return ca;
                    }
                },
                {"data":"media"},
                {"data":"points"},
                {
                    "data": "id",
                    "render": function (data, id, row, meta) {
                        var a = '<button class="btn btn-success" data-toggle="modal" data-target="#editQuestionModal" onclick="changeQuestion(' + data + ')"><i class="fa fa-fw fa-edit"></i></button>';
                        return a;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, id, row, meta) {
                        var a = '<button class="btn btn-danger" onclick="deleteQuestion(' + data + ')"><i class="fa fa-fw fa-close"></i></button>';
                        return a;
                    }
                }
            ],
        "order": [[1, 'asc']]
    });
}

function changeQuestion(id){
    /*$.ajax({
        url: "/api/getQuestion/" + id
    }).then(function (js) {
        console.log(js);
        console.log(js.id);
        console.log(js.type);
        //document.getElementById("EQuizId").value = js.id;
    });*/

    $.getJSON( "/api/getQuestion/" + id, function( json ) {

        //console.log(json.id);

        document.getElementById("EQuestionId").value = json.id;
        document.getElementById("EQuestionText").value = json.questionText;

        if (json.type == "MULTIPLECHOICE"){
            document.getElementById("EQuestionType").selectedIndex = 0;
        }else if (json.type == "GUESS"){
            document.getElementById("EQuestionType").selectedIndex = 1;
        }else if (json.type == "STRING"){
             document.getElementById("EQuestionType").selectedIndex = 2;
        }else if (json.type == "MMULTIPLECHOICE"){
             document.getElementById("EQuestionType").selectedIndex = 3;
        }else if (json.type == "MGUESS"){
             document.getElementById("EQuestionType").selectedIndex = 4;
        }else{
             document.getElementById("EQuestionType").selectedIndex = 5;
        }

        document.getElementById("EAnswer1").value = json.answers[0];
        document.getElementById("EAnswer2").value = json.answers[1];
        document.getElementById("EAnswer3").value = json.answers[2];
        document.getElementById("EAnswer4").value = json.answers[3];

        if (json.answer == 0){
            document.getElementById("ERightAnswer").selectedIndex = 0;
        }else if (json.answer == 1){
            document.getElementById("ERightAnswer").selectedIndex = 1;
        }else if (json.answer == 2){
             document.getElementById("ERightAnswer").selectedIndex = 2;
        }else {
             document.getElementById("ERightAnswer").selectedIndex = 3;
        }

        document.getElementById("EMedia").value = json.media;
        document.getElementById("EPoints").value = json.points;
    });
}

function deleteQuestion(id){
    Pinger_ping("localhost:8080/deleteQuestion/" + document.getElementById("AQuizId").value + "/" + id);
    window.setTimeout(partB(),340);
}

function partB(){
    $('#questionTable').DataTable().ajax.reload();
}

function Pinger_ping(ip) {

  if(!this.inUse) {

    this.inUse = true;
    this.ip = ip;

    var _that = this;

    this.img = new Image();

    this.img.onload = function() {};
    this.img.onerror = function() {};

    this.start = new Date().getTime();
    this.img.src = "http://" + ip;
    this.timer = setTimeout(function() {}, 1500);

  }
}