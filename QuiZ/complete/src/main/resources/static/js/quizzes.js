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
    })
});

function test(){
    console.log("test!");
}