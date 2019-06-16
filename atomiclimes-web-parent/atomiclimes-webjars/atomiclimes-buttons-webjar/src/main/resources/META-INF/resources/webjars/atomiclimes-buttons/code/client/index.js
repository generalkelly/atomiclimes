import $ from 'jquery';
import feather from 'feather-icons';

var checkButton = $(feather.icons["check-circle"].toSvg({'stroke-width': 2}));
$('.checkButton').append(checkButton);

var editButton = $(feather.icons["edit-2"].toSvg({'stroke-width': 2}));
$('.editButton').append(editButton);

var deleteButton = $(feather.icons["trash-2"].toSvg({'stroke-width': 2}));
$('.deleteButton').append(deleteButton);

var addButton = $(feather.icons["plus-circle"].toSvg({'stroke-width': 2}));
$('.addButton').append(addButton);


var goToButton = $(feather.icons["arrow-right"].toSvg({'stroke-width': 2}));
$('.goToButton').append(goToButton);