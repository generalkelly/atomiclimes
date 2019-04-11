import $ from 'jquery';

$.fn.submitButton = function(object, receiverUrl) {

    this.click(function(event){
	ajaxPost(object, receiverUrl);
    });

}

function ajaxPost(object, receiverUrl){
    $.ajax({
	type: "POST",
	url: receiverUrl,
	data: JSON.stringify(object()),
	beforeSend: function(xhr) {
	    xhr.setRequestHeader("Accept", "application/json");
	    xhr.setRequestHeader("Content-Type", "application/json");
	},
	success: function () {
	},
	error: function () {
	}
    });
}