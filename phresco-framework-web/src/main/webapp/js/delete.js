$(document).ready(function(){
	$('#checkAllAuto').click(function() {
		$(".check").each(function() {
			if ($(this).is(':disabled')) {
			} else {
				$(this).attr('checked', $('#checkAllAuto').is(':checked')); 
			}
		});
		if ($('#checkAllAuto').is(':checked')) {
			$('#deleteButton').addClass("btn-primary");
			$('#deleteButton').attr("disabled", false);
		} else {
			$('#deleteButton').removeClass("btn-primary");
			$('#deleteButton').attr("disabled", true);
		}
	});

	$('.check').click(function() {
		if ($('.check').is(':checked')) {
			$('#deleteButton').addClass("btn-primary");
			$('#deleteButton').attr("disabled", false);
		} else {
			$('#deleteButton').removeClass("btn-primary");
			$('#deleteButton').attr("disabled", true);
		}
		isAllChecked();
	});
});

function isAllChecked() {
	var $allCheck = $('.check');
	if ($allCheck.length == $allCheck.filter(':checked').length) {
		$('#checkAllAuto').attr('checked', true);
	} else {
		$('#checkAllAuto').attr('checked', false);
	}
}