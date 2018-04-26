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
                        var ca = row.answers[answer];
                        return ca;
                    }
                },
                {"data":"media"},
                {"data":"points"}
            ],
        "order": [[1, 'asc']]
    });
}