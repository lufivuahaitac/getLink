$("#link").on('paste', function () {
    setTimeout(function () {
        post();
    }, 100);
});
$("#link").on('focus', function () {
    $('#linkValidate').html('');
});

function post() {
    $('#download').html('');
    $('#linkValidate').html('');
    $("#link").blur();
    var postUrl;
    var input = $('#link').val().split("|");
    var link = input[0];
    if (link.indexOf("fshare.vn/file/") !== -1) {
        postUrl = "getFshare";
    } else if (link.indexOf("4share.vn/f/") !== -1) {
        postUrl = "get4share";
    } else {
        $('#linkValidate').html('<label class="error">Link không đúng. Vui lòng thử lại</label>');
        $('#linkValidate').css("display", "block");
        return;
    }
    var password = input[1];
    console.log("PostUrl: " + postUrl + " - link: " + link + " - password: " + password);
    $('#fountainG').css("display", "block");
    $.ajax({
        type: 'POST',
        url: postUrl,
        data: 'url=' + link + "&password=" + password,
        success: function (response) {
            console.log(response);
            var obj = JSON.parse(response);
            console.log(typeof obj.url);
            if (typeof obj.url !== 'undefined' && obj.url !== '') {
                if (postUrl === 'getFshare') {
                    var name = obj.url.split(/\//).pop();
                    console.log('Name: ' + name);
                    $('#download').html('<a id=vipLink href="' + obj.url + '">' + name + '</a>');
                }
                if (postUrl === 'get4share') {
                    console.log('Name: ' + name);
                    $('#download').html('<a id=vipLink href="' + obj.url + '">' + obj.fileName + '</a>');
                }

            }
            if (typeof obj.error !== 'undefined' && obj.error !== '') {
                $('#search-error-container').html('<label class="error">' + obj.error + '</label>');
                $('#search-error-container').css("display", "block");
            }

        },
        complete: function (data) {
            $('#fountainG').css("display", "none");
        }
    });
}
;