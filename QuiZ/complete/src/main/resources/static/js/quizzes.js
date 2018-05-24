$(document).ready(function(){
    var dt = $('#quizzes').DataTable({
            ajax: {
                url: '/api/getAllQuiz',
                dataSrc: ''
            },
            "columns": [
                {"data": "id"},
                {"data": "title"},
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-success" href="/startQuiz/' + data + '"><i class="fa fa-fw fa-play"></i></a>';
                        return a;
                    }
                },
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-info" href="/changeQuiz/' + data + '"><i class="fa fa-fw fa-edit"></i></a>';
                        return a;
                    }
                },
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-danger" href="/deleteQuiz/' + data + '"><i class="fa fa-fw fa-close"></i></a>';
                        return a;
                    }
                }
            ],
        "order": [[1, 'asc']]
    });
    var dt1 = $('#liveQuizzes').DataTable({
            ajax: {
                url: '/api/getAllLiveQuiz',
                dataSrc: ''
            },
            "columns": [
                {"data": "quiz.title"},
                {"data": "hashId"},
                {
                    "data": "dateOfActivation",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = data.date.day + '.' + data.date.month + '.' + data.date.year + ' ' + data.time.hour + ':' + data.time.minute + ':' + data.time.second;
                        return a;
                    }
                },
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-info" href="/enterQuiz/' + data + '"><i class="fa fa-fw fa-repeat"></i></a>';
                        return a;
                    }
                },
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-danger" href="/api/deleteLiveQuiz/' + data + '"><i class="fa fa-fw fa-close"></i></a>';
                        return a;
                    }
                },
                {
                    "data": "id",
                    "orderable": false,
                    "searchable": false,
                    "render": function (data, type, row, meta) {
                        var a = '<a class="btn btn-danger" href="/api/deleteLiveQuiz/' + data + '"><i class="fa fa-fw fa-close"></i></a>';
                        return a;
                    }
                }
            ],
        "order": [[1, 'asc']]
    });
});

function test(){
    console.log("test!");
}