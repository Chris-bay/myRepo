function  ready(quizId){
    var dt = $('#questionTable').DataTable({
            ajax: {
                url: '/api/getQuestionsOfQuiz/' + quizId,
                dataSrc: ''
            },
            "columns": [
                {
                    "data": "data",
                    "render": function (data, type, row, meta) {
                        return data.questionText;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.type;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.answers;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.answers;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.answers;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.answers;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.answer;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.mediaLink;
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return data.points;
                    }
                }
            ],
        "order": [[1, 'asc']]
    });
}